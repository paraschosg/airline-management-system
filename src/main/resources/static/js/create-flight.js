const loggedUser =
    JSON.parse(localStorage.getItem("loggedUser"));

if(!loggedUser){

    window.location.href =
        "/pages/login.html";
}

if(
    loggedUser.role !== "FLIGHT_ADMIN" &&
    loggedUser.role !== "SYSTEM_ADMIN"
){
    alert("Access Denied");

    window.location.href =
        "/pages/flights.html";
}

document
    .getElementById("flightForm")
    .addEventListener("submit", createFlight);

async function createFlight(e){

    e.preventDefault();

    const flight = {

        flightNumber:
        document.getElementById("flightNumber").value,

        airplane:
        document.getElementById("airplane").value,

        flightDate:
        document.getElementById("flightDate").value,

        flightTime:
        document.getElementById("flightTime").value,

        totalSeats:
            parseInt(
                document.getElementById("totalSeats").value
            ),

        totalRows:
            parseInt(
                document.getElementById("totalRows").value
            ),

        seatsPerRow:
            parseInt(
                document.getElementById("seatsPerRow").value
            ),

        businessRows:
            parseInt(
                document.getElementById("businessRows").value
            )
    };

    const response =
        await fetch(

            "http://localhost:8080/flights",

            {
                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(flight)
            }
        );

    if(response.ok){

        alert("Flight Created");

        window.location.href =
            "/pages/flights.html";

    }else{

        alert("Create Failed");
    }
}