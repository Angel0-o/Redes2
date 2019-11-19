/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2.pkg1_chatmulticast;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Angel
 */
public class Interfaz extends Thread {

    private JFrame fram;
    private JPanel contentPane;
    private JLabel nameL;
    private JTextField nameT;
    private JButton nameB;
    private JTextArea chatArea;
    private JTextField message;
    private JButton enviar;

    public JLabel getNameL() {
        return nameL;
    }

    public void setNameL(JLabel nameL) {
        this.nameL = nameL;
    }

    public JTextField getNameT() {
        return nameT;
    }

    public void setNameT(JTextField nameT) {
        this.nameT = nameT;
    }

    public void setNameB(JButton nameB) {
        this.nameB = nameB;
    }

    public JTextArea getChatArea() {
        return chatArea;
    }

    public void setChatArea(JTextArea chatArea) {
        this.chatArea = chatArea;
    }

    public JTextField getMessage() {
        return message;
    }

    public void setMessage(JTextField message) {
        this.message = message;
    }

    public JButton getEnviar() {
        return enviar;
    }

    public void setEnviar(JButton enviar) {
        this.enviar = enviar;
    }

    public Interfaz() {
        Envia evento = new Envia();
        fram = new JFrame();
        fram.setTitle("Chat Multicast");
        fram.setSize(400, 520);
        fram.setLocationRelativeTo(null);
        fram.setDefaultCloseOperation(EXIT_ON_CLOSE);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        fram.setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(Color.LIGHT_GRAY);

        //Añadiendo los elementos a la interfaz
        nameL = new JLabel("NickName");
        nameL.setBounds(40, 25, 60, 25);
        contentPane.add(nameL);
        nameL.setVisible(true);

        nameT = new JTextField();
        nameT.setBounds(110, 25, 90, 25);
        contentPane.add(nameT);

        nameB = new JButton("Conectar");
        nameB.setBounds(210, 25, 80, 25);
        nameB.addActionListener(evento);
        contentPane.add(nameB);

        chatArea = new JTextArea();
        chatArea.setBounds(40, 60, 300, 300);
        contentPane.add(chatArea);

        message = new JTextField();
        message.setBounds(40, 370, 200, 25);
        contentPane.add(message);

        enviar = new JButton("Enviar");
        enviar.setBounds(250, 370, 80, 25);
        enviar.addActionListener(evento);
        contentPane.add(enviar);

        fram.setVisible(true);
    }

    @Override
    public void run() {
        try {
            ChatMulticast.chatListen(nameT, chatArea);
        } catch (IOException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private class Envia implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Object botonPulsado = e.getSource();
                if (botonPulsado == nameB) {
                    ChatMulticast.chatWrite(nameT, message);
                }
                if (botonPulsado == enviar) {
                    ChatMulticast.chatWrite(nameT, message);
                }
            } catch (IOException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        //Creando interfaz
        Interfaz ventana = new Interfaz();
        ventana.start();
    }
}
