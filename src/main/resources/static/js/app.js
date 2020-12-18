const postsUrl = 'http://127.0.0.1:8080/api/posts';

const sendGetRequest = () => {
    getRequest.open('GET', postsUrl, true);
    getRequest.send(null);
}

const getRequest = new XMLHttpRequest();  
getRequest.onreadystatechange = () => {        
    if (getRequest.readyState == XMLHttpRequest.DONE) {
        console.log(getRequest.responseText);
    }
}

const getAllPosts = () => {
    var getAllRequest = new XMLHttpRequest();

    getAllRequest.open('GET', postsUrl, true);
    getAllRequest.send(null);

    getAllRequest.onreadystatechange = () => {
        if (getAllRequest.readyState == XMLHttpRequest.DONE) {
            var json = JSON.parse(getAllRequest.responseText);
            createPostsList(json)        
        }
    }
}

const postBgColors = ['#e0e9ff', '#c9d9ff'];

const createPostsList = (postsArray) => {
    let postsList = document.createElement('div');
    postsList.classList.add('post-list');

    // diffrent color for even and odd posts in order
    let i = 0;

    for (const post in postsArray) {
        let postHTML = createNewPost(postsArray[post]);
        postsList.appendChild(postHTML);
        postHTML.style.backgroundColor = postBgColors[i % postBgColors.length]
        i++;
    }


    let container = document.getElementById('posts_container');
    container.appendChild(postsList);
}


const createNewPost = (postJSON) => {
    var post = document.createElement('div');

    post.classList.add('post-element');

    var title = document.createElement('h2');
    var author = document.createElement('h5');
    var content = document.createElement('p');
    var creationDate = document.createElement('p');

    title.innerHTML = postJSON.title;
    author.innerHTML = postJSON.author;
    creationDate.innerHTML = postJSON.creationDate;
    content.innerHTML = postJSON.content;

    post.appendChild(title);
    post.appendChild(author);
    post.appendChild(creationDate)
    post.appendChild(content);

    return post;
}