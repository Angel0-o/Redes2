/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg04serializacion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alumno
 */
public class Servidor {
    public static void main(String[] args) {
        ObjectOutputStream salida = null;
        ObjectInputStream entrada = null;
        
        try {
            ServerSocket s = new ServerSocket(9999);
            System.out.println("Servidor iniciado...");
            for(;;)
            {
                Socket cl = s.accept();
                System.out.println("Cliente conectado desde " + cl.getInetAddress() + " : " + cl.getPort());
                
                salida = new ObjectOutputStream(cl.getOutputStream());
                entrada = new ObjectInputStream(cl.getInputStream());
                Usuario u = (Usuario) entrada.readObject();
                
                System.out.println("Objeto recibido. Extrayendo informacion..");
                System.out.println("Nombre: " + u.getNombre() + "\nA_Paterno: " + u.getApaterno() + "\nA_Materno: " + u.getAmaterno() + "\nPwd: " + u.getPwd() + "\nEdada: " + u.getEdad());
                System.out.println("Devolviendo objeto");
                salida.writeObject(u);
                salida.flush();
            }//For
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }//catch
    }//Main
}
