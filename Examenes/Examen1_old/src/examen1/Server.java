/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examen1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Angel
 */
public class Server {
    
    static final long serialVersionUID = 42L;
    
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException 
    {
        boolean objetoBandera = false;
        byte[] bufferU = new byte[2048];
        byte[] bufferO = new byte[2048];
        int lonU,lonO = 0;
        
        //Comprobando datos
        System.out.println("Buscando datos...");
        File auxF = new File("Users.txt");
        File auxFO = new File("Objects.txt");
        if (!auxF.exists())
        {
            //Escribiendo Usuarios por default
            System.out.println("Creando usuarios por default");
            ObjectOutputStream fileU = new ObjectOutputStream(new FileOutputStream("Users.txt"));
            Usuario[] users = new Usuario[2];
            users[0]= new Usuario(0,"admin","admin","Administrador",1);//0: Usuario, 1: Administrador
            users[1]= new Usuario(1,"pedro","picapiedra","Usuario",0);//0: Usuario, 1: Administrador
            ListaUsuarios lista = new ListaUsuarios(users.length, users);
            fileU.writeObject(lista);
            fileU.flush();
            fileU.close();
        }
        
        if (!auxFO.exists())
        {
            //Escribiendo Objetos por default
            System.out.println("Creando objetos por default");
            ObjectOutputStream fileO = new ObjectOutputStream(new FileOutputStream("Objects.txt"));
            Refrigerador[] re = new Refrigerador[2];
            re[0] = new Refrigerador(0, 10, 20, 30);
            Cortinas[] co = new Cortinas[2]; 
            co[0] = new Cortinas(0, "Centro", "Arriba", new Date(100000), new Date(100000));
            Termostato[] te = new Termostato[2];
            te[0] = new Termostato(0, 10);
            DisMascota[] di = new DisMascota[2];
            di[0] = new DisMascota(0, new Date(100000), 5, new Date(100000), 10, new Date(100000), 5);
            Irrigador[] ir = new Irrigador[2];
            ir[0] = new Irrigador(0, new Date(100000), 10);
            Alarma[] al = new Alarma[2];
            al[0] = new Alarma(0, new Date(100000), new Date(100000), true);
            Lampara[] la = new Lampara[2];
            la[0] = new Lampara(0, "Centro", true);
            Luminaria[] lu = new Luminaria[2];
            lu[0] = new Luminaria(0, "Centro", 0, true);
            ListaObjetos listaO1 = new ListaObjetos(1, 1, re);
            ListaObjetos listaO2 = new ListaObjetos(2, 1, co);
            ListaObjetos listaO3 = new ListaObjetos(3, 1, te);
            ListaObjetos listaO4 = new ListaObjetos(4, 1, di);
            ListaObjetos listaO5 = new ListaObjetos(5, 1, ir);
            ListaObjetos listaO6 = new ListaObjetos(6, 1, al);
            ListaObjetos listaO7 = new ListaObjetos(7, 1, la);
            ListaObjetos listaO8 = new ListaObjetos(8, 1, lu);
            fileO.writeObject(listaO1);
            fileO.writeObject(listaO2);
            fileO.writeObject(listaO3);
            fileO.writeObject(listaO4);
            fileO.writeObject(listaO5);
            fileO.writeObject(listaO6);
            fileO.writeObject(listaO7);
            fileO.writeObject(listaO8);
            fileO.flush();
            fileO.close();
        }
        
        System.out.println("Leyendo archivo de usuarios [" + auxF.length() + "] ");
        ObjectInputStream fileRU = new ObjectInputStream(new FileInputStream("Users.txt"));
        ListaUsuarios auxU;
        auxU = (ListaUsuarios) fileRU.readObject();
        auxU.imprimeUsuario();
        fileRU.close();
        
        System.out.println("Leyendo objetos [" + auxFO.length() + "] ");
        ObjectInputStream fileRO = new ObjectInputStream(new FileInputStream("Objects.txt"));
        ListaObjetos[] auxO = new ListaObjetos[8];
        for(int i = 0; i < 8; i ++)
        {
            auxO[i] = (ListaObjetos) fileRO.readObject();
            auxO[i].imprimeObjeto();
        }
        fileRO.close();
        
        //Inicia Servidor
        ServerSocket ss = new ServerSocket(6666);
        System.out.println("Servidor listo...\nEsperando clientes...");
        while(true)
        {
            Socket cl = ss.accept();
            System.out.println("Cliente conectado desde " + cl.getInetAddress() + " : " + cl.getPort());
            Usuario auxUS;
            ObjectInputStream entradaO = new ObjectInputStream(cl.getInputStream());
            auxUS = (Usuario) entradaO.readObject();
            int tipo = -1;
            for(int i = 0; i < auxU.getTamano(); i ++)
            {
                if(auxU.getObjeto(i).getUsr().equals(auxUS.getUsr()) && auxU.getObjeto(i).getPwd().equals(auxUS.getPwd()))
                {
                    System.out.println("Usuario encontrado");
                    tipo = auxU.getObjeto(i).getTipo();
                    break;
                }
            }
            
            DataOutputStream salidaD = new DataOutputStream(cl.getOutputStream());
            DataOutputStream salidaDO = new DataOutputStream(cl.getOutputStream());
            DataInputStream entradaDO;
            switch(tipo)
            {
                case -1:
                    System.out.println("\nError en usuario o contraseña");
                    salidaD.writeInt(tipo);
                    cl.close();
                break;
                case 0:
                    System.out.println("\nUsuario conectado");
                    salidaD.writeInt(tipo);
                    entradaDO = new DataInputStream(new FileInputStream("Objects.txt"));
                    lonO = entradaDO.read(bufferO);
                    System.out.println("Bytes leidos: " + lonO);
                    salidaDO.writeInt((lonO));
                    salidaDO.write(bufferO);
                break;
                case 1:
                    System.out.println("\nAdministrador conectado");
                    salidaD.writeInt(tipo);
                    salidaD.flush();
                    DataInputStream entradaDU = new DataInputStream(new FileInputStream("Users.txt"));
                    lonU = entradaDU.read(bufferU);
                    System.out.println("Bytes leidos: " + lonU);
                    salidaD.writeInt((lonU));
                    salidaD.flush();
                    salidaD.write(bufferU);
                    salidaD.flush();
                    entradaDO = new DataInputStream(new FileInputStream("Objects.txt"));
                    lonO = entradaDO.read(bufferO);
                    System.out.println("Bytes leidos: " + lonO);
                    salidaDO.writeInt((lonO));
                    salidaDO.flush();
                    salidaDO.write(bufferO);
                    salidaDO.flush();
                break;
            }
        }
    }
}
