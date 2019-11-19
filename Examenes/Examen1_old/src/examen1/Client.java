/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examen1;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.Buffer;

/**
 *
 * @author Angel
 */
public class Client {
    
    static final long serialVersionUID = 42L;
    
    public static void main(String[] args) throws IOException, ClassNotFoundException 
    {
        String usuario,pass;
        int usrT, lonU, lonO;
        byte[] bufferU = new byte[2048];
        byte[] bufferO = new byte[2048];
        boolean banderaU = true;
        while(banderaU)
        {
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Escribe tu nombre de Usuario");
            usuario = teclado.readLine();
            System.out.println("Escribe tu Contraseña");
            pass = teclado.readLine();

            Usuario usuarioC = new Usuario(0,usuario, pass, usuario, 0);

            Socket c = new Socket("127.0.0.1", 6666);

            ObjectOutputStream salidaO = new ObjectOutputStream(c.getOutputStream());
            salidaO.writeObject(usuarioC);

            DataInputStream entradaD = new DataInputStream(c.getInputStream());
            DataInputStream entradaDO = new DataInputStream(c.getInputStream());
            usrT = entradaD.readInt();
            
            DataOutputStream fileU = new DataOutputStream(new FileOutputStream("UsersC.txt"));
            DataOutputStream fileO = new DataOutputStream(new FileOutputStream("ObjectsC.txt"));
            switch(usrT)
            {
                case -1:
                    System.out.println("\nError en usuario o contraseña");
                break;
                case 0:
                    System.out.println("\nUsuario valido");
                    banderaU = false;
                    lonO = entradaD.readInt();
                    entradaD.read(bufferO,0,lonO);
                    fileO.write(bufferO,0,lonO);
                    System.out.println("Leyendo objetos ");
                    ObjectInputStream fileRO = new ObjectInputStream(new FileInputStream("ObjectsC.txt"));
                    fileRO.read(bufferO);
                    ListaObjetos[] auxO = new ListaObjetos[8];
                    for(int i = 0; i < 8; i ++)
                    {
                        auxO[i] = (ListaObjetos) fileRO.readObject();
                        auxO[i].imprimeObjeto();
                    }
                break;
                case 1:
                    int r,w;
                    System.out.println("\nAdministrador valido");
                    banderaU = false;
                    //Usuarios
                    lonU = entradaD.readInt();
                    System.out.println("Bytes Recidos: " + lonU);
                    r = entradaD.read(bufferU,0,lonU);
                    System.out.println("Bytes leidos: " + r);
                    fileU.write(bufferU,0,lonU);
                    //Leyendo del archivo
                    System.out.println("Leyendo usuarios ");
                    ObjectInputStream fileRU = new ObjectInputStream(new FileInputStream("UsersC.txt"));
                    ListaUsuarios auxU;
                    System.out.println("Lista creada");
                    auxU = (ListaUsuarios) fileRU.readObject();
                    auxU.imprimeUsuario();
                    //Objetos
                    lonO = entradaDO.readInt();
                    System.out.println("Bytes Recidos: " + lonO);
                    r = entradaDO.read(bufferO,0,lonO);
                    System.out.println("Bytes leidos: " + r);
                    fileO.write(bufferO,0,lonO);
                    //Leyendo del archivo
                    System.out.println("Leyendo objetos ");
                    fileRO = new ObjectInputStream(new FileInputStream("ObjectsC.txt"));
                    fileRO.read(bufferO);
                    auxO = new ListaObjetos[8];
                    for(int i = 0; i < 8; i ++)
                    {
                        auxO[i] = (ListaObjetos) fileRO.readObject();
                        auxO[i].imprimeObjeto();
                    }
                    
                    //Interfaz
                    System.out.println("-----------------------------------");
                    System.out.println("- Escoja una opcion para realizar -");
                    System.out.println("-----------------------------------");
                    System.out.println("- 1.- Alta                        -");
                    System.out.println("- 2.- Baja                        -");
                    System.out.println("- 3.- Cambio                      -");
                    System.out.println("- 4.- Consulta                    -");
                    System.out.println("- 5.- Termino                     -");
                    System.out.println("-----------------------------------");
                break;
            }
        }
    }
}
