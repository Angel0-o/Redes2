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
import java.text.DateFormat;
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
        byte[] bufferO = new byte[4096];
        int lon= 0;
        
        //Comprbando datos
        System.out.println("Buscando datos...");
        File auxF = new File("Casa.txt");
        if (!auxF.exists())
        {
            //Escribiendo Usuarios por default
            System.out.println("Creando usuarios por default");
            ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream("Casa.txt",true));
            Usuario[] us = new Usuario[2];
            us[0]= new Usuario(0,"admin","admin","Administrador",1);//0: Usuario, 1: Administrador
            us[1]= new Usuario(1,"pedro","picapiedra","Usuario",0);//0: Usuario, 1: Administrador
            //Escribiendo Objetos por default
            System.out.println("Creando objetos por default");
            Refrigerador[] re = new Refrigerador[1];
            re[0] = new Refrigerador(0, 10, 20, 30);
            Cortinas[] co = new Cortinas[1]; 
            co[0] = new Cortinas(0, "Centro", "Arriba", new Date(100000), new Date(100000));
            Termostato[] te = new Termostato[1];
            te[0] = new Termostato(0, 10);
            DisMascota[] di = new DisMascota[1];
            di[0] = new DisMascota(0, new Date(100000), 5, new Date(100000), 10, new Date(100000), 5);
            Irrigador[] ir = new Irrigador[1];
            ir[0] = new Irrigador(0, new Date(100000), 10);
            Alarma[] al = new Alarma[1];
            al[0] = new Alarma(0, new Date(100000), new Date(100000), true);
            Lampara[] la = new Lampara[1];
            la[0] = new Lampara(0, "Centro", true);
            Luminaria[] lu = new Luminaria[1];
            lu[0] = new Luminaria(0, "Centro", 0, true);
            ListaObjetos[] listaO = new ListaObjetos[9];
            listaO[0] = new ListaObjetos(0, us.length, us);
            listaO[1] = new ListaObjetos(1, re.length, re);
            listaO[2] = new ListaObjetos(2, co.length, co);
            listaO[3] = new ListaObjetos(3, te.length, te);
            listaO[4] = new ListaObjetos(4, di.length, di);
            listaO[5] = new ListaObjetos(5, ir.length, ir);
            listaO[6] = new ListaObjetos(6, al.length, al);
            listaO[7] = new ListaObjetos(7, la.length, la);
            listaO[8] = new ListaObjetos(8, lu.length, lu);
            ListaCasa listaC = new ListaCasa(listaO.length, listaO);
            file.writeObject(listaC);
            file.close();
        }
        //Usuarios
        System.out.println("Leyendo usuarios y objetos...");
        ObjectInputStream fileR = new ObjectInputStream(new FileInputStream("Casa.txt"));
        ListaCasa auxC;
        auxC = (ListaCasa) fileR.readObject();
        auxC.imprimeListaObjeto();
        fileR.close();
        
        //Inicia Servidor
        ServerSocket ss = new ServerSocket(6666);
        System.out.println("Servidor listo...\nEsperando clientes...");
        while(true)
        {
            Socket cl = ss.accept();
            System.out.println("Cliente conectado desde " + cl.getInetAddress() + " : " + cl.getPort());
            //Recibimos objeto usuario
            ObjectInputStream entradaO = new ObjectInputStream(cl.getInputStream());
            Usuario auxC1;
            auxC1 = (Usuario) entradaO.readObject();
            //Buscamos usuario
            ListaObjetos auxLO = (ListaObjetos) auxC.getObjeto(0);
            Usuario[] auxUO = new Usuario[auxLO.getCantidad()];
            for(int i = 0; i < auxLO.getCantidad(); i ++)
            {
                auxUO[i] = (Usuario) auxLO.getObjeto(i);
            }
            int tipo = -1;
            for(int i = 0; i < auxLO.getCantidad(); i ++)
            {
                if(auxUO[i].getUsr().equals(auxC1.getUsr()) && auxUO[i].getPwd().equals(auxC1.getPwd()))
                {
                    System.out.println("Usuario encontrado");
                    tipo = auxUO[i].getTipo();
                    break;
                }
            }
            //Enviamos el archivo
            DataOutputStream salidaD = new DataOutputStream(cl.getOutputStream());
            DataInputStream entradaF;
            DataInputStream entradaD = new DataInputStream(new FileInputStream("Casa.txt"));
            DataOutputStream salidaF;
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
                    lon = entradaD.read(bufferO);
                    System.out.println("Bytes leidos: " + lon);
                    salidaD.writeInt((lon));
                    salidaD.write(bufferO);
                    //Recibiendo archivo+}
                    
                    entradaF = new DataInputStream(cl.getInputStream());
                    salidaF = new DataOutputStream(new FileOutputStream("Casa.txt"));
                    lon = entradaF.readInt();
                    System.out.println("Bytes leidos: " + lon);
                    entradaF.read(bufferO, 0, lon);
                    salidaF.write(bufferO, 0, lon);
                    entradaF.close();
                    salidaF.close();
                    
                    //Leyendo archivo
                break;
                case 1:
                    System.out.println("\nAdministrador conectado");
                    salidaD.writeInt(tipo);
                    lon = entradaD.read(bufferO);
                    System.out.println("Bytes leidos: " + lon);
                    salidaD.writeInt((lon));
                    salidaD.write(bufferO);
                    //Recibiendo archivo
                    
                    entradaF = new DataInputStream(cl.getInputStream());
                    salidaF = new DataOutputStream(new FileOutputStream("Casa.txt"));
                    lon = entradaF.readInt();
                    System.out.println("Bytes leidos: " + lon);
                    entradaF.read(bufferO, 0, lon);
                    salidaF.write(bufferO, 0, lon);
                    entradaF.close();
                    salidaF.close();
                    
                    //Leyendo archivo
                break;
            }
        }
    }
}
