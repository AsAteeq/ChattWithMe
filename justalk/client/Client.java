/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package justalk.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 *
 * @author User
 */
public class Client{
            
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private String name;
    private String address;
    private int port;

    private boolean connected;

    public Client() {
        this.connected = false;
    }
    
    public void connect(String address, int port) throws UnknownHostException, IOException {
        this.address = address;
        this.port = port;
        System.out.println("Connecting to server...");
        socket = new Socket(InetAddress.getByName(this.address), this.port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        connected = true;
    }

    public void login(String name) {
        if (!isConnected())
            return;
        
        this.name = name;
        out.println(this.name);
    }

    public String getAddress() {
        return this.address;
    }

    public int getPort() {
        return this.port;
    }

    public String getName() {
        return name;
    }

    public boolean isConnected() {
        return connected;
    }

    public String receive() {
        String text;
        try {
            while((text = in.readLine()) != null) {
                System.out.println(text);
                return text;
            }
        } catch (IOException e) {
            return "/e/Server shut down";
        }
        return "";
    }

    public void send(String msg) {
        out.println(msg);
    }

    public void close() {
        synchronized(socket) {
            try {
                out.close();
                in.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}