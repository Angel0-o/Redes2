/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg02archtcpb;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alumno
 */
public class SArchTCP {
    
    public static void main(String[] args) {
        try {
            ServerSocket s = new ServerSocket(7000);
            
            //Iniciamos el ciclo infinito
            for(;;)
            {
                Socket cl = s.accept();
                System.out.println("Conexion establecida desde " + cl.getInetAddress() + "con Puerto " + cl.getPort());
                DataInputStream entrada = new DataInputStream(cl.getInputStream());
                byte[] b = new byte[1024];
                String nombre = entrada.readUTF();
                System.out.println("Recibimos el archivo " + nombre);
                long tamano = entrada.readLong();
                DataOutputStream salida =new DataOutputStream(new FileOutputStream(nombre));
                long recibidos = 0;
                int n, porcentaje = 0;
                
                while(recibidos < tamano)
                {
                    n = entrada.read(b);
                    salida.write(b, 0, n);
                    //System.out.println("Pase el write");
                    salida.flush();
                    recibidos = n + recibidos;
                    porcentaje = (int)(recibidos * 100 / tamano);
                    System.out.println("Porcentaje: " + porcentaje + "%\r");
                }//While
                System.out.println("Archivo recibido");
                salida.close();
                entrada.close();
                cl.close();
            }//For
        } catch (IOException ex) {
            Logger.getLogger(SArchTCP.class.getName()).log(Level.SEVERE, null, ex);
        }//Catch
    }//Main
}//Class
