package cn.tempus.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import cn.tempus.websocket.MyWebSocketHandler;

@Component
public class WebSocketMessageReceiver {
	
	@Autowired
	MyWebSocketHandler myhandler;
	
    /**接收消息的方法*/
    public void receiveMessage(String uid){
    	TextMessage msg = new TextMessage("hehe");
    	myhandler.sendMessageToUser(uid, msg);
    }
    
}
