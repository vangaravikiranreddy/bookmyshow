document.addEventListener('DOMContentLoaded', function() {
    const moviesList = document.getElementById('moviesList');

    const moviesSection = document.getElementById('moviesSection');
    const detailsSection = document.getElementById('detailsSection');


    // Fetch movies data from the backend API
    fetch("http://localhost:8080/movies") // Replace with your actual backend URL
        .then(response => response.json())
        .then(data => {
            // Handle the response from the backend API

            console.log('API Response:', data);

            data.movies.forEach(movie => {

                console.log(movie)

                const movieItemTemplate = `
                                <li class="movie-item">
                                  <img class="movie-image" src="${movie.imageUrl}" alt="">
                                  <span class="movie-title">${movie.name}</span>
                                </li>
                                 `;

                // Create a temporary container element to hold the template
                const tempContainer = document.createElement('div');
                tempContainer.innerHTML = movieItemTemplate;

                // Get the elements from the template
                const movieItem = tempContainer.querySelector('.movie-item');
                const image = tempContainer.querySelector('.movie-image');

                moviesList.appendChild(movieItem);


                // Add a click event listener to the image
                image.addEventListener('click', () => {
                    const movieId = movie.id;

                    // Dispatch a custom event with the movie ID
                    const event = new CustomEvent('movieImageClicked', { detail: movieId });
                    document.dispatchEvent(event);
                });
            });
            // You can perform other actions based on the response
        })
        .catch(error => {
            console.error('API Error:', error);
        });
});

