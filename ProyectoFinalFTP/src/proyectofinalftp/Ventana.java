/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinalftp;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Angel
 */
public class Ventana extends Thread {

    private final JFrame fram;
    private final JPanel contentPane;
    private JLabel nodoA;
    private JLabel nodoS;
    private JComboBox lista;
    private JTextField textF;
    private JButton buscarB;
    private JTextPane logArea;
    private int port;
    private int portNext;
    private String host;
    private String path;
    private Envia evento;

    public Ventana() {
        String portTitle = JOptionPane.showInputDialog(null, "Introduzca el puerto ");
        evento = new Envia();
        fram = new JFrame();
        contentPane = new JPanel();
        fram.setTitle(portTitle);
        fram.setSize(400, 520);
        fram.setLocationRelativeTo(null);
        fram.setDefaultCloseOperation(EXIT_ON_CLOSE);
        port = Integer.parseInt(portTitle);

        //Creando carpeta asociada al puerto
        path = "C:/FTP_R2/" + portTitle;
        File directorio = new File(path);
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Directorio creado");
            } else {
                System.out.println("Error al crear directorio");
            }
        }

        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        fram.setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(Color.LIGHT_GRAY);

        //Añadiendo los elementos a la interfaz
        nodoA = new JLabel("Prev: 5000");
        nodoA.setBounds(40, 25, 80, 25);
        contentPane.add(nodoA);

        nodoS = new JLabel("Next: 5002");
        nodoS.setBounds(130, 25, 80, 25);
        contentPane.add(nodoS);

        lista = new JComboBox();
        lista.setBounds(220, 25, 100, 25);
        contentPane.add(lista);

        textF = new JTextField();
        textF.setBounds(40, 60, 90, 25);
        contentPane.add(textF);

        buscarB = new JButton("Buscar");
        buscarB.setBounds(140, 60, 90, 25);
        buscarB.addActionListener(evento);
        contentPane.add(buscarB);

        logArea = new JTextPane();
        logArea.setBounds(40, 90, 300, 300);
        logArea.setContentType("text/html");
        contentPane.add(logArea);

        fram.setVisible(true);
    }

    public class Envia implements ActionListener {

        String ho;
        int po;

        public String getHo() {
            return ho;
        }

        public void setHo(String ho) {
            this.ho = ho;
        }

        public int getPo() {
            return po;
        }

        public void setPo(int po) {
            this.po = po;
        }

        public Envia() {
            ho = "127.0.0.1";
            po = 9000;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Object botonPulsado = e.getSource();
            if (botonPulsado == buscarB) {
                ServiceMulticast.service_CliRMI(ho, po, textF.getText(), logArea);
            }
        }
    }

    @Override
    public void run() {
        try {
            ServiceMulticast.service_SerRMI(path, port);
            ServiceMulticast.serviceListen(nodoA, nodoS, lista, port, portNext, host, evento);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        //Creando interfaz
        Ventana ventana = new Ventana();
        ventana.start();
        //Anunciando el puerto
        ServiceMulticast.serviceWrite(ventana.port);
    }
}
