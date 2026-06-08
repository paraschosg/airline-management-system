const loggedUser =
    JSON.parse(localStorage.getItem("loggedUser"));

if (!loggedUser) {

    window.location.href =
        "/pages/login.html";
}

async function loadProfile() {

    const response =
        await fetch(
            `http://localhost:8080/users/${loggedUser.username}`
        );

    const user =
        await response.json();

    document.getElementById("firstName").value =
        user.firstName || "";

    document.getElementById("lastName").value =
        user.lastName || "";

    document.getElementById("email").value =
        user.email || "";

    document.getElementById("address").value =
        user.address || "";

    document.getElementById("afm").value =
        user.afm || "";

    document.getElementById("employeeCode").value =
        user.employeeCode || "";

    document.getElementById("identityNumber").value =
        user.identityNumber || "";
}

document.getElementById("profileForm")
    .addEventListener("submit", updateProfile);

async function updateProfile(e) {

    e.preventDefault();

    const request = {

        email:
        document.getElementById("email").value,

        firstName:
        document.getElementById("firstName").value,

        lastName:
        document.getElementById("lastName").value,

        address:
        document.getElementById("address").value,

        afm:
        document.getElementById("afm").value,

        employeeCode:
        document.getElementById("employeeCode").value,

        identityNumber:
        document.getElementById("identityNumber").value,

        role:
        loggedUser.role,

        active:
        loggedUser.active
    };

    const response =
        await fetch(

            `http://localhost:8080/users/${loggedUser.id}`,

            {
                method: "PUT",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(request)
            }
        );

    if (response.ok) {

        alert("Profile Updated");

    } else {

        alert("Update Failed");
    }
}

window.onload = loadProfile;