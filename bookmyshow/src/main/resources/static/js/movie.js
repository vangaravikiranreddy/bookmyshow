document.addEventListener('DOMContentLoaded', function() {
    const moviesList = document.getElementById('moviesList');

    // Fetch movies data from the backend API
    fetch("http://localhost:8080/movies") // Replace with your actual backend URL
        .then(response => response.json())
        .then(data => {
            // Handle the response from the backend API

            console.log('API Response:', data);

            data.movies.forEach(movie => {
                const movieItem = document.createElement('li');

                const image = document.createElement('img');
                image.src = movie.imageUrl; // Replace with the actual image URL

                const movieName = document.createElement('span');
                movieName.textContent = movie.name;

                movieItem.appendChild(image);
                movieItem.appendChild(movieName);

                moviesList.appendChild(movieItem);
            });
            // You can perform other actions based on the response
        })
        .catch(error => {
            console.error('API Error:', error);
        });
});
