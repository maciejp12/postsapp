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

// input validation

const minNameLen = 3;
const maxNameLen = 64;

const minPasswordLen = 6;
const maxPasswordLen = 64;

var validateInput = (input) => {
    var errormessage = document.getElementById('errormessage');

    if (!validateName(input.name)) {
        errormessage.innerHTML = 'name must be between 3 and 63 characters long';
        return false;
    }

    if (!validatePassword(input.password)) {
        errormessage.innerHTML = 'password must be between 6 and 64 characters long';
        return false;
    }

    if (!validateEmail(input.email)) {
        errormessage.innerHTML = 'email is not valid';
        return false;
    }

    return true;
}



var validateName = (name) => {
    let len = name.length
    return len >= minNameLen && len <= maxNameLen;
}

var validatePassword = (password) => {
    let len = password.length
    return len >= minPasswordLen && len <= maxPasswordLen;
}

var validateEmail = (email) => {
    const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email.toLowerCase());
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

    if(!validateInput(json)) {
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
                var errormessage = document.getElementById('errormessage');
                errormessage.innerHTML = json.message;
            }     
        }
    }

    

    xhr.send(JSON.stringify(json));
}