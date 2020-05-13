/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package justalk.server;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class Server {
	
    public static int DEFAULT_PORT = 2019;

    private volatile List<ServerClient> clients = new ArrayList<>();

    private ServerSocket serverSocket;
    private final int port;

    private boolean running = false;

    public Server(int port) {
        this.port = port;
    }
    
    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            final  Server server = this;
            
            Thread mainThread = new Thread("main") {
                @Override
                public void run() {
                    running = true;
                    listen(server);
                    System.out.println("Server is running on port: " + serverSocket.getLocalPort());
                }
            };
            mainThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void listen(final Server server) {
        Thread listenThread = new Thread("listen") {
            @Override
            public void run() {
                while(running) {
                    Socket clientSocket = null;
                    try {
                        clientSocket = serverSocket.accept();
                    } catch (IOException e) {
                        System.out.println("Connection closed.");
                    }
                    if(clientSocket == null) return;
                    ServerClient c = new ServerClient(clientSocket, server);
                    
                    if(checkForDouble(c)) {
                        c.send("/0/Username already taken"); 
                        continue;
                    }
                    
                    c.send("/1/Login Success");
                    c.send("/c/Welcome " + c.getName());
                    clients.add(c);
                    new Thread(c).start();
                    System.out.println("Connected: " + c.getName() + " @ " + c.getAddress() + ":" + c.getPort());
                    updateList();
                    sendPublic("/m/" + c.getName() + " joined.");
                }
            }
        };
        listenThread.start();
    }
    
    public void process(String text, ServerClient sc) {
        String message;
        if(text.startsWith("/m/")) {
            message = text.substring(3, text.length());
            sendPublic("/m/" + sc.getName() + ": " + message);
            return;
        }
        
        if(text.startsWith("/p/")) {
            message = text.substring(3, text.length());
            String recipient = message.substring(0, message.indexOf(' '));
            for(int i = 0; i < clients.size(); i++) {
                if(recipient.equals(clients.get(i).getName())) {
                    message = message.substring(recipient.length(), message.length());
                    sendPrivate(message, recipient, sc.getName());
                    return;
                }
            }
            sc.send("/o/" + recipient + " *user offline");
        }
    }

    private void sendPublic(String msg) {
        for(int i = 0; i < clients.size(); i++) {
            clients.get(i).send(msg);
        }
    }
    
    private void sendPrivate(String msg, String recipient, String sender) {
        for(int i = 0; i < clients.size(); i++) {
            if(clients.get(i).getName().equals(recipient)) {
                clients.get(i).send("/p/" + msg);
            } else if(clients.get(i).getName().equals(sender)) {
                clients.get(i).send("/s/" + recipient + msg);
            }
        }
    }
    
    private void updateList() {
        StringBuilder sb = new StringBuilder("/u/");
        for(int i = 0; i < clients.size(); i++) {
            sb.append((clients.get(i).getName() + "/./"));
        }
        sendPublic(sb.toString());
    }
    
    private boolean checkForDouble(ServerClient sc) {
        for(int i = 0; i < clients.size(); i++) {
            if(clients.get(i).getName().equals(sc.getName())) {
                return true;
            }
        }
        return false;
    }
    
    public void removeUser(ServerClient sc) {
        clients.remove(sc);
        updateList();
        sendPublic("/m/" + sc.getName() + " left.");
    }
    
    public void shutdown() {
        running = false;
        sendPublic("/e/Server shut down");
        for(int i = 0; i < clients.size(); i++) {
            clients.get(i).close();
        }
        clients.clear();
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Server server = new Server(DEFAULT_PORT);
                server.start();
            }
        });
    }
}
