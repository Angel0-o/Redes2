/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg06datagram_socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 *
 * @author Angel
 */
public class SEco_UDP {
    
    public static void main(String[] args) throws SocketException, IOException 
    {
        DatagramSocket s = new DatagramSocket(2000);
        System.out.println("Servidor inciando");
        for(;;)
        {
            DatagramPacket p = new DatagramPacket(new byte[1000], 1000);
            s.receive(p);
            System.out.println("Datagrama recibido desde " + p.getAddress() + ":" + p.getPort());
            String msg = new String(p.getData(),0,p.getLength());
            System.out.println("Con el mensaje : " + msg);
        }
    }
}
