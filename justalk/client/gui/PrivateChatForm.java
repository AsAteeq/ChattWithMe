/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package justalk.client.gui;

import java.awt.Image;
import justalk.client.Client;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author User
 */
@SuppressWarnings("serial")
public class PrivateChatForm extends javax.swing.JFrame {

    private String name;
    private Client client;
    
    /**
     * Creates new form RoomChatForm
     */
    public PrivateChatForm() {
        initComponents();
        
        try {
            Image i = ImageIO.read(getClass().getResource(""));
            setIconImage(i);
        } catch (IOException ex) {
            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public PrivateChatForm(String name, Client client) {
        initComponents();
        
        setLocationRelativeTo(null);
        this.messageTextField.setFocusCycleRoot(true);
        this.messageTextField.requestFocusInWindow();
        this.name = name;
        this.setTitle(name);
        this.client = client;
        
        try {
            Image i = ImageIO.read(getClass().getResource(""));
            setIconImage(i);
        } catch (IOException ex) {
            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contentPanel = new javax.swing.JPanel();
        chatPanel = new javax.swing.JPanel();
        chatScrollPane = new javax.swing.JScrollPane();
        chatTextArea = new javax.swing.JTextArea();
        messagePanel = new javax.swing.JPanel();
        messageTextField = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Private Chat");

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

        getContentPane().add(contentPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void messageTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_messageTextFieldKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            privateMessage();
        }
    }//GEN-LAST:event_messageTextFieldKeyPressed

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        // TODO add your handling code here:
        privateMessage();
        messageTextField.requestFocus();
    }//GEN-LAST:event_sendButtonActionPerformed
    
    public String getName() {
        return name;
    }
    
    private void privateMessage() {
        client.send("/p/" + name + " " + client.getName() + ": " + messageTextField.getText());
        messageTextField.setText("");
    }
    
    public void receiveMessage(String msg) {
        chatTextArea.append(msg + '\n');
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel chatPanel;
    private javax.swing.JScrollPane chatScrollPane;
    private javax.swing.JTextArea chatTextArea;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JPanel messagePanel;
    private javax.swing.JTextField messageTextField;
    private javax.swing.JButton sendButton;
    // End of variables declaration//GEN-END:variables
}
