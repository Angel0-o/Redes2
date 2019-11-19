/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg05external;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Angel
 */
public class DemoExternalizable {
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        System.out.println("Creando objeto");
        String[] usuarios ={"A","B","C"};
        String[] passwords ={"1","2","3"};
        ListaUsuarios lp = new ListaUsuarios(usuarios, passwords);
        ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("objetos.out"));
        o.writeObject(lp);
        o.close();
        System.out.println("\nRecuperando objeto");
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("objetos.out"));
        lp = (ListaUsuarios) in.readObject();
        lp.muestraUsuario();
    }
}
