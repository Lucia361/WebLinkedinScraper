$(document).ready(() => {
    $('[data-bs-toggle=tooltip]').tooltip();

    new Choices('#selected-locations', {
        removeItemButton: true,
        maxItemCount: 5,
        searchResultLimit: 5,
        renderChoiceLimit: 5
    });

    new Choices('#selected-industries', {
        removeItemButton: true,
        maxItemCount: 5,
        searchResultLimit: 5,
        renderChoiceLimit: 5,
    });

    getProfilesSearches();

    document.getElementById("start-profile-scraper").addEventListener("click", (event) => startProfileScraper(event));

})

let profileSearchIds = new Set(); // To store existing profile search IDs

const getProfilesSearches = () => {
    fetch('/api/v1/profiles-searches')
    .then((response) => response.json()).then((data) => {
        if (data.length < 1) {
            document.getElementById("searches-table").style.display = "none";
            document.getElementById("no-searches-indicator").style.display = "block";
        }
        const tableBody = document.getElementById("profiles-searches-tbody");
        data.forEach((search, index) => {
            if (!profileSearchIds.has(search.id)) {
                profileSearchIds.add(search.id);
                const newRow = tableBody.insertRow();
                newRow.innerHTML = `
                   <td>${index + 1}</td>
                   <td>${getFormattedDate(search.searchedAt)}</td>
                   <td><button class="btn btn-sm btn-primary" onclick="viewProfiles('${search.id}')" >View Profiles</button></td>
               `;
            }
        });
    })
    .catch((error) => {
        console.log("Error fetching profiles searches:", error);
    });
};

const getPostsSearches = () => {
    fetch('/profiles-searches').then((response) => response.json()).then((data) => {
        const tableBody = document.getElementById("posts-searches-tbody");
        data.forEach((search, index) => {
            const newRow = tableBody.insertRow();
            newRow.innerHTML = `
           <td>${index + 1}</td>
           <td>${getFormattedDate(search.searchedAt)}</td>
           <td><button class="btn btn-sm btn-primary" onclick="viewPosts('${search.id}')" >View Posts</button></td>
           `;
        })
    });
}

const viewProfiles = (searchId) => {
    let profiles = new Set();
    fetch(`/api/v1/view-profiles/${searchId}`).then((response) => response.json()).then((data) => {
        const tableBody = document.getElementById("profiles-by-search-result");
        tableBody.innerHTML = ''; // Clear existing rows before adding new profiles
        data.forEach((profile, index) => {
            console.log("typeof experience:", typeof(profile.experience), profile.experience)
            if(!profiles.has(profile.email)) {
                profiles.add(profile.email);
                const newRow = tableBody.insertRow();
            newRow.innerHTML = `
            <td>${index + 1}</td>
            <td>${profile.name}</td>
            <td>${profile.email}</td>
            <td>${profile.about}</td>
            <td class="experience-th" title="${profile.experience}"><button id="${index}-copyExperienceBtn" type="button" class="badge rounded-pill bg-success" data-copytext="${profile.experience}" onclick="copyToClipBoard('${index}-copyExperienceBtn')" >Copy experience</button></td>
            <td class="education-th" title="${profile.education}" ><button id="${index}-copyEducationBtn" type="button" class="badge rounded-pill bg-success" data-copytext="${profile.education}" onclick="copyToClipBoard('${index}-copyEducationBtn')" >Copy education</button></td>
            <td class="text-center" >${profile.isOpenToWork ? "Yes" : "No"}</td>
            <td><a href="${profile.link}"  target="_blank" >Open Profile</a></td>
            `;
            }
        })
        $('#profiles-by-search-modal').modal("show");
    }).catch((error) => {
        console.log("Unable to get profiles by search, error: ", error);
    })
}


const viewPosts = (searchId) => {
    fetch(`/view-posts/${searchId}`).then((response) => response.json()).then((data) => {

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

const startProfileScraper = (event) => {
        event.preventDefault();

        let selectedLocations = []
        let selectedIndustries = []

        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        const totalProfilesToFetch = document.getElementById("totalProfilesToFetch").value;

        const firstConnection = document.getElementById('con1').checked;
        const secondConnection = document.getElementById('con2').checked;
        const thirdConnection = document.getElementById('con3').checked;

        console.log("first connection checked: ", firstConnection)

        const locations = document.getElementById("selected-locations").selectedOptions;
        for(let option of locations) {
          selectedLocations.push(option.value);
        }

        const industries = document.getElementById("selected-industries").selectedOptions;
        for(let option of industries) {
          selectedIndustries.push(option.value)
        }

        const requestedData = {
            email: email,
            password: password,
            totalProfilesToFetch: totalProfilesToFetch,
            headlessMode: false,
            filters: {
                isFirstConnectionChecked: firstConnection,
                isSecondConnectionChecked: secondConnection,
                isThirdConnectionChecked: thirdConnection,
                selectedLocations: selectedLocations,
                selectedIndustries: selectedIndustries,
            }
        };
    

        fetch('/api/v1/profile-scraper', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestedData)
        }).then((response) => response.text).then((response) => {
            getProfilesSearches();
        }).catch((error) => {
            console.log(error);
        })
}

const copyToClipBoard = (id) => {

    const copyBtn = document.getElementById(id);
    const copyText = copyBtn.getAttribute("data-copytext");
    copyBtn.addEventListener("click", function () {
    navigator.clipboard.writeText(copyText);
    alert("Copied successfully!")
});
}