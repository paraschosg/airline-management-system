async function loadReservations() {

    // const response =
    //     await fetch("http://localhost:8080/reservations");

    const loggedUser =
        JSON.parse(localStorage.getItem("loggedUser"));

    const response =
        await fetch(
            `http://localhost:8080/reservations/user/${loggedUser.id}`
        );

    const reservations =
        await response.json();

    console.log(reservations);

    const tbody =
        document.getElementById("reservationsBody");

    tbody.innerHTML = "";

    reservations.forEach(reservation => {

        const username =
            reservation.user
                ? reservation.user.username
                : "No User";

        const flightNumber =
            reservation.flight
                ? reservation.flight.flightNumber
                : "No Flight";

        const seat =
            reservation.seatRow && reservation.seatColumn
                ? `${reservation.seatRow}-${reservation.seatColumn}`
                : "-";

        tbody.innerHTML += `

            <tr>

                <td>${reservation.id}</td>

                <td>${username}</td>

                <td>${flightNumber}</td>

                <td>${seat}</td>

                <td>${reservation.type}</td>

                <td>${reservation.status}</td>

                <td>
                
                    <button
                        onclick="cancelReservation(${reservation.id})"
                        class="cancel-btn">
                
                        Cancel
                
                    </button>
                
                </td>

            </tr>

        `;
    });
}

window.onload = loadReservations;

async function cancelReservation(id) {

    const response =
        await fetch(

            `http://localhost:8080/reservations/cancel/${id}`,

            {
                method: "PUT"
            }
        );

    if(response.ok){

        alert("Reservation Cancelled");

        loadReservations();

    } else {

        alert("Cancel Failed");
    }
}