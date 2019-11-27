/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinalftp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Angel
 */
public class ServerFile extends Thread {

    ServerSocket s;
    Socket c;
    int port;

    public ServerFile(int p) throws IOException {
        s = new ServerSocket(p);
    }
    
    public void run(){
        System.out.println("Servidor de archivos en el puerto " + s.getLocalPort());
        for (;;) {
            try {
                c = s.accept();
                System.out.println("Conexion establecida desde " + c.getInetAddress() + "con Puerto " + c.getPort());
                DataInputStream entrada = new DataInputStream(c.getInputStream());
                byte[] b = new byte[1024];
                String nombre = entrada.readUTF();
                System.out.println("Recibimos el archivo " + nombre);
                long tamano = entrada.readLong();
                DataOutputStream salida = new DataOutputStream(new FileOutputStream(nombre));
                long recibidos = 0;
                int n, porcentaje = 0;

                while (recibidos < tamano) {
                    n = entrada.read(b);
                    salida.write(b, 0, n);
                    //System.out.println("Pase el write");
                    salida.flush();
                    recibidos = n + recibidos;
                    porcentaje = (int) (recibidos * 100 / tamano);
                    System.out.println("Porcentaje: " + porcentaje + "%\r");
                }//While
                System.out.println("Archivo recibido");
                salida.close();
                entrada.close();
                c.close();
            } catch (IOException ex) {
                Logger.getLogger(ServerFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ServerFile sf = new ServerFile(9999);
        sf.start();
    }
}
