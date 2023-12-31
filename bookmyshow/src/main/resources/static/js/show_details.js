// Get the movie ID from the URL parameter
const params = new URLSearchParams(window.location.search);
const clickedMovieId = parseInt(params.get('movieId'));


// Construct the URL for the API endpoint with query parameters
const apiUrl = `http://localhost:8080/shows?id=${clickedMovieId}`;


const requestOptions = {
    method: 'POST'
};

// Make a POST request to the API
fetch(apiUrl, requestOptions)
    .then(response => response.json())
    .then(data => {
        // Handle the response data
        console.log('API Response:', data);
        displayResponse(data);

    })
    .catch(error => {
        // Handle errors
        console.error('API Error:', error);
    });


function displayResponse(data) {
    console.log('User Id: ',userId)
    const responseContainer = document.getElementById('responseContainer');
    responseContainer.innerHTML = ''; // Clear existing content


    const showImage = `
          <ul class="image">
                 <img class="movie-image" src="${data.movieUrl}" alt="">  
           </ul>                     
         `;

    // Create a temporary container element to hold the template
    const tempContainer = document.createElement('div');
    tempContainer.innerHTML = showImage;

    // Get the elements from the template
    const movieImage = tempContainer.querySelector('.image');

    responseContainer.appendChild(movieImage);

    data.shows.forEach(show => {

        const show_seats = document.createElement('div');
        show_seats.className = 'show_seats';

        const showInfo = `
             <ul class="show">
                <p class="show_id">Show ID: ${show.showId}</p>
                <p class="show_start_time">Start Time: ${show.startTime}</p>
                <p class="show_end_time">End Time: ${show.endTime}</p>
                <p class="screen_name">Screen Name: ${show.screenName}</p>
                <p class="theater_name">Theater Name: ${show.theaterName}</p>
                <p class="region_name">Region Name: ${show.regionName}</p>
             </ul>   
        `;

        tempContainer.innerHTML = showInfo;
        const shows = tempContainer.querySelector('.show');

        const show_list = document.createElement('div');
        show_list.className = 'show_list';
        show_list.appendChild(shows)

        const seat_table = document.createElement('table');
        seat_table.className = 'seat_table';

        // Create table headers
        const tableHeader = `
            <tr>
                <th>Row</th>
                <th>Column</th>
                <th>Seat Number</th>
                <th>Seat Type</th>
            </tr>
        `;
        seat_table.innerHTML = tableHeader;

        show.seats.forEach(seat => {
            const tableRow = document.createElement('tr');

            const rowCell = document.createElement('td');
            rowCell.textContent = seat.rowVal;
            tableRow.appendChild(rowCell);

            const colCell = document.createElement('td');
            colCell.textContent = seat.colVal;
            tableRow.appendChild(colCell);

            const seatNumberCell = document.createElement('td');
            seatNumberCell.textContent = seat.seatNumber;
            tableRow.appendChild(seatNumberCell);

            const seatNameCell = document.createElement('td');
            seatNameCell.textContent = seat.seatName;
            tableRow.appendChild(seatNameCell);

            // Attach a click event listener to the table row
            tableRow.addEventListener('click', () => {
                console.log('Seat Id: ',seat.seatId,' Show Id: ',show.showId,' User Id',userId);
                const seatIds = [seat.seatId];
                book(seatIds, show.showId, userId);
            });


            seat_table.appendChild(tableRow);
        });

        show_seats.appendChild(show_list);
        show_seats.appendChild(seat_table);

        responseContainer.appendChild(show_seats);
   });
}

function book(seatIds, showId,userId) {
     const requestData = {
         userId : userId,
         seatIds : seatIds,
         showId : showId
     }

     fetch("http://localhost:8080/book", {
         method: 'POST',
         headers: {
             'Content-Type': 'application/json'
         },
         body: JSON.stringify(requestData)
     })
         .then(response => response.json())
         .then(data => {
             console.log('SUCCESS',data);
             displayPopup(data);
         })
         .catch(error => {
             console.error('FAILURE');
         });
}

function displayPopup(data) {
    // Create a div element for the popup
    const popup = document.createElement('div');
    popup.classList.add('popup');

    // Set the content of the popup
    popup.innerHTML = `
    <div class="popup-content">
      <h2>${data.movieName}</h2>
      <img src="${data.movieImageUrl}" alt="">
      <p>Theater: ${data.theaterName}</p>
      <p>Screen: ${data.screenNumber}</p>
      <p>Located at: ${data.locatedAt}</p>
      <p>Booking ID: ${data.bookingId}</p>
      <p>Amount: ${data.amount}</p>
      <p>Booked At: ${data.bookedAt}</p>
    </div>
  `;

    // Append the popup to the body
    document.body.appendChild(popup);

    // Close the popup when clicked anywhere outside it
    popup.addEventListener('click', (event) => {
            popup.remove();
    });
}