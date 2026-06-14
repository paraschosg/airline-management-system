const loggedUser = JSON.parse(localStorage.getItem("loggedUser"));

if (!loggedUser) {
    window.location.href = "/pages/login.html";
}

const isFlightAdmin =
    loggedUser.role === "FLIGHT_ADMIN" || loggedUser.role === "SYSTEM_ADMIN";

window.onload = loadFlights;

async function loadFlights() {

    const response = await fetch("/flights");
    const flights = await response.json();

    let html = "";

    flights.forEach(flight => {

        // Κουμπιά αλλαγής κατάστασης — μόνο για admins, βάσει επιτρεπόμενων μεταβάσεων
        let actions = "";
        if (isFlightAdmin) {
            const s = flight.status;
            if (s === "CREATED") {
                actions += `<button onclick="changeStatus(${flight.id}, 'STAFFED')">→ STAFFED</button> `;
                actions += `<button onclick="changeStatus(${flight.id}, 'CANCELLED')">→ CANCELLED</button> `;
            } else if (s === "STAFFED") {
                actions += `<button onclick="changeStatus(${flight.id}, 'COMPLETED')">→ COMPLETED</button> `;
                actions += `<button onclick="changeStatus(${flight.id}, 'CANCELLED')">→ CANCELLED</button> `;
            }
            actions += `<button onclick="deleteFlight(${flight.id})" style="background:#cc0000;">Delete</button>`;
        }

        html += `
        <div class="card">
            <h3>${flight.flightNumber}</h3>
            <p>Airplane: ${flight.airplane}</p>
            <p>Date: ${flight.flightDate}</p>
            <p>Time: ${flight.flightTime}</p>
            <p>Total Seats: ${flight.totalSeats}</p>
            <p>Status: <b>${flight.status}</b></p>
            ${actions ? `<div style="margin-top:10px;">${actions}</div>` : ""}
        </div>`;
    });

    document.getElementById("flights").innerHTML = html || "<p>Δεν υπάρχουν πτήσεις.</p>";
}

async function changeStatus(id, status) {

    const response = await fetch(`/flights/${id}/status?status=${status}`, {
        method: "PUT"
    });

    if (response.ok) {
        loadFlights();
    } else {
        const err = await response.text();
        alert("Αποτυχία αλλαγής κατάστασης: " + err);
    }
}

async function deleteFlight(id) {

    if (!confirm("Διαγραφή πτήσης; Θα διαγραφούν και οι κρατήσεις της.")) return;

    const response = await fetch(`/flights/${id}`, { method: "DELETE" });

    if (response.ok) {
        loadFlights();
    } else {
        alert("Αποτυχία διαγραφής.");
    }
}