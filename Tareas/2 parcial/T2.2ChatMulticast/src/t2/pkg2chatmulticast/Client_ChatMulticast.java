/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t2.pkg2chatmulticast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
 * @author angel
 */
public class Client_ChatMulticast {
    
    public static void main(String[] args) {
        try {
            int pto = 2001;
            String host = "230.0.0.1";
            SocketAddress remote = new InetSocketAddress(host, 2000);
            InetSocketAddress dir = new InetSocketAddress(2001);
            //Interfaz de red
            NetworkInterface ni = NetworkInterface.getByName("wlan3");//eth1 | wlan1
            //Creacion del canal y el selector
            DatagramChannel escribe = DatagramChannel.open(StandardProtocolFamily.INET);
            escribe.setOption(StandardSocketOptions.SO_REUSEADDR, true);
            escribe.setOption(StandardSocketOptions.IP_MULTICAST_IF, ni);
            escribe.configureBlocking(false);
            Selector sel = Selector.open();
            escribe.register(sel, SelectionKey.OP_WRITE | SelectionKey.OP_READ);
            //Nos unismos al grupo
            InetAddress group = InetAddress.getByName(host);
            escribe.join(group, ni);
            escribe.socket().bind(dir);
            //Lectura
            //Creamos buffer
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            String msg;
            ByteBuffer b = ByteBuffer.allocate(50);
            System.out.println("Cliente listo...\nEsperando datagramas...");
            while (true) 
            {
                sel.select();
                Iterator<SelectionKey> it = sel.selectedKeys().iterator();
                while (it.hasNext()) 
                {
                    SelectionKey k = it.next();
                    it.remove();
                    if (k.isWritable()) 
                    {
                        DatagramChannel ch = (DatagramChannel) k.channel();
                        System.out.println("Escribe un mensaje");
                        msg = teclado.readLine();
                        b.clear();
                        b.put(msg.getBytes());
                        b.flip();
                        ch.send(b,remote);
                        k.interestOps(SelectionKey.OP_READ);
                        continue;
                    }
                    else if (k.isReadable()) 
                    {
                        DatagramChannel ch = (DatagramChannel) k.channel();
                        b.clear();
                        SocketAddress emisor = ch.receive(b);
                        msg = new String(b.array());
                        b.flip();
                        InetSocketAddress d = (InetSocketAddress) emisor;
                        System.out.println("Datagrama recibido desde " + d.getAddress() + ": " + d.getPort());
                        System.out.println(msg);
                        k.interestOps(SelectionKey.OP_WRITE);
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
