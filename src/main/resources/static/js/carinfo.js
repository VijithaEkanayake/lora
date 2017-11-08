document.addEventListener("DOMContentLoaded", function(){
    getMessages();
    document.getElementById("devices").addEventListener("click", function(){ window.location=shapeURL("/devices"); });
});

var getMessages = function(){
    document.getElementById("table").innerHTML = '';
    var request = new XMLHttpRequest;
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            var data = JSON.parse(request.responseText);
            var table = document.getElementById("table");
            var header = table.createTHead();
            var headerRow = header.insertRow(0);
            headerRow.insertCell(0).innerHTML = "<b>Free</b>";
            headerRow.insertCell(1).innerHTML = "<b>Occupied</b>";
            headerRow.insertCell(2).innerHTML = "<b>Timestamp</b>";

            console.log(data);
            var foot = table.createTFoot();
            for (var i = 0; i < data.length; i++) {
                var row = foot.insertRow(i);
                console.log(data[i]);
                row.insertCell(0).innerHTML = data[i]["free"];
                row.insertCell(1).innerHTML = data[i]["occupied"];
                row.insertCell(2).innerHTML = data[i]["timestamp"];
            }
            console.log(data);
        }
    };
    request.open("GET", "/car/carinfo", false);
    request.send();
};