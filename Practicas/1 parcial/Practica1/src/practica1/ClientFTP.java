/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author alumno
 */
public class ClientFTP {
    
    public static void main(String[] args) {
        try {
            String host = "127.0.0.1";
            int pto = 7000;
            byte[] b = new byte[1024];
            Socket cl = new Socket(host, pto);
            //Deshabilitamos el algoritmo de nagle
            cl.setTcpNoDelay(false);
            JFileChooser jf = new JFileChooser();
            jf.setMultiSelectionEnabled(true);
            int r = jf.showOpenDialog(null);
            
            if(r == JFileChooser.APPROVE_OPTION)
            {
                File[] f = jf.getSelectedFiles();
                DataOutputStream salida = new DataOutputStream(cl.getOutputStream());
                salida.writeInt(b.length);
                salida.flush();
                salida.writeInt(f.length);
                salida.flush();
                
                for(int i = 0; i < f.length; i ++)
                {
                    String archivo = f[i].getAbsolutePath();
                    String nombre = f[i].getName();
                    long tamano = f[i].length();
                    DataInputStream entrada = new DataInputStream(new FileInputStream(archivo));
                    System.out.println("Enviando: " + nombre + " :" + tamano + " bytes");
                    
                    salida.writeUTF(nombre);
                    salida.flush();
                    salida.writeLong(tamano);
                    salida.flush();

                    long enviado = 0;
                    int n, porcentaje = 0;
                    
                    while(enviado < tamano)
                    {
                        n = entrada.read(b,0,b.length);//Numero de bytes leidos en b
                        salida.write(b, 0, n);
                        salida.flush();
                        enviado = n + enviado;
                        porcentaje = (int)(enviado * 100 / tamano);
                        //System.out.println("Porcentaje: " + porcentaje + "%\r");
                    }//While
                    entrada.close();
                }
                salida.close();
                cl.close();
            }//If
        } catch (IOException ex) {
            Logger.getLogger(ClientFTP.class.getName()).log(Level.SEVERE, null, ex);
        }//Catch
    }//Main
}
