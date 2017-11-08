var chatElement;
var recipient;
var recipientParagraf;
var eventSource;

document.addEventListener("DOMContentLoaded", function(){
    chatElement = document.getElementById("chat");
    document.getElementById("send").addEventListener("click", sendMessage);
    document.getElementById("textarea").focus();
    document.getElementById("textarea").onkeydown = function(event){
        if (event.keyCode === 13){ sendMessage(); }
    };

    getUser().then(function(result){
        eventSource = new EventSource(shapeURL("/messaging/" + result));
        eventSource.onclose = function(e) { console.log("Соединение закрыто:" + e.data); };
        eventSource.onmessage = function (e) {
            console.log("ALL: " + e.data + "; " + e.event);
        };

        var eventType = "from" + recipient  + "to" + result;
        console.log(eventType);
        eventSource.addEventListener(eventType, function(e) {
            console.log(e.data);
            var element = document.createElement("div");
            element.setAttribute("style", "color:red; text-align: left;");
            element.appendChild(document.createTextNode(e.data));
            chatElement.appendChild(element);
        });
    });

    buildUserList();

    var paragraf = document.createElement("p");
    recipientParagraf = paragraf;
    recipient = document.getElementById("left").children[0].getAttribute("id");
    paragraf.appendChild(document.createTextNode(recipient));
    document.getElementById("recipientDiv").appendChild(paragraf);
    getHistory(recipient);
});

var connect = function(){
    getUser().then(function(result){
        var eventType = "from" + recipient  + "to" + result;
        console.log(eventType);
        eventSource.addEventListener(eventType, function(e) {
            console.log(e.data);
            var element = document.createElement("div");
            element.setAttribute("style", "color:red; text-align: left;");
            element.appendChild(document.createTextNode(e.data));
            chatElement.appendChild(element);
        });
    });
};

var sendMessage = function(){
    var message = document.getElementById("textarea").value;
    if(message !== ""){
        var data = new FormData();
        data.append("message", message.toString());
        data.append("recipient", recipient);

        var request = new XMLHttpRequest;
        request.onreadystatechange = function() {
            if (request.readyState === 4 && request.status === 200){
                document.getElementById("textarea").value = "";
                var element = document.createElement("div");
                element.setAttribute("style", "color:crimson; text-align: right;");
                element.appendChild(document.createTextNode(message));
                chatElement.appendChild(element);
                console.log(":-> to " + recipient + " = " + request.responseText);
            }
        };
        request.open("POST", shapeURL("/chat/sendmessage"));
        request.send(data);
    }
};

var changeUser = function(){
    recipientParagraf.innerHTML = this.getAttribute("id");
    recipient = this.getAttribute("id");
    connect();
    getHistory(this.getAttribute("id"));
};

var getUser = function(){
    return new Promise(function (res) {
        var request = new XMLHttpRequest;
        request.onreadystatechange = function () {
            if (request.readyState === 4 && request.status === 200) {
                res(request.responseText);
            }
        };
        request.open("GET", shapeURL("/user/current"));
        request.send();
    });
};

var buildUserList = function(){
    var request = new XMLHttpRequest;
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            var users = JSON.parse(request.responseText);
            for(var i = 0; i < users.length; i++){
                var btn = document.createElement("BUTTON");
                btn.appendChild(document.createTextNode(users[i]));
                btn.setAttribute("id",users[i]);
                document.getElementById("left").appendChild(btn);
                document.getElementById("left").appendChild(document.createElement("br"));
            }
        }
    };
    request.open("GET", shapeURL("/user/all"), false);
    request.send();

    var elements = document.getElementById("left").children;
    for(var i = 0; i < elements.length; i++){
        elements[i].addEventListener("click", changeUser);
    }
};

var getHistory = function(login){
    var request = new XMLHttpRequest;
    document.getElementById("textarea").value = "";
    chatElement.innerHTML = "";
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            var messages = JSON.parse(request.responseText);
            messages = sortList(messages, "id");
            console.log(login + "; " + messages.length);
            for(var i = 0; i < messages.length; i++){
                var element = document.createElement("div");
                if(messages[i]["sender"] === recipient){
                    element.setAttribute("style", "color:crimson; text-align: left;");
                    element.appendChild(document.createTextNode(messages[i]["message"]));
                    chatElement.appendChild(element);
                }
                else {
                    element.setAttribute("style", "color:crimson; text-align: right;");
                    element.appendChild(document.createTextNode(messages[i]["message"]));
                    chatElement.appendChild(element);
                }
            }
        }
    };
    request.open("POST", shapeURL("/chat/history/" + login), false);
    request.send();
};

var sortList = function (list, key) {
    function compare(a, b) {
        a = a[key];
        b = b[key];
        var type = (typeof(a) === 'string' ||
            typeof(b) === 'string') ? 'string' : 'number';
        var result;
        if (type === 'string') result = a.localeCompare(b);
        else result = a - b;
        return result;
    }
    return list.sort(compare);
};