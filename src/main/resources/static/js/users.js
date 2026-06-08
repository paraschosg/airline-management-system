const loggedUser =
    JSON.parse(localStorage.getItem("loggedUser"));

if (!loggedUser) {

    window.location.href =
        "/pages/login.html";
}

if (loggedUser.role !== "SYSTEM_ADMIN") {

    alert("Access Denied");

    window.location.href =
        "/pages/reservations.html";
}

async function loadUsers() {

    const response =
        await fetch("http://localhost:8080/users");

    const users =
        await response.json();

    renderUsers(users);
}

function renderUsers(users) {

    const tbody =
        document.getElementById("usersBody");

    tbody.innerHTML = "";

    users.forEach(user => {

        tbody.innerHTML += `

            <tr>

                <td>${user.id}</td>

                <td>${user.username ?? ""}</td>

                <td>${user.email ?? ""}</td>

                <td>${user.role ?? ""}</td>

                <td>${user.active}</td>

                <td>

                    <button
                        onclick="activateUser(${user.id})">

                        Activate

                    </button>

                    <button
                        onclick="deactivateUser(${user.id})">

                        Deactivate

                    </button>

                    <button
                        onclick="deleteUser(${user.id})">

                        Delete

                    </button>

                </td>

            </tr>

        `;
    });
}

async function searchUsers() {

    const keyword =
        document.getElementById("searchInput").value.trim();

    if (keyword === "") {

        loadUsers();
        return;
    }

    const response =
        await fetch(
            `http://localhost:8080/users/search?keyword=${keyword}`
        );

    const users =
        await response.json();

    renderUsers(users);
}

async function activateUser(id) {

    const response =
        await fetch(

            `http://localhost:8080/users/${id}/activate`,

            {
                method: "PUT"
            }
        );

    if (response.ok) {

        alert("User Activated");

        loadUsers();
    }
}

async function deactivateUser(id) {

    const response =
        await fetch(

            `http://localhost:8080/users/${id}/deactivate`,

            {
                method: "PUT"
            }
        );

    if (response.ok) {

        alert("User Deactivated");

        loadUsers();
    }
}

async function deleteUser(id) {

    const answer =
        confirm("Delete this user?");

    if (!answer) {
        return;
    }

    const response =
        await fetch(

            `http://localhost:8080/users/${id}`,

            {
                method: "DELETE"
            }
        );

    if (response.ok) {

        alert("User Deleted");

        loadUsers();

    } else {

        alert("Delete Failed");
    }
}

window.onload = loadUsers;