$(document).ready(() => {
    $('[data-bs-toggle=tooltip]').tooltip();

    new Choices('#selected-locations', {
        removeItemButton: true,
        maxItemCount:5,
        searchResultLimit:5,
        renderChoiceLimit:5
      });
      
    new Choices('#selected-industries', {
        removeItemButton: true,
        maxItemCount: 5,
        searchResultLimit: 5,
        renderChoiceLimit: 5,
    });
})