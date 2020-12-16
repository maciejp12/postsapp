const url = 'http://127.0.0.1:8080/api/user/process_register';

var getRegisterInput = () => {
    //TODO validate data

    let email = document.getElementById('email').value;
    let name = document.getElementById('name').value;
    let password = document.getElementById('password').value;

    return {'email' : email,
            'name' : name,
            'password' : password
    }
}

function submitRegisterForm(){
    var xhr = new XMLHttpRequest();
    var json = getRegisterInput();

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