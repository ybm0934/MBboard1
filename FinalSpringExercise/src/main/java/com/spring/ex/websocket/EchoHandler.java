package com.spring.ex.websocket;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class EchoHandler extends TextWebSocketHandler {

	private Map<String, WebSocketSession> map;

	public EchoHandler() {
		map = new HashMap<String, WebSocketSession>();
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("새로운 소켓이 시작되었습니다.");
		System.out.println("접속자 session ID : " + session.getId());
		map.put(session.getId(), session);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("요청 받고 응답하고...");
		System.out.println("sessionId : " + session.getId() + "/ 메세지 : " + message + " /데이터 : " + message.getPayload());

		String msg = session.getId() + "님 : " + message.getPayload();
		Iterator keys = map.keySet().iterator();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			WebSocketSession userSession = map.get(key);
			userSession.sendMessage(new TextMessage(msg));
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("소켓 연결 종료");
		map.remove(session.getId());
	}

}
