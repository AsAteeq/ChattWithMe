/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package justalk.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/**
 *
 * @author User
 */
public class ServerClient implements Runnable{
	
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean running = false;

    private String name;
    private String address;
    private int port;

    private Server server;

    public ServerClient(Socket socket, Server server) {
        try {
            this.socket = socket;
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.server = server;
            name = in.readLine();
            address = socket.getInetAddress().getHostAddress();
            port = socket.getPort();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        synchronized(socket) {
            try {
                running = false;
                out.close();
                in.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public void send(String msg) {
        out.println(msg);
    }

    @Override
    public void run() {
        running = true;
        while(running) {
            String text;
            try {
                while((text = in.readLine()) != null) {
                    server.process(text, this);
                }
            } catch (IOException e) {
                server.removeUser(this);
                System.out.println(name + " disconnected");
                running = false;
                close();
            }
        }
    }
}