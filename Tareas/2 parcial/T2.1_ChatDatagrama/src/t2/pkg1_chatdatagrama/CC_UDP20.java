/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t2.pkg1_chatdatagrama;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 *
 * @author angel
 */
public class CC_UDP20 {
    
    public static void main(String[] args) throws SocketException, IOException
    {
        //Datos de conexion
        String host = "127.0.0.1";
        int pto = 6661;
        DatagramSocket c = new DatagramSocket(6660);
        
        System.out.println("Esperando mensajes...");
        while(true)
        {
            //Recibiendo numero de paquetes
            byte[] bn;
            int numPack;
            String msg;
            DatagramPacket paqueteN = new DatagramPacket(new byte[1000], 20);
            c.receive(paqueteN);
            msg = new String(paqueteN.getData(),0,paqueteN.getLength());
            numPack = Integer.parseInt(msg);
            msg = "";
            System.out.println("\tPreparandose para recibir " + numPack + " paquetes");
            //Recibiendo paquetes
            for (int i = 1; i <= numPack; i++) 
            {
                DatagramPacket paqueteR = new DatagramPacket(new byte[1000], 20);
                c.receive(paqueteR);
                //System.out.println("Recibiendo paquete " + i + " de " + paqueteR.getLength() + " bytes");
                String aux = new String(paqueteR.getData(),0,20);
                msg = msg + aux;
                //System.out.println(msg);
            }
            //Mostrando mensaje
            if(msg.equalsIgnoreCase("SALIR"))
            {
                System.out.println("El otro usuario se ha desconectado");
                c.close();
                break;
            }
            System.out.println("Recibiendo: " + msg + " : " + msg.length());
            //Creando paquete para enviar
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            String aux;
            int longitud = 0, offset = 0, residuo = 0;
            System.out.println("Escribe un mensaje");
            msg = teclado.readLine();
            //Numero de paquetes
            byte[] b = msg.getBytes();
            if(b.length > 20)
            {
                residuo = b.length%20;
                if(residuo == 0)
                    numPack = b.length / 20;
                else
                    numPack = (b.length / 20) + 1;
            }else
            {
                numPack = 1;
                residuo = b.length;
            }
            aux = Integer.toString(numPack);
            System.out.println("\tPreparandose para enviar " + b.length + " bytes en " + aux + " paquetes");
            bn = aux.getBytes();
            DatagramPacket paqueteNE = new DatagramPacket(bn, bn.length, InetAddress.getByName(host),pto);
            c.send(paqueteNE);
            //Enviando paquetes
            for(int i = 0; i < numPack; i ++)
            {
                offset = 20 * i;
                if(numPack == (i + 1))
                    longitud = residuo;
                else
                    longitud = 20;
                DatagramPacket paqueteE = new DatagramPacket(b,offset , longitud, InetAddress.getByName(host), pto);
                c.send(paqueteE);
                System.out.println("Enviando paquete " + (i + 1 )+ " de " + paqueteE.getLength() + " bytes");
            }
            if(msg.equalsIgnoreCase("SALIR"))
                break;
        }
    }
}
