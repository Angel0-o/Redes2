/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package STCPObj;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Angel
 */
public class ServidorObject {
    public static void main(String[] args) throws IOException, ClassNotFoundException 
    {
        ServerSocket s = new ServerSocket(6666);
        long tam;
        int temp;
        boolean resultado;
        byte[] buffer = new byte[1024];
        System.out.println("Esperando clientes..");
        for(;;)
        {
            Socket cl = s.accept();
            System.out.println("Cliente conectado desde " + cl.getInetAddress() + " : " + cl.getPort());
            DataInputStream entradaData = new DataInputStream(cl.getInputStream());
            ObjectOutputStream salidaData = new ObjectOutputStream(cl.getOutputStream());
            tam = entradaData.readLong();
            System.out.println("Tamaño recibido de " + tam);
            temp = (int) tam;
            //buffer = new byte[temp];
            entradaData.read(buffer, 0,temp);
            
            System.out.println("\nRecuperando objeto");
            DataOutputStream o = new DataOutputStream(new FileOutputStream("objetos.out"));
            o.write(buffer);
            o.flush();
            o.close();
            
            Objeto2[] o2 = new Objeto2[10];
            int numObjetos2, numObjetos1 = 0;
            ListaObjetos1 lista = null;
            //Lista de objetos1
            ObjectInputStream entradaObject = new ObjectInputStream(new FileInputStream("objetos.out"));
            numObjetos2 = entradaObject.readInt();
            System.out.println("Numero de objetos  " + numObjetos2);
            for(int i = 0; i < numObjetos2; i ++)
            {
                o2[i] = (Objeto2) entradaObject.readObject();
                o2[i].imprimeObjeto2();
                System.out.println("Lei " + i + "objetos 2");
            }
            lista = (ListaObjetos1) entradaObject.readObject();
            lista.muestraObjeto1();
            System.out.println("Objetos recuperados del archivo");
            //Envio de objetos
            salidaData.writeInt(numObjetos2);
            salidaData.flush();
            System.out.println("Tamaño de objetos 2 enviado");
            for(int i = 0; i < numObjetos2; i ++)
            {
                salidaData.writeObject(o2[i]);
            }
            System.out.println("Objetos 2 enviados");
            salidaData.writeInt(lista.getTamano());
            System.out.println("Tamaño de objetos 1 enviado");
            for(int i = 0; i < lista.getTamano(); i ++)
            {
                salidaData.writeObject(lista.getObjeto(i));
            }
            System.out.println("Objetos 1 enviados");
            resultado = entradaData.readBoolean();
            System.out.println("Resultado  " + resultado);
        }
    }
}
