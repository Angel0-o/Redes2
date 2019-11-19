/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg07udp_primitivos;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author Alumno
 */
public class CPrimUDP {
    
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException
    {
        DatagramSocket cl = new DatagramSocket();
        InetAddress host = InetAddress.getByName("127.0.0.1");
        int pto = 2000;
        ByteArrayOutputStream salida_buffer = new ByteArrayOutputStream();
        DataOutputStream salida = new DataOutputStream(salida_buffer);
        salida.writeInt(4);
        salida.flush();
        salida.writeFloat(4.1f);
        salida.flush();
        salida.writeLong(72);
        salida.flush();
        
        byte[] buffer = salida_buffer.toByteArray();
        DatagramPacket p = new DatagramPacket(buffer, buffer.length, host, pto);
        cl.send(p);
        cl.close();
    }
}
