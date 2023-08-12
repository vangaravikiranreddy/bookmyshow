document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    const requestData = {
        email: email,
        password: password
    };

    // Call your backend API here
    fetch("http://localhost:8080/login", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestData)
    })
        .then(response => response.json())
        .then(data => {
            // Handle the response from the backend API

            console.log('API Response:', data);
            const userAuthenticated = data.responseStatus;
            if (userAuthenticated == "SUCCESS") {
                console.log('valid Password')
                window.location.href = '../html/movie.html'; // Redirect to the movies page
            } else {
                console.log('Invalid Password')
            }
            // You can redirect the user to a different page or perform other actions based on the response
        })
        .catch(error => {
            console.error('API Error:', error);
        });
});
