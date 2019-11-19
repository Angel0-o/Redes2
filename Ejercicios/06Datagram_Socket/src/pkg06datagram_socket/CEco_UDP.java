/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg06datagram_socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 *
 * @author Angel
 */
public class CEco_UDP {
    
    public static void main(String[] args) throws SocketException, IOException 
    {
        DatagramSocket cl = new DatagramSocket();
        System.out.println("Cliente iniciado\nEscriba un mensaje");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String msg = br.readLine();
        byte[] b = msg.getBytes();
        String host = "127.0.0.1";
        int pto = 2000;
        DatagramPacket p = new DatagramPacket(b, b.length, InetAddress.getByName(host), pto);
        cl.send(p);
        cl.close();
    }
}
