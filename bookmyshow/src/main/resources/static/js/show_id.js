// Listen for the custom event
document.addEventListener('movieImageClicked', event => {
    const clickedMovieId = event.detail;

    // Construct the URL for the second HTML file with the movie ID as a parameter
    const secondPageUrl = `show.html?movieId=${clickedMovieId}`;

    // Navigate to the second HTML page
    window.location.href = secondPageUrl;
});


