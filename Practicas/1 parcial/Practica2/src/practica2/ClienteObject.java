/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Angel
 */
public class ClienteObject {
    public static void main(String[] args) throws IOException {
        Socket cl = new Socket("127.0.0.1", 7000);
        ObjectOutputStream salida = new ObjectOutputStream(cl.getOutputStream());
        //ObjectInputStream entrada = new ObjectInputStream(cl.getInputStream());
        
        Persona p1 = new Persona(1, 2015630465, "Morales García Miguel Ángel", 6666);
        
        salida.writeObject(p1);
        salida.close();
    }
}
