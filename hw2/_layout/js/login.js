document.querySelector('#task4 form').addEventListener('submit', (e) => {
    e.preventDefault();

    const login = document.querySelector('#handleOrEmail').value;
    const password = document.querySelector('#password').value;
    const remember = document.querySelector('#remember').checked ? 'Yes' : 'No';

    alert(
        'Form:\n' +
        '--------------------------------\n' +
        'Handle/Email: ' + login + '\n' +
        'Password: ' + password + '\n' +
        'Remember me: ' + remember
    );
    location.reload();
});