console.log("FLIGHTS JS LOADED");
console.log(loggedUser);
console.log(loggedUser.role);
const loggedUser =
    JSON.parse(localStorage.getItem("loggedUser"));
if(!loggedUser){

    window.location.href =
        "/pages/login.html";
}

window.onload = () => {

    const createBtn =
        document.getElementById("createFlightBtn");

    if(
        loggedUser.role === "FLIGHT_ADMIN" ||
        loggedUser.role === "SYSTEM_ADMIN"
    ){
        createBtn.style.display = "inline-block";
    }

    loadFlights();
};

async function loadFlights() {

    console.log("Loading Flights...");
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

            <p>Status: ${flight.status}</p>

        </div>

        `;
    });

    document.getElementById("flights").innerHTML = html;
}

async function changeStatus(id, status) {

    const response =
        await fetch(

            `http://localhost:8080/flights/${id}/status?status=${status}`,

            {
                method: "PUT"
            }
        );

    if(response.ok){

        alert("Flight Status Updated");

        loadFlights();

    } else {

        alert("Update Failed");
    }
}

