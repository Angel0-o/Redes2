/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg02archtcpb;

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
public class CArchTCP {
    
    public static void main(String[] args) {
        try {
            String host = "127.0.0.1";
            int pto = 7000;
            Socket cl = new Socket(host, pto);
            JFileChooser jf = new JFileChooser();
            int r = jf.showOpenDialog(null);
            
            if(r == JFileChooser.APPROVE_OPTION)
            {
                System.out.println("Entre al if");
                File f = jf.getSelectedFile();
                String archivo = f.getAbsolutePath();
                String nombre = f.getName();
                long tamano = f.length();
                DataOutputStream salida = new DataOutputStream(cl.getOutputStream());
                DataInputStream entrada = new DataInputStream(new FileInputStream(archivo));
                salida.writeUTF(nombre);
                salida.flush();
                salida.writeLong(tamano);
                salida.flush();
                byte[] b = new byte[1024];
                long enviado = 0;
                int n, porcentaje = 0;
                
                while(enviado < tamano)
                {
                    n = entrada.read(b);//Numero de bytes leidos en b
                    salida.write(b, 0, n);
                    //System.out.println("Pase el write");
                    salida.flush();
                    enviado = n + enviado;
                    porcentaje = (int)(enviado * 100 / tamano);
                    System.out.println("Porcentaje: " + porcentaje + "%\r");
                }//While
                System.out.println("Archivo enviado");
                salida.close();
                entrada.close();
                cl.close();
            }//If
        } catch (IOException ex) {
            Logger.getLogger(CArchTCP.class.getName()).log(Level.SEVERE, null, ex);
        }//Catch
    }//Main
}
