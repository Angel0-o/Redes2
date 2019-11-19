/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg04serializacion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alumno
 */
public class Cliente {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int pto = 9999;
        
        ObjectOutputStream salida = null;
        ObjectInputStream entrada = null;
        try {
            Socket cl = new Socket(host, pto);
            System.out.println("Conexion establecida");
            salida = new ObjectOutputStream(cl.getOutputStream());
            entrada = new ObjectInputStream(cl.getInputStream());
            Usuario u = new Usuario("Pepito", "Perez", "Lopez", "12345", 20);
            System.out.println("Enviando objeto..");
            salida.writeObject(u);
            salida.flush();
            System.out.println("Esperando objeto..");
            Usuario u2 = (Usuario) entrada.readObject();
            System.out.println("Extrayendo datos: ");
            System.out.println("Nombre: " + u2.getNombre() + "\nA_Paterno: " + u2.getApaterno() + "\nA_Materno: " + u2.getAmaterno() + "\nPwd: " + u2.getPwd() + "\nEdada: " + u2.getEdad());
            salida.close();
            entrada.close();
            cl.close();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
