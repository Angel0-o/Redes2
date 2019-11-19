/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg11cmulticastnb;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;

/**
 *
 * @author Angel
 */
public class CMulticastNB {
    
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
        String hhost = "230.0.0.1";
        SocketAddress remote = null;
        
        remote = new InetSocketAddress(hhost, pto);
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for(NetworkInterface netint: Collections.list(nets) )
            displayInterfaceInformation(netint);
        //Nombre de la tarjeta de red
        NetworkInterface ni = NetworkInterface.getByName("wlan1");
        
        DatagramChannel cl = DatagramChannel.open(StandardProtocolFamily.INET);
        cl.setOption(StandardSocketOptions.SO_REUSEADDR, true);
        cl.setOption(StandardSocketOptions.IP_MULTICAST_IF, ni);
        cl.configureBlocking(false);
        Selector sel = Selector.open();
        cl.register(sel, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        InetAddress group = InetAddress.getByName(hhost);
        cl.join(group, ni);
        ByteBuffer b = ByteBuffer.allocate(4);
        int n = 0;
        while(n<100)
        {
            sel.select();
            Iterator<SelectionKey> it = sel.selectedKeys().iterator();
            while(it.hasNext())
            {
                SelectionKey k = it.next();
                it.remove();
                if(k.isWritable())
                {
                    DatagramChannel ch = (DatagramChannel) k.channel();
                    b.clear();
                    b.putInt(n++);
                    b.flip();
                    ch.send(b, remote);
                    continue;
                }
            }
        }
        System.out.println("Termina envio de datgramas");
    }
}
