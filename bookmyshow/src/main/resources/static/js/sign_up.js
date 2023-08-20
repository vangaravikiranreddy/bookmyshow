// sign_up.js
document.getElementById("signUpForm").addEventListener("submit", function(event) {
    event.preventDefault();

    // Collect user inputs
    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const requestData = {
        name : name,
        email: email,
        password: password
    };

    // Perform backend registration logic here (e.g., sending data to the server)
    // Call your backend API here
    fetch("http://localhost:8080/signup", {
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
        })
        .catch(error => {
            console.error('API Error:', error);
        });

    // Display success message and hide the sign-up form
    document.getElementById("signUpForm").style.display = "none";
    document.getElementById("successMessage").style.display = "block";
});
