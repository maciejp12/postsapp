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

    getAllRequest.open('GET', postsUrl + '/head', true);
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
    postsList.id = 'posts_list';

    // diffrent color for even and odd posts in order
    
    let i = 0;

    for (const post in postsArray) {
        if (postsArray[post].content.length == 127) {
            postsArray[post].content += '...';
        }

        let postHTML = createNewPost(postsArray[post]);
        postsList.appendChild(postHTML);
        postHTML.style.backgroundColor = postBgColors[i % postBgColors.length]
        i++;
    }


    let container = document.getElementById('posts_container');
    container.appendChild(postsList);
}

const createNewPost = (postJSON) => {
    let post = document.createElement('div');

    post.classList.add('post-element');

    let title = document.createElement('button');
    let info = document.createElement('p');
    let content = document.createElement('p');

    title.classList.add('post-element-title');
    info.classList.add('post-element-info');
    content.classList.add('post-element-content')
    
    title.onclick = () => {
        showPost(postJSON.postId)
    }

    title.innerHTML = postJSON.title;
    info.innerHTML = 'Created by <a href="#" class="post-element-author">' + postJSON.author + '</a> on ' + postJSON.creationDate;
    content.innerHTML = postJSON.content;

    post.appendChild(title);
    post.append(info);
    post.appendChild(content);

    return post;
}

const disposePostsList = () => {
    let list = document.getElementById('posts_list');
    if (list != null) {
        list.remove();
    }
}

const addReturnButton = () => {
    var returnBtn = document.createElement('button');
    returnBtn.innerHTML = 'return';
    returnBtn.onclick = () => {
        //TODO clean first
        document.getElementById('cur_post').remove();
        returnBtn.remove();
        getAllPosts();
    }

    document.getElementById('posts_container').appendChild(returnBtn);
}

const getPostById = (id) => {
    var getByIdRequest = new XMLHttpRequest();

    getByIdRequest.open('GET', postsUrl + '/' + id, true);
    getByIdRequest.send(null);

    getByIdRequest.onreadystatechange = () => {
        if (getByIdRequest.readyState == XMLHttpRequest.DONE) {
            var json = JSON.parse(getByIdRequest.responseText);
            loadPost(json);
        }
    }
}

var showPost = (id) => {
    disposePostsList();
    addReturnButton();
    getPostById(id);
}

var loadPost = (postJSON) => {
    console.log(postJSON);

    let container = document.getElementById('posts_container');
    let postElement = document.createElement('div');
    postElement.id = 'cur_post';

    postElement.classList.add('post-element');
    postElement.style.backgroundColor = postBgColors[0];

    let title = document.createElement('p');
    let info = document.createElement('p');
    let content = document.createElement('p');

    title.classList.add('post-element-title');
    info.classList.add('post-element-info');
    content.classList.add('post-element-content');

    title.innerHTML = postJSON.title;
    info.innerHTML = 'Created by <a href="#" class="post-element-author">' + postJSON.author + '</a> on ' + postJSON.creationDate;
    content.innerHTML = postJSON.content;

    postElement.appendChild(title);
    postElement.appendChild(info);
    postElement.appendChild(content);

    container.appendChild(postElement);
    
}