async function loadSeats(){

    const flightId =
        document.getElementById("flightId").value;

    const type =
        document.getElementById("type").value;

    const response = await fetch(
        `http://localhost:8080/reservations/flight/${flightId}/available-seats?type=${type}`
    );

    const seats = await response.json();

    let html = "";

    seats.forEach(seat => {

        html += `
    <button
        class="seat available"
        onclick="selectSeat(${seat.row}, ${seat.column})"
    >
        ${seat.row}-${seat.column}
    </button>
`;
    });

    document.getElementById("seatMap").innerHTML = html;
    function selectSeat(row,column){

        alert(
            "Selected Seat: " + row + "-" + column
        );
    }
}