package org.example;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);

        SocketIOServer server = new SocketIOServer(config);

        // Event listener for incoming messages
        server.addEventListener("send_message", ChatMessage.class, (client, data, ackSender) -> {
            // Broadcast the message to all connected clients
            server.getBroadcastOperations().sendEvent("receive_message", data);
        });

        server.start();
        System.out.println("Chat server started on port 9092.");

        // Keep the server running
        Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
    }
}

// Message class
class ChatMessage {
    private String user;
    private String message;

    public ChatMessage() {}

    public ChatMessage(String user, String message) {
        this.user = user;
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}