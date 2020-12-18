const url = 'http://127.0.0.1:8080/api/user/process_register';
const passwdNotMatchMessage = 'password doesn\'t match';
const passwdMatchMessage = 'matching';

var getRegisterInput = () => {
    //TODO validate data

    let email = document.getElementById('email').value;
    let name = document.getElementById('name').value;
    let password = document.getElementById('password').value;
    let passwordConfirm = document.getElementById('confirm_password').value;

    return {'email' : email,
            'name' : name,
            'password' : password,
            'password_confirm' : passwordConfirm
    }
}

var checkPassword = () => {
    let message = document.getElementById('password_message');
    let password = document.getElementById('password');
    let passwordConfirm = document.getElementById('confirm_password');

    if (password.value == passwordConfirm.value) {
        message.innerHTML = passwdMatchMessage;
        message.style.color = 'green';
    } else {
        message.innerHTML = passwdNotMatchMessage;
        message.style.color = 'red';
    }
}

var submitRegisterForm = () => {
    var xhr = new XMLHttpRequest();
    var json = getRegisterInput();

    if (json.password != json.password_confirm) {
        let passwordMessage = document.getElementById('password_message');
        passwordMessage.innerHTML = passwdNotMatchMessage;
        passwordMessage.style.color = 'red';
        return;
    }

    xhr.open('POST', url, true);
    xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
    xhr.onreadystatechange = () => {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            var json = JSON.parse(xhr.responseText);
            if (json.valid) {
                window.location.href = '/register_success';
            } else {
                var errormessage = document.getElementById("errormessage");
                errormessage.innerHTML = json.message;
            }     
        }
    }

    

    xhr.send(JSON.stringify(json));
}