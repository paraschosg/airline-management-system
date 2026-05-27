async function login() {

    const username =
        document.getElementById("username").value;

    const password =
        document.getElementById("password").value;

    const response =
        await fetch(
            "http://localhost:8080/users/login",
            {
                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify({

                    username: username,
                    password: password
                })
            }
        );

    console.log(response);
    if(response.ok){

        const user =
            await response.json();

        console.log(user);

        localStorage.setItem(
            "loggedUser",
            JSON.stringify(user)
        );

        console.log(
            localStorage.getItem("loggedUser")
        );

        alert("Login Success");

        window.location.href =
            "/pages/reservations.html";

    } else {

        alert("Wrong username or password");
    }
}