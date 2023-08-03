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

})

const getProfilesSearches = () => {
    fetch('/api/v1/profiles-searches').then((response) => response.json()).then((data) => {
        console.log("data searches:", data);
        const tableBody = document.getElementById("profiles-searches-tbody");
        data.forEach((search, index) => {
            const newRow = tableBody.insertRow();
            newRow.innerHTML = `
           <td>${index + 1}</td>
           <td>${getFormattedDate(search.searchedAt)}</td>
           <td><button class="btn btn-sm btn-primary" onclick="viewProfiles('${search.id}')" >View Profiles</button></td>
           `;
        })
    });
}

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
    fetch(`/api/v1/view-profiles/${searchId}`).then((response) => response.json()).then((data) => {
        console.log("profiles earches: ", data)
        const tableBody = document.getElementById("profiles-by-search-result");
        data.forEach((profile, index) => {
            const newRow = tableBody.insertRow();
            newRow.innerHTML = `
            <td>${index + 1}</td>
            <td>${profile.name}</td>
            <td>${profile.email}</td>
            <td>${profile.about}</td>
            <td>${profile.experience}</td>
            <td>${profile.education}</td>
            <td>${profile.isOpenToWork}</td>
            <td>${profile.link}</td>
            `;
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