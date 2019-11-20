/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t2.pkg2chatmulticast;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.StandardProtocolFamily;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alumno
 */
public class Server_ChatMulticast {

    public static void main(String[] args) {
        try {
            int pto = 2000;
            String host = "230.0.0.1";
            SocketAddress remote = new InetSocketAddress(host, 2001);
            InetSocketAddress dir = new InetSocketAddress(pto);
            //Interfaz de red
            NetworkInterface ni = NetworkInterface.getByName("wlan3");//eth1 | wlan1
            //Creacion del canal y el selector
            DatagramChannel s = DatagramChannel.open(StandardProtocolFamily.INET);
            s.setOption(StandardSocketOptions.SO_REUSEADDR, true);
            s.setOption(StandardSocketOptions.IP_MULTICAST_IF, ni);
            //Configuramos el canal para que sea no bloqueante
            s.configureBlocking(false);
            //Abrimos el selector y lo configuramos para lectura y escritura
            Selector sel = Selector.open();
            s.register(sel, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            //Nos unismos al grupo
            InetAddress group = InetAddress.getByName(host);
            s.join(group, ni);
            s.socket().bind(dir);
            //ByteBuffer b = ByteBuffer.allocate(50);
            String msg;
            int tam;
            System.out.println("Servidor listo...\nEsperando datagramas...");
            while (true) 
            {
                sel.select();
                Iterator<SelectionKey> it = sel.selectedKeys().iterator();
                while (it.hasNext()) 
                {
                    SelectionKey k = it.next();
                    it.remove();
                    if (k.isReadable()) 
                    {
                        DatagramChannel ch = (DatagramChannel) k.channel();
                        ByteBuffer b = ByteBuffer.allocate(20);
                        SocketAddress emisor = ch.receive(b);
                        b.flip();
                        tam = b.getInt();
                        System.out.println("Tamaño: " + tam);
                        msg = new String(b.array(),0,tam);
                        InetSocketAddress d = (InetSocketAddress) emisor;
                        System.out.println("Datagrama recibido desde " + d.getAddress() + ": " + d.getPort());
                        System.out.println("Dato: " + msg);
                        //ECO
                        msg = "ECO: " + msg;
                        b.clear();
                        b.put(msg.getBytes());
                        b.flip();
                        ch.send(b, remote);
                        msg = "";
                        continue;
                    }
                }
            }
        } catch (SocketException ex) {
            Logger.getLogger(Server_ChatMulticast.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Server_ChatMulticast.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
