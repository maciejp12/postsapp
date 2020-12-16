const url = 'http://127.0.0.1:8080/api/posts';

var getInput = () => {

    //TODO validate data

    let title = document.getElementById('title').value;
    let author = document.getElementById('author').value;
    let content = document.getElementById('content').value;
    
    return {'title' : title,
            'author' : author,
            'content' : content};
}

function submitform(){
    var xhr = new XMLHttpRequest();
    var json = getInput();

    xhr.open('POST', url, true);
    xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
    xhr.onreadystatechange = () => {
        window.location.href = '/';
    }

    xhr.send(JSON.stringify(json));
}