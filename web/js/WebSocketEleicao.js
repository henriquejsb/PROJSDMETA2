

var websocket = null;

var smv = function () {
    $.ajax({
        type: "POST",
        url: "liveEleicao?eleicao="+elei,
        dataType: "text",
        success: function (data1) {
            var html1 = "" + data1;


            $("#stats").html(html1.replace(/\n/g, "<br />"));
        },
        error: function (data) {
            console.log("erro a pedir detalhes!");
        }
    });

};

window.onload = function() { // URI = ws://10.16.0.165:8080/WebSocket/ws
    connect('ws://' + window.location.host + '/ws');
    console.log(elei);
    smv();

}

function connect(host) { // connect to the host websocket
    if ('WebSocket' in window)
        websocket = new WebSocket(host);
    else if ('MozWebSocket' in window)
        websocket = new MozWebSocket(host);
    else {
        writeToHistory('Get a real browser which supports WebSocket.');
        return;
    }

    websocket.onopen    = onOpen; // set the event listeners below
    websocket.onclose   = onClose;
    websocket.onmessage = onMessage;
    websocket.onerror   = onError;
}

function onOpen(event) {
    /*writeToHistory('Connected to ' + window.location.host + '.');
     document.getElementById('chat').onkeydown = function(key) {
     if (key.keyCode == 13)
     doSend(); // call doSend() on enter key
     };*/

}

function onClose(event) {
    /*writeToHistory('WebSocket closed.');
     document.getElementById('chat').onkeydown = null;*/
}

function onMessage(message) { // print the received message
    //writeToHistory(message.data);
    var aux = message.data.split("\n");
    if(aux[0] == "ELEICAO")
        if(aux[1] == elei)
        smv();
}

function onError(event) {
    /*writeToHistory('WebSocket error (' + event.data + ').');
     document.getElementById('chat').onkeydown = null;*/
}

function doSend() {

}

function writeToHistory(text) {
    /*var history = document.getElementById('history');
     var line = document.createElement('p');
     line.style.wordWrap = 'break-word';
     line.innerHTML = text;
     history.appendChild(line);
     history.scrollTop = history.scrollHeight;*/
}