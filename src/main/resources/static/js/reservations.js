let selectedRow = null;
let selectedColumn = null;

async function loadFlights() {

    console.log("LOAD FLIGHTS");

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

    console.log("LOAD SEATS");

    const flightId =
        document.getElementById("flightId").value;

    const type =
        document.getElementById("type").value;

    console.log("FLIGHT =", flightId);
    console.log("TYPE =", type);

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

    if(type === "ECONOMY") {

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

        button.className =
            "seat-button";

        button.style.padding = "10px";
        button.style.background = "green";
        button.style.color = "white";
        button.style.border = "none";
        button.style.cursor = "pointer";

        button.onclick = function () {

            selectedRow = seat.row;
            selectedColumn = seat.column;

            document.getElementById("selectedRow").value =
                seat.row;

            document.getElementById("selectedColumn").value =
                seat.column;

            alert(
                "Selected Seat: " +
                seat.row +
                "-" +
                seat.column
            );
        };

        container.appendChild(button);
    });
}

async function createReservation() {

    const userId =
        document.getElementById("userId").value;

    const flightId =
        document.getElementById("flightId").value;

    const type =
        document.getElementById("type").value;

    console.log("USER ID =", userId);
    console.log("FLIGHT ID =", flightId);
    console.log("TYPE =", type);

    if(type !== "ECONOMY") {

        if(!selectedRow || !selectedColumn) {

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
                    "Content-Type":
                        "application/json"
                },

                body:
                    JSON.stringify(requestBody)
            }
        );

    if(response.ok) {

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

window.onload = function () {

    loadFlights();
};
async function cancelReservation(id) {

    console.log("CANCEL ID =", id);

    const response = await fetch(
        `http://localhost:8080/reservations/cancel/${id}`,
        {
            method: "PUT"
        }
    );

    console.log(response);

    if(response.ok){

        alert("Reservation cancelled");

        loadReservations();

    } else {

        alert("Cancel failed");
    }
}