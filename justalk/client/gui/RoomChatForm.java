/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package justalk.client.gui;

import java.awt.Image;
import justalk.client.Client;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author User
 */
@SuppressWarnings("serial")
public class RoomChatForm extends javax.swing.JFrame implements Runnable {

    private Client client;
    
    private Thread mainThread;
    private Thread receiveThread;
    
    private boolean running = false;
    
    private final List<PrivateChatForm> privateChatForms = new ArrayList<>();
    
    /**
     * Creates new form RoomChatForm
     */
    public RoomChatForm() {
        initComponents();
        
        setLocationRelativeTo(null);
        
        try {
            Image i = ImageIO.read(getClass().getResource(""));
            setIconImage(i);
        } catch (IOException ex) {
            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public RoomChatForm(Client client) {
        this.client = client;
        initComponents();
        
        setLocationRelativeTo(null);
        setTitle("Chat Room | " + this.client.getName());
        messageTextField.requestFocus();
        
        try {
            Image i = ImageIO.read(getClass().getResource(""));
            setIconImage(i);
        } catch (IOException ex) {
            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void start() {
        mainThread = new Thread(this, "main");
        mainThread.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    @SuppressWarnings("rawtypes")
	private void initComponents() {

        contentPanel = new javax.swing.JPanel();
        chatPanel = new javax.swing.JPanel();
        chatScrollPane = new javax.swing.JScrollPane();
        chatTextArea = new javax.swing.JTextArea();
        messagePanel = new javax.swing.JPanel();
        messageTextField = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();
        rightPanel = new javax.swing.JPanel();
        onlineUsersPanel = new javax.swing.JPanel();
        onlineUsersScrollPane = new javax.swing.JScrollPane();
        onlineUsersList = new javax.swing.JList();
        logoutButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat Room");

        contentPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPanel.setLayout(new java.awt.BorderLayout(5, 5));

        chatPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        chatPanel.setPreferredSize(new java.awt.Dimension(400, 250));
        chatPanel.setLayout(new java.awt.BorderLayout(5, 5));

        chatTextArea.setEditable(false);
        chatTextArea.setColumns(20);
        chatTextArea.setRows(5);
        chatTextArea.setPreferredSize(null);
        chatScrollPane.setViewportView(chatTextArea);

        chatPanel.add(chatScrollPane, java.awt.BorderLayout.CENTER);

        messagePanel.setLayout(new java.awt.BorderLayout(5, 5));

        messageTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                messageTextFieldKeyPressed(evt);
            }
        });
        messagePanel.add(messageTextField, java.awt.BorderLayout.CENTER);

        sendButton.setText("Send");
        sendButton.setPreferredSize(new java.awt.Dimension(73, 30));
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });
        messagePanel.add(sendButton, java.awt.BorderLayout.EAST);

        chatPanel.add(messagePanel, java.awt.BorderLayout.SOUTH);

        contentPanel.add(chatPanel, java.awt.BorderLayout.CENTER);

        rightPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        rightPanel.setPreferredSize(new java.awt.Dimension(160, 250));
        rightPanel.setLayout(new java.awt.BorderLayout());

        onlineUsersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Online Users"));
        onlineUsersPanel.setLayout(new java.awt.BorderLayout());

        onlineUsersList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                onlineUsersListMousePressed(evt);
            }
        });
        onlineUsersScrollPane.setViewportView(onlineUsersList);

        onlineUsersPanel.add(onlineUsersScrollPane, java.awt.BorderLayout.CENTER);

        rightPanel.add(onlineUsersPanel, java.awt.BorderLayout.CENTER);

        logoutButton.setText("Logout");
        logoutButton.setPreferredSize(new java.awt.Dimension(73, 30));
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });
        rightPanel.add(logoutButton, java.awt.BorderLayout.SOUTH);

        contentPanel.add(rightPanel, java.awt.BorderLayout.EAST);

        getContentPane().add(contentPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void messageTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_messageTextFieldKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            sendPublic();
        }
    }//GEN-LAST:event_messageTextFieldKeyPressed

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        // TODO add your handling code here:
        sendPublic();
        messageTextField.requestFocus();
    }//GEN-LAST:event_sendButtonActionPerformed

    private void onlineUsersListMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onlineUsersListMousePressed
        // TODO add your handling code here:
        if(evt.getClickCount() == 2) {
            String value = (String) onlineUsersList.getSelectedValue();
            PrivateChatForm frm = getWindowByName(value);
            if(frm == null) {
                createWindow(value);
            } else if(!frm.isVisible()) {
                frm.setVisible(true);
            } else {
                frm.requestFocus();
            }
        }
        messageTextField.requestFocus();
    }//GEN-LAST:event_onlineUsersListMousePressed

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        // TODO add your handling code here:
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_logoutButtonActionPerformed

    private void createWindow(String value) {
        if(value.equals("You") || value.equals("")) {
            return;
        }
        
        if(privateChatForms.isEmpty()) {
            privateChatForms.add(new PrivateChatForm(value, client));
            return;
        }
        
        if(getWindowByName(value) == null) {
            privateChatForms.add(new PrivateChatForm(value, client));
        }
    }
    
    private PrivateChatForm getWindowByName(String name) {
        for(int i = 0; i < privateChatForms.size(); i++) {
            if(privateChatForms.get(i).getName().equals(name)) {
                return privateChatForms.get(i);
            }
        }
        return null;
    }
    
    private void sendPublic() {
        String text = messageTextField.getText();
        if(text.equals("")) return;
        
        client.send("/m/" + text);
        messageTextField.setText("");
    }
    
    public void sendPrivate(String text, String name) {
        client.send("/p/" + name + text);
    }
    
    private void receive() {
        receiveThread = new Thread() {
            @Override
            public void run() {
                while(running) {
                    String text = client.receive();
                    process(text);
                }
            }
        };
        receiveThread.start();
    }
    
    private void process(String text) {
        String message = text.substring(3, text.length());
        String prefix = text.substring(0, 3);
        String recipient;
        switch (prefix) {
            case "/c/":
                setVisible(true);
                chatTextArea.append(message + '\n');
                break;
            case "/m/":
                if(message.startsWith(client.getName() + ":")) {
                    message = "You" + message.substring(client.getName().length(), message.length());
                }
                chatTextArea.append(message + '\n');
                break;
            case "/p/":
                recipient = message.substring(1, message.indexOf(':'));
                message = message.substring(1, message.length());
                PrivateChatForm recipientWindow = getWindowByName(recipient);
                if(recipientWindow != null) {
                    if(!recipientWindow.isVisible()) {
                        recipientWindow.setVisible(true);
                    }
                    recipientWindow.receiveMessage(message);
                } else {
                    createWindow(recipient);
                    recipientWindow = getWindowByName(recipient);
                    recipientWindow.setVisible(true);
                    recipientWindow.receiveMessage(message);
                }
                break;
            case "/s/":
                recipient = message.substring(0, message.indexOf(' '));
                message = message.substring(recipient.length() + 1, message.length());
                message = "You" + message.substring(client.getName().length(), message.length());
                getWindowByName(recipient).receiveMessage(message);
                break;
            case "/o/":
                recipient = message.substring(0, message.indexOf(' '));
                message = message.substring(recipient.length() + 1, message.length());
                getWindowByName(recipient).receiveMessage(message);
                break;
            case "/u/":
                String[] users = message.split("/./");
                updateList(users);
                break;
            case "/e/":
                chatTextArea.append(message + '\n');
                running = false;
                client.close();
                break;
        }
    }
    
    @SuppressWarnings("unchecked")
	private void updateList(String[] users) {
        String[] userL = new String[users.length];
        for(int i = 0, j = 0; i < users.length; i++) {
            if(!users[i].equals(client.getName())) {
                userL[j + 1] = users[i];
                j++;
            } else {
                userL[0] = "You";
            }
        }
        onlineUsersList.setListData(userL);
    }
        
    @Override
    public void run() {
        running = true;
        receive();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel chatPanel;
    private javax.swing.JScrollPane chatScrollPane;
    private javax.swing.JTextArea chatTextArea;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JButton logoutButton;
    private javax.swing.JPanel messagePanel;
    private javax.swing.JTextField messageTextField;
    @SuppressWarnings("rawtypes")
	private javax.swing.JList onlineUsersList;
    private javax.swing.JPanel onlineUsersPanel;
    private javax.swing.JScrollPane onlineUsersScrollPane;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JButton sendButton;
    // End of variables declaration//GEN-END:variables
}
