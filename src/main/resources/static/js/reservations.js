async function loadFlights() {

    const response =
        await fetch("http://localhost:8080/flights");

    const flights =
        await response.json();

    const select =
        document.getElementById("flightId");

    select.innerHTML = "";

    flights.forEach(flight => {

        select.innerHTML += `

            <option value="${flight.id}">
                ${flight.flightNumber} - ${flight.airplane}
            </option>

        `;
    });

    loadSeats();
}

async function loadSeats() {

    const flightId =
        document.getElementById("flightId").value;

    const type =
        document.getElementById("type").value;

    const response =
        await fetch(
            `http://localhost:8080/reservations/available-seats/${flightId}?type=${type}`
        );

    const seats =
        await response.json();

    const seatsDiv =
        document.getElementById("availableSeats");

    seatsDiv.innerHTML = "";

    seats.forEach(seat => {

        seatsDiv.innerHTML += `

            <button
                onclick="selectSeat(${seat.row}, ${seat.column})"
                class="seat-button">

                ${seat.row}-${seat.column}

            </button>

        `;
    });
}

let selectedRow = null;
let selectedColumn = null;

function selectSeat(row, column) {

    selectedRow = row;
    selectedColumn = column;

    alert(`Selected seat: ${row}-${column}`);
}

async function createReservation() {

    const userId =
        document.getElementById("userId").value;

    const flightId =
        document.getElementById("flightId").value;

    const type =
        document.getElementById("type").value;

    if (!selectedRow || !selectedColumn) {

        alert("Select a seat first");
        return;
    }

    const response =
        await fetch("http://localhost:8080/api/reservations", {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({

                userId: userId,
                flightId: flightId,
                type: type,
                seatRow: selectedRow,
                seatColumn: selectedColumn
            })
        });

    if (response.ok) {

        alert("Reservation created!");

        loadSeats();

    } else {

        alert("Reservation failed");
    }
}

window.onload = loadFlights;