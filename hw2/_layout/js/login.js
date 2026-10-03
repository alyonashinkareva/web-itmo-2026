const loginForm = document.querySelector(".login-form-box form");

loginForm.addEventListener("submit", (event) => {
    event.preventDefault();

    const handleOrEmail = document.getElementById("handleOrEmail").value;
    const password = document.getElementById("password").value;
    const remember = document.getElementById("remember").checked;

    alert(`Handle/Email: ${handleOrEmail}\nPassword: ${password}\nRemember me: ${remember ? "yes" : "no"}`);
});
