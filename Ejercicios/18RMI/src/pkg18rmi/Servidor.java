/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg18rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Angel
 */
public class Servidor implements Suma{
    private Servidor(){}
    
    public int suma(int a, int b)
    {return a + b;}

    public static void main(String[] args) {
        try {
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            System.out.println("RMI Listo..");
        } catch (Exception e) {
            System.out.println("Excepcion del registro RMI");
            e.printStackTrace();
        }
        try {
            System.setProperty("java.rmi.server.codebase", "file:/C:/temp/suma");
            Servidor obj = new Servidor();
            Suma stub = (Suma)UnicastRemoteObject.exportObject(obj,0);
            //Ligamos el objeto remoto en el registro
            Registry registro = LocateRegistry.getRegistry();
            registro.bind("Suma", stub);
            System.out.println("Servidor Listo..");
        } catch (Exception e) {
        }
    }
}
