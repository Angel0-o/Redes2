/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Angel
 */
public class ServidorObject {
    public static void main(String[] args) throws IOException 
    {
        ServerSocket s = new ServerSocket(6666);
        DataInputStream entradaData = null;
        long tam;
        int temp;
        byte[] buffer;
        for(;;)
        {
            Socket cl = s.accept();
            entradaData = new DataInputStream(cl.getInputStream());
            tam = entradaData.readLong();
            temp = (int) tam;
            buffer = new byte[temp];
            entradaData.read(buffer, 0,(int) temp);
        }
    }
}
