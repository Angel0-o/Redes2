/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg11cmulticastnb;

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
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import static pkg11cmulticastnb.CMulticastNB.displayInterfaceInformation;

/**
 *
 * @author alumno
 */
public class SMulticastNB {
    
    static void displayInterfaceInformation(NetworkInterface netint)
    {
        System.out.printf("Interfaz: %s\n",netint.getDisplayName());
        System.out.printf("Nombre: %s\n",netint.getName());
        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
        for(InetAddress inetAddress: Collections.list(inetAddresses))
        {
            System.out.printf("InetAddress: %s\n",inetAddress);
        }
        System.out.println("\n");
    }
    
    public static void main(String[] args) throws SocketException, IOException
    {
        int pto = 2000;
        InetSocketAddress dir = new InetSocketAddress(pto);
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for(NetworkInterface netint: Collections.list(nets) )
        {
            displayInterfaceInformation(netint);
        }
        //Nombre de la tarjeta de red
        NetworkInterface ni = NetworkInterface.getByName("wlan1");//eth1
        
        DatagramChannel s = DatagramChannel.open(StandardProtocolFamily.INET);
        s.setOption(StandardSocketOptions.SO_REUSEADDR, true);
        s.setOption(StandardSocketOptions.IP_MULTICAST_IF, ni);
        s.configureBlocking(false);
        Selector sel = Selector.open();
        s.register(sel, SelectionKey.OP_READ);
        InetAddress group = InetAddress.getByName("230.0.0.1");
        s.join(group, ni);
        s.socket().bind(dir);
        ByteBuffer b = ByteBuffer.allocate(4);
        System.out.println("Servidor listo...\nEsperando datagramas...");
        while(true)
        {
            sel.select();
            Iterator<SelectionKey> it = sel.selectedKeys().iterator();
            while(it.hasNext())
            {
                SelectionKey k = it.next();
                it.remove();
                if(k.isReadable())
                {
                    DatagramChannel ch = (DatagramChannel) k.channel();
                    b.clear();
                    SocketAddress emisor = ch.receive(b);
                    b.flip();
                    InetSocketAddress d =(InetSocketAddress) emisor;
                    System.out.println("Datagrama recibido desde " + d.getAddress() + ": " + d.getPort());
                    System.out.println("Dato: " + b.getInt());
                    continue;
                }
            }
        }
    }
}
