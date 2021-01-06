const postsUrl = 'http://127.0.0.1:8080/api/posts';
const commentsUrl = 'http://127.0.0.1:8080/api/comments';

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
const titleOnMouseOverColor = '#111111';

const createPostsList = (postsArray) => {
    let postsList = document.createElement('div');
    postsList.classList.add('post-list');
    postsList.id = 'posts_list';

    // diffrent color for even and odd posts in order
    
    let i = 0;

    for (const post in postsArray) {
        if (postsArray[post].content.length == 64) {
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
    
    title.onmouseover = () => {
        title.style.color = titleOnMouseOverColor;
    }

    title.onmouseout = () => {
        title.style.color = null;
    }

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
        document.getElementById('comments_list').remove();
        document.getElementById('comments_container').style.display = 'none';
        returnBtn.remove();
        curPostId = null;
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
            loadComments(id);
        }
    }
}

var curPostId = null;

var showPost = (id) => {
    disposePostsList();
    addReturnButton();
    curPostId = id;
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

var loadComments = (id) => {
    document.getElementById('comments_container').style.display = 'block';

    var getCommentsByIdRequest = new XMLHttpRequest();

    getCommentsByIdRequest.open('GET', commentsUrl + '/parent/' + id, true);
    getCommentsByIdRequest.send(null);

    getCommentsByIdRequest.onreadystatechange = () => {
        if (getCommentsByIdRequest.readyState == XMLHttpRequest.DONE) {
            var json = JSON.parse(getCommentsByIdRequest.responseText);
            console.log(json);
            createCommentsList(json);
        }
    }
}

var isAuth = () => {
    let authDiv = document.getElementById('add_com_auth');
    return authDiv != null;   
}

var createCommentsList = (comments) => {
    var commentsList = document.createElement('div');
    commentsList.classList.add('comments-list');
    commentsList.id = 'comments_list';

    for (const i in comments) {
        let comment = comments[i];

        let commentContainer = document.createElement('div');
        commentContainer.classList.add('comment-container');

        let commentScore = document.createElement('div');
        commentScore.classList.add('comment-score');

        let commentPoints = document.createElement('h1');
        commentPoints.classList.add('comment-score-points');
        commentPoints.innerHTML = comment.points;

        let commentElement = document.createElement('div');
        commentElement.classList.add('comment-element');

        let commentInfo = document.createElement('p');
        commentInfo.classList.add('comment-info');
        commentInfo.innerHTML = '<b><a class="undecorated" href="#">' + comment.author + '</a></b>' + ' , ' + comment.creationDate;

        let commentText = document.createElement('p');
        commentText.classList.add('comment-text');
        commentText.innerHTML = comment.text;

        commentScore.appendChild(commentPoints);

        commentElement.appendChild(commentInfo);
        commentElement.appendChild(commentText);

        if (isAuth()) {

            let commentPlus = document.createElement('button');
            commentPlus.classList.add('comment-score-btn');
            commentPlus.innerHTML = '+';
            commentPlus.onclick = () => {
                updateCommentPoints(comment.commentId, 1)
            }

            let commentMinus = document.createElement('button');
            commentMinus.classList.add('comment-score-btn');
            commentMinus.innerHTML = '-';
            commentMinus.onclick = () => {
                updateCommentPoints(comment.commentId, -1)
            }

            commentPoints.remove();
            commentScore.appendChild(commentPlus);
            commentScore.appendChild(commentPoints);
            commentScore.appendChild(commentMinus);
        

            let responseBtn = document.createElement('button');
            responseBtn.classList.add('comment-response-btn');
            responseBtn.innerHTML = 'respond';

            responseBtn.onclick = () => {
                responseBtn.style.display = 'none';
                commentElement.appendChild(createCommentResponseTools(comment, responseBtn));        
            }
            commentElement.appendChild(responseBtn);

        }
        
        commentContainer.appendChild(commentScore);
        commentContainer.appendChild(commentElement);

        commentsList.appendChild(commentContainer);
    }

    document.getElementById('comments_container').appendChild(commentsList);
}

var createCommentResponseTools = (comment, responseBtn) => {
    let responseTools = document.createElement('div');

    let responseTextarea = document.createElement('textarea');
    responseTextarea.classList.add('response-text-area');

    let responseSubmitBtn = document.createElement('button');
    responseSubmitBtn.classList.add('response-button');
    responseSubmitBtn.innerHTML = 'submit';

    responseSubmitBtn.onclick = () => {
        addNewChildComment(responseTextarea.value, comment.commentId);
        responseBtn.style.display = 'block';
        responseTools.remove();
        // TODO refresh comments list
    }

    let cancelBtn = document.createElement('button');
    cancelBtn.classList.add('response-button');
    cancelBtn.innerHTML = 'cancel';

    cancelBtn.onclick = () => {
        responseBtn.style.display = 'block';
        responseTools.remove();
    }

    responseTools.appendChild(responseTextarea);
    responseTools.appendChild(responseSubmitBtn);
    responseTools.appendChild(cancelBtn);

    return responseTools;
}

var updateCommentPoints = (id, value) => {
    let url = commentsUrl + '/points';

    let updatePoints = {
        'commentId' : id,
        'value' : value
    }

    var updatePointsRequest = new XMLHttpRequest();

    updatePointsRequest.open('POST', url, true);
    updatePointsRequest.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');

    updatePointsRequest.onreadystatechange = () => {
        if (updatePointsRequest.readyState == XMLHttpRequest.DONE) {
            var json = JSON.parse(updatePointsRequest.responseText);
            console.log(json);
        }
    }

    updatePointsRequest.send(JSON.stringify(updatePoints));
}

var addNewComment = () => {
    var cxhr = new XMLHttpRequest();

    let comText = document.getElementById('comment_content').value;
    let errorMessage = document.getElementById('add_com_message');

    if (comText == '') {
        errorMessage.style.color = '#FF0000';
        errorMessage.innerHTML = 'please enter comment text';    
        return;
    }

    errorMessage.innerHTML = '';

    cxhr.open('POST', commentsUrl, true);
    
    let commentJSON = {
        'parentId' : curPostId,
        'parentCommentId' : null,
        'text' : comText
    }

    cxhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');

    cxhr.onreadystatechange = () => {
        if (cxhr.readyState == XMLHttpRequest.DONE) {
            var json = JSON.parse(cxhr.responseText);
            document.getElementById('comment_content').value = '';
            document.getElementById('comments_list').remove();
            loadComments(curPostId);
            console.log(json);
        }
    }

    cxhr.send(JSON.stringify(commentJSON));
}


var addNewChildComment = (text, parentCommentId) => {
    var childCommentRequest = new XMLHttpRequest();

    childCommentRequest.open('POST', commentsUrl, true);
    
    let commentJSON = {
        'parentId' : curPostId,
        'parentCommentId' : parentCommentId,
        'text' : text
    }

    childCommentRequest.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');

    childCommentRequest.onreadystatechange = () => {
        if (childCommentRequest.readyState == XMLHttpRequest.DONE) {
            var json = JSON.parse(childCommentRequest.responseText);
            console.log(json);
        }
    }

    childCommentRequest.send(JSON.stringify(commentJSON));
}
