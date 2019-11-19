/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg08multicast;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author Alumno
 */
public class CMulticast {
    
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException
    {
        MulticastSocket cl = new MulticastSocket(9999);
        InetAddress gpo = null;
        System.out.println("Cliente escuchando en " + cl.getLocalPort());
        cl.setReuseAddress(true);
        
        gpo= InetAddress.getByName("228.1.1.1");
        cl.joinGroup(gpo);
        System.out.println("Unido al grupo");
        
        for(;;)
        {
            DatagramPacket p = new DatagramPacket(new byte[10], 10);
            cl.receive(p);
            String msg = new String(p.getData());
        }
    }
}
