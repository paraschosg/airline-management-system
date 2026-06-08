const loggedUser =
    JSON.parse(localStorage.getItem("loggedUser"));
if(user.role !== "ADMIN"){

    const usersMenu =
        document.getElementById("usersMenu");

    if(usersMenu){

        usersMenu.style.display = "none";
    }
}
console.log(loggedUser);

if (!loggedUser) {

    window.location.href =
        "/pages/login.html";
}

let selectedRow = null;
let selectedColumn = null;

window.onload = function () {

    document.getElementById("loggedUser").innerHTML =
        "Welcome " + loggedUser.username;

    loadFlights();
};

async function loadFlights() {

    const response =
        await fetch("http://localhost:8080/flights");

    const flights =
        await response.json();

    console.log(flights);

    const select =
        document.getElementById("flightId");

    select.innerHTML = "";

    flights.forEach(flight => {

        const option =
            document.createElement("option");

        option.value = flight.id;

        option.text =
            flight.flightNumber +
            " | " +
            flight.flightDate;

        select.appendChild(option);
    });

    loadSeats();
}

async function loadSeats() {

    const flightId =
        document.getElementById("flightId").value;

    const type =
        document.getElementById("type").value;

    console.log(flightId);
    console.log(type);

    if (!flightId) {
        return;
    }

    const response =
        await fetch(
            `http://localhost:8080/reservations/flight/${flightId}/available-seats?type=${type}`
        );

    const seats =
        await response.json();

    console.log(seats);

    const container =
        document.getElementById("seatsContainer");

    container.innerHTML = "";

    if (type === "ECONOMY") {

        container.innerHTML =
            "<p>Economy reservations do not require seat selection</p>";

        return;
    }

    seats.forEach(seat => {

        const button =
            document.createElement("button");

        button.innerText =
            "Row " +
            seat.row +
            " Seat " +
            seat.column;

        button.style.padding = "10px";
        button.style.margin = "5px";
        button.style.background = "green";
        button.style.color = "white";
        button.style.border = "none";
        button.style.cursor = "pointer";

        button.onclick = function () {

            selectedRow = seat.row;
            selectedColumn = seat.column;

            alert(
                "Selected Seat: " +
                selectedRow +
                "-" +
                selectedColumn
            );
        };

        container.appendChild(button);
    });
}

async function createReservation() {

    const userId =
        loggedUser.id;

    const flightId =
        document.getElementById("flightId").value;

    const type =
        document.getElementById("type").value;

    if (type !== "ECONOMY") {

        if (!selectedRow || !selectedColumn) {

            alert("Please select a seat");

            return;
        }
    }

    const requestBody = {

        userId: Number(userId),

        flightId: Number(flightId),

        type: type,

        seatRow:
            type === "ECONOMY"
                ? null
                : selectedRow,

        seatColumn:
            type === "ECONOMY"
                ? null
                : selectedColumn
    };

    console.log(requestBody);

    const response =
        await fetch(
            "http://localhost:8080/reservations",
            {
                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(requestBody)
            }
        );

    console.log(response);

    if (response.ok) {

        alert("Reservation Created");

        selectedRow = null;
        selectedColumn = null;

        loadSeats();

    } else {

        const error =
            await response.text();

        console.log(error);

        alert("Reservation Failed");
    }
}

function logout() {

    localStorage.removeItem("loggedUser");

    alert("Logged out");

    window.location.href =
        "/pages/login.html";
}