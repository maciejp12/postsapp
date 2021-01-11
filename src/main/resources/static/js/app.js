const postsUrl = 'http://127.0.0.1:8080/api/posts';
const commentsUrl = 'http://127.0.0.1:8080/api/comments';

var scrollPostion = [0, 0];
var postListScrollPosition = [0, 0];

var updateScrollPostion = () => {
    if (window.pageYOffset != undefined) {
        scrollPostion = [pageXOffset, pageYOffset];
    } else {
        var sx, sy, d = document,
            r = d.documentElement,
            b = d.body;
        sx = r.scrollLeft || b.scrollLeft || 0;
        sy = r.scrollTop || b.scrollTop || 0;
        scrollPostion = [sx, sy];
    }
}

var updatePostListScrollPostion = () => {
    if (window.pageYOffset != undefined) {
        postListScrollPosition = [pageXOffset, pageYOffset];
    } else {
        var sx, sy, d = document,
            r = d.documentElement,
            b = d.body;
        sx = r.scrollLeft || b.scrollLeft || 0;
        sy = r.scrollTop || b.scrollTop || 0;
        postListScrollPosition = [sx, sy];
    }
}

const getAllPosts = () => {
    var getAllRequest = new XMLHttpRequest();

    getAllRequest.open('GET', postsUrl + '/head', true);
    getAllRequest.send(null);

    getAllRequest.onreadystatechange = () => {
        if (getAllRequest.readyState == XMLHttpRequest.DONE) {
            var json = JSON.parse(getAllRequest.responseText);
            createPostsList(json);
            window.scroll(postListScrollPosition[0], postListScrollPosition[1]);    
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
        updatePostListScrollPostion();
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
            createCommentsList(json);
            window.scroll(scrollPostion[0], scrollPostion[1])
        }
    }
}

async function isAuth() {
    const authUrl = 'http://127.0.0.1:8080/api/user/auth';
    const response = await fetch(authUrl, {});
    const json = await response.json();

    return json.auth;
}

var sortCommentsByPoints = (commentsArray) => {
    return commentsArray.sort((a, b) => a.points < b.points);
}

var filterChildComments = (commentsArray, id) => {
    return commentsArray.filter(function (c) {
        return c.parentCommentId == id;
    });
}

var filterNonNullParentComments = (commentsArray) => {
    return commentsArray.filter(function (c) {
        return c.parentCommentId != null;
    });
}

var createCommentsList = (comments) => {
    let commentsList = document.createElement('div');
    commentsList.classList.add('comments-list');
    commentsList.id = 'comments_list';

    let noParentComments = filterChildComments(comments, null);
    noParentComments = sortCommentsByPoints(noParentComments);

    let childComments = filterNonNullParentComments(comments);

    for (let i = 0; i < noParentComments.length; i++) {
        let comment = noParentComments[i];
        appendToCommentsList(comment, filterChildComments(childComments, comment.commentId),
                             childComments,  commentsList, 0);
    }

    document.getElementById('comments_container').appendChild(commentsList);
}

const leftMarginCommentGap = 30;

var appendToCommentsList = (comment, children, allChildComments, commentsList, depth) => {
    let commentHTML = createCommentElement(comment);
    commentHTML.style.marginLeft += (leftMarginCommentGap * depth) + 'px';
    commentsList.appendChild(commentHTML);
    
    let sortedChildComments = sortCommentsByPoints(children);

    for (let i = 0; i < sortedChildComments.length; i++) {
        let childComment = sortedChildComments[i];
        appendToCommentsList(childComment, filterChildComments(allChildComments, childComment.commentId), 
                             allChildComments, commentsList, depth + 1);
    }
}

var createCommentElement = (commentJSON) => {

    let commentContainer = document.createElement('div');
    commentContainer.classList.add('comment-container');

    let commentScore = document.createElement('div');
    commentScore.classList.add('comment-score');

    let commentPoints = document.createElement('h1');
    commentPoints.classList.add('comment-score-points');
    commentPoints.innerHTML = commentJSON.points;

    let commentElement = document.createElement('div');
    commentElement.classList.add('comment-element');

    let commentInfo = document.createElement('p');
    commentInfo.classList.add('comment-info');
    commentInfo.innerHTML = '<b><a class="comment-author" href="#">' + commentJSON.author + '</a></b>' + ' , ' + commentJSON.creationDate;

    let commentText = document.createElement('p');
    commentText.classList.add('comment-text');
    commentText.innerHTML = commentJSON.text;

    commentScore.appendChild(commentPoints);

    commentElement.appendChild(commentInfo);
    commentElement.appendChild(commentText);

    isAuth().then(authenthicated => {
        if (authenthicated) {

            let commentPlus = document.createElement('button');
            commentPlus.classList.add('comment-score-btn');
            commentPlus.innerHTML = '+';
            commentPlus.onclick = () => {
                updateCommentPoints(commentJSON.commentId, 1, commentPoints)
            }

            let commentMinus = document.createElement('button');
            commentMinus.classList.add('comment-score-btn');
            commentMinus.innerHTML = '-';
            commentMinus.onclick = () => {
                updateCommentPoints(commentJSON.commentId, -1, commentPoints)
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
                commentElement.appendChild(createCommentResponseTools(commentJSON, responseBtn));        
            }
            commentElement.appendChild(responseBtn);

        }
    });
    
    commentContainer.appendChild(commentScore);
    commentContainer.appendChild(commentElement);

    return commentContainer;
}

var createCommentResponseTools = (commentJSON, responseBtn) => {
    let responseTools = document.createElement('div');

    let responseTextarea = document.createElement('textarea');
    responseTextarea.classList.add('response-text-area');

    let responseSubmitBtn = document.createElement('button');
    responseSubmitBtn.classList.add('response-button');
    responseSubmitBtn.innerHTML = 'submit';

    responseSubmitBtn.onclick = () => {
        addNewChildComment(responseTextarea.value, commentJSON.commentId);
        responseBtn.style.display = 'block';
        responseTools.remove();
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

var updateCommentPoints = (id, value, commentPoints) => {
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
            if (json.valid) {
                getCommentScore(id, commentPoints);
            }
        }
    }

    updatePointsRequest.send(JSON.stringify(updatePoints));
}

var getCommentScore = (commentId, commentPoints) => {
    var getCommentScoreRequest = new XMLHttpRequest();

    getCommentScoreRequest.open('GET', commentsUrl + '/points/' + commentId, true);
    getCommentScoreRequest.send(null);

    getCommentScoreRequest.onreadystatechange = () => {
        if (getCommentScoreRequest.readyState == XMLHttpRequest.DONE) {
            var json = JSON.parse(getCommentScoreRequest.responseText);
            commentPoints.innerHTML = json.score;
        }
    }
}

var addNewComment = () => {
    var addCommentRequest = new XMLHttpRequest();

    let comText = document.getElementById('comment_content').value;
    let errorMessage = document.getElementById('add_com_message');

    if (comText == '') {
        errorMessage.style.color = '#FF0000';
        errorMessage.innerHTML = 'please enter comment text';    
        return;
    }

    errorMessage.innerHTML = '';

    addCommentRequest.open('POST', commentsUrl, true);
    
    let commentJSON = {
        'parentId' : curPostId,
        'parentCommentId' : null,
        'text' : comText
    }

    addCommentRequest.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');

    addCommentRequest.onreadystatechange = () => {
        if (addCommentRequest.readyState == XMLHttpRequest.DONE) {
            var json = JSON.parse(addCommentRequest.responseText);
            if (json.valid) {
                updateScrollPostion();
                document.getElementById('comment_content').value = '';
                document.getElementById('comments_list').remove();
                loadComments(curPostId);
            }
        }
    }

    addCommentRequest.send(JSON.stringify(commentJSON));
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
            
            if (json.valid) {
                updateScrollPostion();
                document.getElementById('comment_content').value = '';
                document.getElementById('comments_list').remove();
                loadComments(curPostId);
            }
        }
    }

    childCommentRequest.send(JSON.stringify(commentJSON));
}
