/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2.pkg1_chatmulticast;

import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.StandardProtocolFamily;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import javax.swing.JTextPane;
import javax.swing.JTextField;

/**
 *
 * @author Angel
 */
public class ChatMulticast {

    public static String analyzerChat(int modo, String text) {
        String aux = "";
        System.out.println("\tAnalyzer: " + text);
        ArrayList<String> words = new ArrayList(Arrays.asList(text.split(" ")));
        if (modo == 1) {
            return text + " is online";
        }
        if (modo == 2) {
            for (int i = 0; i < words.size(); i++) {
                if (words.get(i).equals(";)")) {
                    words.set(i, "<img src=file:///C:/Users/Angel/Documents/GitHub/Redes2/Practicas/2%20parcial/P2.1_ChatMulticast/Img/imagen_guino.jpg width=\"40\" height=\"40\">");
                }
                if (words.get(i).equals(":o")) {
                    words.set(i, "<img src=file:///C:/Users/Angel/Documents/GitHub/Redes2/Practicas/2%20parcial/P2.1_ChatMulticast/Img/imagen_beso.jpg width=\"30\" height=\"30\">");
                }
                if (words.get(i).equals(":s")) {
                    words.set(i, "<img src=file:///C:/Users/Angel/Documents/GitHub/Redes2/Practicas/2%20parcial/P2.1_ChatMulticast/Img/imagen_gesto.jpg width=\"30\" height=\"30\">");
                }
                if (words.get(i).equals("_Perro_")) {
                    words.set(i, "<img src=file:///C:/Users/Angel/Documents/GitHub/Redes2/Practicas/2%20parcial/P2.1_ChatMulticast/Img/perro.gif width=\"30\" height=\"30\">");
                }
                if (words.get(i).equals("_Homero_")) {
                    words.set(i, "<img src=file:///http://tusimagenesde.com/wp-content/uploads/2015/01/gifs-animados-5.gif width=\"30\" height=\"30\">");
                }
            }
        }
        for (int i = 0; i < words.size(); i++) {
            aux += words.get(i) + " ";
        }
        System.out.println("\tAnalyzer: " + aux);
        return aux;
    }

    public static void chatListen(JTextField name, JTextPane chatArea) throws IOException, InterruptedException {
        InetSocketAddress remote = new InetSocketAddress("230.1.1.1", 2000);
        //Interfaz de red
        NetworkInterface netInterface = NetworkInterface.getByName("wlan3");//eth1 | wlan1
        //Creacion y configuracion del canal
        DatagramChannel channel = DatagramChannel.open(StandardProtocolFamily.INET);
        channel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
        channel.setOption(StandardSocketOptions.IP_MULTICAST_IF, netInterface);
        //Configuramos el canal para que sea no bloqueante
        channel.configureBlocking(false);
        //Abrimos el selector y lo configuramos para lectura y escritura
        Selector sel = Selector.open();
        channel.register(sel, SelectionKey.OP_READ);
        //Nos unismos al grupo
        InetAddress group = InetAddress.getByName("230.1.1.1");
        channel.join(group, netInterface);
        //Ligamos el canal para que escuche en un puerto determinado
        channel.socket().bind(new InetSocketAddress(2000));
        ByteBuffer b;
        String msg = "";
        String aux;
        while (true) {
            sel.select();
            Iterator<SelectionKey> it = sel.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey k = it.next();
                it.remove();
                if (k.isReadable()) {
                    System.out.println("Lectura");
                    DatagramChannel ch = (DatagramChannel) k.channel();
                    // Recepcion del tamaño del datagrama
                    b = ByteBuffer.allocate(4);
                    b.clear();
                    SocketAddress emisor = ch.receive(b);
                    b.flip();
                    //Recepcion del mensaje
                    sleep(20);
                    int lon = b.getInt();
                    System.out.println("Recibiendo longitud: " + lon);
                    if(lon < 100)
                    {
                        b.clear();
                        System.out.println("Clear");
                        b = ByteBuffer.allocate(lon);
                        System.out.println("Allocate");
                        emisor = ch.receive(b);
                        System.out.println("Recibido");
                        aux = new String(b.array(), 0, lon);
                        System.out.println("Mensaje: " + aux);
                        if (aux.startsWith("1")) {
                            aux = aux.substring(1, aux.length());
                            aux = analyzerChat(1, aux);
                        }
                        if (aux.startsWith("2")) {
                            aux = aux.substring(1, aux.length());
                            aux = analyzerChat(2, aux);
                        }
                        msg += aux + "<br>";
                        //Añadiendo al Jtextarea
                        chatArea.setText(msg);
                        b.flip();
                        continue;
                    }
                }
            }
        }
    }

    public static void chatWrite(JTextField name, JTextField texto) throws IOException {
        InetSocketAddress remote = new InetSocketAddress("230.1.1.1", 2000);
        //Interfaz de red
        NetworkInterface netInterface = NetworkInterface.getByName("wlan3");//eth1 | wlan1
        //Creacion y configuracion del canal
        DatagramChannel channel = DatagramChannel.open(StandardProtocolFamily.INET);
        channel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
        channel.setOption(StandardSocketOptions.IP_MULTICAST_IF, netInterface);
        //Configuramos el canal para que sea no bloqueante
        channel.configureBlocking(false);
        //Abrimos el selector y lo configuramos para lectura y escritura
        Selector sel = Selector.open();
        channel.register(sel, SelectionKey.OP_WRITE);
        //Nos unismos al grupo
        InetAddress group = InetAddress.getByName("230.1.1.1");
        channel.join(group, netInterface);
        //Ligamos el canal para que escuche en un puerto determinado
        ByteBuffer b;

        String aux;
        sel.select();
        Iterator<SelectionKey> it = sel.selectedKeys().iterator();
        while (it.hasNext()) {
            SelectionKey k = it.next();
            it.remove();
            if (k.isWritable()) {
                System.out.println("Escritura");
                DatagramChannel ch = (DatagramChannel) k.channel();
                aux = "2" + name.getText() + ": " + texto.getText();
                // Envio del tamaño del datagrama
                int lon = aux.length();
                b = ByteBuffer.allocate(4);
                b.clear();
                b.putInt(lon);
                b.flip();
                ch.send(b, remote);
                System.out.println("Enviando longitud: " + lon);
                //Envio del mensaje
                b = ByteBuffer.allocate(lon);
                b.clear();
                b.put(aux.getBytes());
                b.flip();
                ch.send(b, remote);
                System.out.println("Enviando mensaje: " + aux);
                aux = "";
                continue;
            }
        }
    }

    public static void chatConect(JTextField name, JTextField texto) throws IOException {
        InetSocketAddress remote = new InetSocketAddress("230.1.1.1", 2000);
        //Interfaz de red
        NetworkInterface netInterface = NetworkInterface.getByName("wlan3");//eth1 | wlan1
        //Creacion y configuracion del canal
        DatagramChannel channel = DatagramChannel.open(StandardProtocolFamily.INET);
        channel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
        channel.setOption(StandardSocketOptions.IP_MULTICAST_IF, netInterface);
        //Configuramos el canal para que sea no bloqueante
        channel.configureBlocking(false);
        //Abrimos el selector y lo configuramos para lectura y escritura
        Selector sel = Selector.open();
        channel.register(sel, SelectionKey.OP_WRITE);
        //Nos unismos al grupo
        InetAddress group = InetAddress.getByName("230.1.1.1");
        channel.join(group, netInterface);
        //Ligamos el canal para que escuche en un puerto determinado
        ByteBuffer b;

        String aux;
        sel.select();
        Iterator<SelectionKey> it = sel.selectedKeys().iterator();
        while (it.hasNext()) {
            SelectionKey k = it.next();
            it.remove();
            if (k.isWritable()) {
                System.out.println("Conectando");
                DatagramChannel ch = (DatagramChannel) k.channel();
                aux = "1" + name.getText();
                // Envio del tamaño del datagrama
                int lon = aux.length();
                b = ByteBuffer.allocate(4);
                b.clear();
                b.putInt(lon);
                b.flip();
                ch.send(b, remote);
                System.out.println("Enviando longitud: " + lon);
                //Envio del mensaje
                b = ByteBuffer.allocate(lon);
                b.clear();
                b.put(aux.getBytes());
                b.flip();
                ch.send(b, remote);
                System.out.println("Enviando usuario: " + aux);
                aux = "";
                continue;
            }
        }
    }
}
