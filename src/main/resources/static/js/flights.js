console.log("FLIGHTS JS LOADED");

async function loadFlights() {

    const response =
        await fetch("http://localhost:8080/flights");

    const flights =
        await response.json();

    console.log(flights);

    let html = "";

    flights.forEach(flight => {

        html += `

        <div class="card">

            <h3>${flight.flightNumber}</h3>

            <p>Airplane: ${flight.airplane}</p>

            <p>Date: ${flight.flightDate}</p>

            <p>Time: ${flight.flightTime}</p>

            <p>Total Seats: ${flight.totalSeats}</p>

        </div>

        `;
    });

    document.getElementById("flights").innerHTML = html;
}