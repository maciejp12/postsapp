const url = 'http://127.0.0.1:8080/api/posts';

var getInput = () => {


    let title = document.getElementById('title').value;
    let content = document.getElementById('content').value;
    
    return {'title' : title,
            'content' : content};
}

const minTitleLen = 3;
const maxTitleLen = 64;

const minContentLen = 3;
const maxContentLen = 4096;

var validateTitle = (title) => {
    let len = title.length;
    return len >= minTitleLen && len <= maxTitleLen;
}

var validateContent = (content) => {
    let len = content.length;
    return len >= minContentLen && len <= maxContentLen;
}

var validateInput = (input) => {
    let errorMessage = document.getElementById('post_error');

    if (!validateTitle(input.title)) {
        errorMessage.innerHTML = 'title must be between 3 and 64 characters long';
        return false;
    }

    if (!validateContent(input.content)) {
        errorMessage.innerHTML = 'content must be between 3 and 4096 characters long';
        return false;
    }

    return true;
}

var submitform = () => {
    var addPostRequest = new XMLHttpRequest();
    var json = getInput();

    if (!validateInput(json)) {
        return;
    }

    addPostRequest.open('POST', url, true);
    addPostRequest.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
    
    addPostRequest.onreadystatechange = () => {
        if (addPostRequest.readyState == XMLHttpRequest.DONE) {
            var json = JSON.parse(addPostRequest.responseText);
            if (json.valid) {
                window.location.href = '/';
            } else {
                var errormessage = document.getElementById('post_error');
                errormessage.innerHTML = json.message;
            }
        }
    }

    addPostRequest.send(JSON.stringify(json));
}