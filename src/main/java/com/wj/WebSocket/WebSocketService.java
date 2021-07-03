//package com.wj.WebSocket;
//
//import com.wj.pojo.User;
//import com.wj.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.websocket.*;
//import javax.websocket.server.PathParam;
//import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;
//import java.util.concurrent.CopyOnWriteArraySet;
//
//@ServerEndpoint("/user/webSocket/{userId}")
//@Component
//public class WebSocketService {
//
//    /**
//     * concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketSet对象。
//     */
//    private static CopyOnWriteArraySet<WebSocketService> webSocketSet = new CopyOnWriteArraySet<>();
//
//    private String userId;
//    private Session session;    // 客户端连接对象
//
//
//    private static UserService userService;
//
//
//    @OnOpen
//    public void onOpen(Session session, @PathParam("userId") String userId) {
//        this.session = session;
//        if(userId!=null){
//            this.userId = userId;
//            webSocketSet.add(this);
//        }
//    }
//    @OnMessage
//    public void onMessage(Session session, String message) {}
//    @OnError
//    public void onError( Throwable error) {
//        error.printStackTrace();
//    }
//    @OnClose
//    public void onClose() {
//        webSocketSet.remove(this);
//    }
//
//    public void fun() throws IOException {
//        // 发送消息
//        this.session.getBasicRemote().sendText("123456");
//        // 关闭连接
//        this.session.close();
//    }
//
//    /**
//     * 实现服务器主动推送
//     */
//    public void sendMessage(String message) throws IOException {
//        this.session.getBasicRemote().sendText(message);
//    }
//
//    public static void sendMessage(String message,String userId) throws IOException{
//        try {
//            for(WebSocketService item :webSocketSet){
//                if(userId==null){
//                    item.sendMessage(message);
//                }else if(item.userId.equals(userId)){
//                    item.sendMessage(message);
//                }
//            }
//        }catch (IOException e){
//
//        }
//    }
//
//
//}
