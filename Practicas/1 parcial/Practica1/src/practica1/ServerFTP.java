/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alumno
 */
public class ServerFTP {
    
    public static void main(String[] args) {
        try {
            ServerSocket s = new ServerSocket(7000);
            System.out.println("Esperando clientes...");
            //Creamo ciclo infinito
            for(;;)
            {
                Socket cl = s.accept();
                System.out.println("Conexion establecida desde " + cl.getInetAddress() + "con Puerto " + cl.getPort());
                DataInputStream entrada = new DataInputStream(cl.getInputStream());
                int buffer = entrada.readInt();
                int numArchivos = entrada.readInt();
                System.out.println("Esperando " + numArchivos + " archivos...");
                byte[] b = new byte[buffer];
                
                for(int i = 0; i < numArchivos; i ++)
                {
                    String nombre = entrada.readUTF();
                    long tamano = entrada.readLong();
                    DataOutputStream salida =new DataOutputStream(new FileOutputStream(nombre));
                    long recibidos = 0;
                    int n, porcentaje = 0;
                    System.out.println("Recibiendo: " + nombre + ", " + tamano);
                    
                    while(recibidos < tamano)
                    {
                        n = entrada.read(b,0,buffer);//Numero de bytes leidos en b
                        salida.write(b, 0, n);
                        salida.flush();
                        recibidos = n + recibidos;
                        porcentaje = (int)(recibidos * 100 / tamano);
                        //System.out.println("Porcentaje: " + porcentaje + "%\r");
                    }//While
                    salida.close();
                }//For
                entrada.close();
                cl.close();
            }//For
        } catch (IOException ex) {
            Logger.getLogger(ServerFTP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
