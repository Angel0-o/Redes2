/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg18rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Angel
 */
public class Cliente {
    private Cliente(){}

    public static void main(String[] args) {
        String host = (args.length<1)? null:args[0];
        try {
            Registry registro = LocateRegistry.getRegistry(host);
            //Buscar el objeto suma en el registro
            Suma stub = (Suma) registro.lookup("Suma");
            int x = 5, y = 4;
            int respuesta = stub.suma(x, y);
            System.out.println("Resultado de " + x +" + " + y + " = " + respuesta);
        } catch (Exception e) {
            System.out.println("Excepcion en el cliente");
            e.printStackTrace();
        }
    }
    
}
