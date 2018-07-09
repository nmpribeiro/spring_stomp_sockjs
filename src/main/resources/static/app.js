var ws;
var reconnect = true;

function setConnected(connected) {
	$("#connect").prop("disabled", connected);
	$("#disconnect").prop("disabled", !connected);
	if (connected) {
		$("#conversation").show();
	} else {
		$("#conversation").hide();
	}
	$("#greetings").html("");
}

function connect() {
	//connect to stomp where stomp endpoint is exposed
	var socket = new SockJS("/websocket");
	ws = Stomp.over(socket);

	ws.connect({}, function(frame) {
		ws.subscribe("/user/queue/errors", function(message) { alert("Error " + message.body) });
		ws.subscribe("/user/queue/reply", function(message) { showGreeting(message.body) });

		ws.subscribe("/response/test", function(message) { showGreeting(message.body) });
		ws.subscribe("/shake-hands/reply", function(message) { showGreeting(message.body) });
		ws.subscribe("/errors", function(message) { showGreeting(message.body) });

		setConnected(true);

		ws.send("/app/shake-hands/req", {}, "Greeting server!");

	}, function(error) {
		console.error("STOMP error " + error);
		disconnect();
	}.bind(this));
}

function disconnect() {
	if (ws != null) {
		if (ws.close) { ws.close(); }
	}
	setConnected(false);
	console.log("Disconnected");

	if (reconnect) {
		setTimeout(() => { connect() }, 1000)
	}
}

function sendName() {
	var data = JSON.stringify({
		'name' : $("#name").val()
	})
	ws.send("/app/request/test", {}, data);
}

function showGreeting(message) {
    console.log("message: ", message);
	$("#greetings").append("<tr><td> " + message + "</td></tr>");
}

$(function() {
	$("form").on('submit', function(e) {
		e.preventDefault();
	});
	$("#connect").click(function() {
		connect();
	});
	$("#disconnect").click(function() {
		disconnect();
	});
	$("#send").click(function() {
		sendName();
	});
});
