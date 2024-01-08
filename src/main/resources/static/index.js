function userLogin() {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "http://127.0.0.1:8080/auth/login", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onload = function() {
        if (xhr.status === 200) {
            console.log(xhr.responseText);
            alert(xhr.responseText);
        } else {
            console.error('Error: ' + xhr.status);
            alert(xhr.status);
        }
    };
    xhr.onerror = function(e) {
        console.error('Error: ' + e);
        alert(xhr.status);
    };
    var loginAccount = document.getElementById('loginAccount').value;
    var password = document.getElementById('password').value;
    data = {
        "loginAccount": loginAccount,
        "password": password
    }
    xhr.send(JSON.stringify(data));
}
