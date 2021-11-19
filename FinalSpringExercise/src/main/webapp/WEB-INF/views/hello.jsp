<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	var ws;
	function yongConnect() {
		ws = new WebSocket('ws://192.168.1.242:9090/myweb/hello');
		ws.onopen = function () {
			document.fm.content.value = '대화 참여함\n';
		};
		
		ws.onmessage = function (event) {
			var data = event.data;
			document.fm.content.value += data + '\n';
		};
		
		ws.onclose = function (event) {
			document.fm.content.value += '대화 종료함\n';
		};
	}
	
	function echo() {
		var msg = document.fm.write.value;
		ws.send(msg);
	}
	
	function yongClose() {
		ws.close();
	}
</script>
</head>
<body>
<h1>hello.jsp</h1>
<h3>result값 : ${result }</h3>
<hr>
<h1>급작 채팅</h1>
<form name="fm">
	<textarea rows="15" cols="35" name="content"></textarea>
	<br>
	입력 : <input type="text" name="write">
	<input type="button" value="전송" onclick="echo();"><br>
	<input type="button" value="대화참여" onclick="yongConnect();"><br>
	<input type="button" value="대화종료" onclick="yongClose();"><br>
</form>
</body>
</html>