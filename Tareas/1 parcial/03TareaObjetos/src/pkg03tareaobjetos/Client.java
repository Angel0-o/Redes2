/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg03tareaobjetos;

import com.sun.security.ntlm.Server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alumno
 */
public class Client {
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            Socket c = new Socket("8.8.0.38",9000);
            long boleta = 2015630465;
            long tam;
            String nombre = "Morales Garcia Miguel Angel";
            int Edad= 20;
            int n;
            byte[] buffer = new byte[1024];
            double valor = 10.5;
            boolean resultado;
            DataOutputStream Salida = new DataOutputStream(c.getOutputStream());
            DataInputStream entrada = new DataInputStream(c.getInputStream());
            
            Salida.writeLong(boleta);
            Salida.writeUTF(nombre);
            Salida.writeInt(Edad);
            
            tam = entrada.readLong();
            n = (int) tam;
            System.out.println("Tamaño " + tam);
            entrada.read(buffer, 0, n);//Numero de bytes leidos en b
            System.out.println("Cadena: " + buffer.toString());
            valor = entrada.readDouble();
            System.out.println("Valor " + valor);
            
            Salida.writeDouble(valor);
            Salida.flush();
            
            resultado = entrada.readBoolean();
            System.out.println("Resultado " + resultado);
            
            Salida.close();
            entrada.close();
            c.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
