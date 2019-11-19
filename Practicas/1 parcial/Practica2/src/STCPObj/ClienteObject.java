/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package STCPObj;

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
        Socket cl = new Socket("8.8.0.38", 9000);

        Persona p1 = new Persona(0, 2015630465, "Morales García Miguel Ángel", 6666);
        ObjectOutputStream salida = new ObjectOutputStream(cl.getOutputStream());
        salida.writeObject(p1);
        System.out.println("Objeto enviado");
        salida.flush();
        salida.close();
        cl.close();
    }
}
