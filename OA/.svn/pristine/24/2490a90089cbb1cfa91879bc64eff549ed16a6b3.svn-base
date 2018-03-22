<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<textarea id="messages" rows="10"></textarea>
	<input/><button onclick="sendMessage(this)">发送消息</button>
	
<script src="./assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script>
var websocket = new WebSocket("wss://localhost:443/OA/ws?uid=${USER.userid}");
websocket.onmessage = function(event){
	$("#messages").append("</br>"+event.data);
}

var sendMessage = function(btn){
	var message = $(btn).prev().val();
	$.post("./sendMessage",{"message":message});
}

</script>
	
</body>
</html>