/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinalftp;

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
import java.util.Iterator;
import javax.swing.JTextField;
import javax.swing.JTextPane;

/**
 *
 * @author Angel
 */
public class ServiceMulticast {
    
    public static void serviceListen(JTextField name, JTextPane chatArea) throws IOException, InterruptedException {
        InetSocketAddress remote = new InetSocketAddress("228.1.1.1", 2000);
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
        InetAddress group = InetAddress.getByName("228.1.1.1");
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
    
        public static void serviceWrite(JTextField name, JTextField texto) throws IOException {
        InetSocketAddress remote = new InetSocketAddress("228.1.1.1", 2000);
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
}
