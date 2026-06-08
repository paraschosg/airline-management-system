async function register() {

    const requestBody = {

        username:
        document.getElementById("username").value,

        password:
        document.getElementById("password").value,

        email:
        document.getElementById("email").value,

        firstName:
        document.getElementById("firstName").value,

        lastName:
        document.getElementById("lastName").value,

        afm:
        document.getElementById("afm").value,

        address:
        document.getElementById("address").value,

        identityNumber:
        document.getElementById("identityNumber").value,

        role: "CUSTOMER"
    };

    const response =
        await fetch(
            "http://localhost:8080/users/register",
            {
                method: "POST",
                headers: {
                    "Content-Type":"application/json"
                },
                body: JSON.stringify(requestBody)
            }
        );

    if(response.ok){

        alert("Registration Success");

        window.location.href =
            "/pages/login.html";

    } else {

        alert("Registration Failed");
    }
}