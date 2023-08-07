$('document').ready(() => {
    $('[data-bs-toggle=tooltip]').tooltip();

    getPostsSearches();
    
    document.getElementById("start-post-scraper").addEventListener("click", (event) => startPostScraper(event));
    setInterval(getStatus, 500);
})

let postSearchIds = new Set();
let statusMessages = []

const getPostsSearches = () => {
    fetch('/api/v1/posts-searches').then((response) => response.json()).then((data) => {
        console.log("post data", data)
        if(data.length < 1) {
            document.getElementById("searches-table").style.display = "none";
            document.getElementById("no-searches-indicator").style.display = "block";
        }
        const tableBody = document.getElementById("posts-searches-tbody");
        data.forEach((search, index) => {
           if(!postSearchIds.has(search.id)) {
            postSearchIds.add(search.id);
            const newRow = tableBody.insertRow();
            newRow.innerHTML = `
           <td>${index + 1}</td>
           <td>${getFormattedDate(search.searchedAt)}</td>
           <td><button class="btn btn-sm btn-primary" onclick="viewPosts('${search.id}')" >View Posts</button></td>
           `;
           }
        })
    });
}

const viewPosts = (searchId) => {
    console.log("called");
    fetch(`/api/v1/view-posts/${searchId}`).then((response) => response.json()).then((data) => {
        const tableBody = document.getElementById("posts-by-search-result");
        tableBody.innerHTML = '';
        data.forEach((post, index) => {
            const newRow = tableBody.insertRow();
            newRow.innerHTML = `
            <td>${index + 1}</td>
            <td>${post.by}</td>
            <td><button id="${index}-post" type="button" class="btn btn-sm btn-success" content="${post.content}" onclick="viewPostContent('${index}-post')" >Show content</button></td>
            <td>${post.matchedKeywords}</td>
            <td><a href="${post.link}" target="_blank" >Open Post</a></td>
            `;
        })
        $('#posts-by-search-modal').modal("show");
    }).catch((error) => {
        console.log("Unable to get posts by search, error: ", error);
    })
}

const getFormattedDate = (date) => {
    date = new Date(date);
    return `${padWithZero(date.getDate())}/${padWithZero(date.getMonth() + 1)}/${date.getFullYear()} - ${padWithZero(date.getHours())}:${padWithZero(date.getMinutes())}`;

    function padWithZero(number) {
        return number.toString().padStart(2, '0');
    }
}


const startPostScraper = (event) => {
    event.preventDefault();

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const keywords = document.getElementById('keywords').value;
    const totalPostsToFetch = document.getElementById("totalPostsToFetch").value;

    const datePosted = document.getElementById("datePosted").value;
    const sortBy = document.getElementById("sortBy").value;

    const requestedData = {
        email: email,
        password: password,
        keywords: keywords,
        totalPostsToFetch: totalPostsToFetch,
        headlessMode: false,
        datePosted: datePosted,
        sortBy: sortBy
    }

    fetch('/api/v1/post-scraper', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestedData)
    }).then((response) => response.text()).then((response) => {
        getStatus();
    })
}



const viewPostContent = (id) => {
    const content = document.getElementById(id).getAttribute("content");
    document.getElementById("post-content-viewer-modal-body").innerHTML = content;

    $('#post-content-viewer-modal').modal("show");
}


const getStatus = () => {
    fetch('/api/v1/status/post-scraper').then((response) => response.json())
        .then((response) => {

            const statusDiv = document.getElementById("scraper-live-messages");

            if (response.scraperRunning) {
                document.getElementById("scraper-live-messages").innerHTML = '';
                document.getElementById("start-post-scraper").disabled = true;

                if (!statusMessages.includes(response.status)) {
                    statusMessages.push(response.status);
                }

                let formattedMessages = statusMessages.map((message, index) => `${index + 1}. ${message}`);
                statusDiv.innerHTML = formattedMessages.join('<br>');

                if (response.status == "Successfully saved fetched posts into database. Shutting down scraper") {
                    getPostsSearches();
                    document.getElementById("scraper-live-messages").innerHTML = 'Successfully saved fetch posts into database.';
                    document.getElementById("start-post-scraper").disabled = false;
                    document.getElementById("scraper-live-messages").innerHTML = "Currently scraper is not running.";
                    $('#post-scraper-modal').modal("close");
                    alert("Successfully fetched posts and saved into database.");
                }

            } else {
                document.getElementById("scraper-live-messages").innerHTML = 'Currently scraper is not running.';
                document.getElementById("start-post-scraper").disabled = false;
            }

        }).catch((error) => {
            console.log("Error getting status: ", error)
        })
}