/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg07udp_primitivos;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 *
 * @author Alumno
 */
public class SPrimUDP {
    
    public static void main(String[] args) throws SocketException, IOException 
    {
        DatagramSocket s = new DatagramSocket(2000);
        System.out.println("Servidor inciando");
        for(;;)
        {
            DatagramPacket p = new DatagramPacket(new byte[1000], 0);
            s.receive(p);
            System.out.println("Datagrama recibido desde " + p.getAddress() + ":" + p.getPort());
            DataInputStream entrada = new DataInputStream(new ByteArrayInputStream(p.getData()));
            int y = entrada.readInt();
            float f = entrada.readFloat();
            long l = entrada.readLong();
            System.out.println("Entero : " + y + ", Flotante : " + f + ", Long : " + l);
        }
    }
}
