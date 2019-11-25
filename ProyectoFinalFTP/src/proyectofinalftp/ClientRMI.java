/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinalftp;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author angel
 */
public class ClientRMI {
    
    private ClientRMI(){}
    
    public static void main(String[] args) {
        String host = "127.0.0.1";
        try {
            Registry registro = LocateRegistry.getRegistry(host, 9000);
            //Buscar el objeto de la interfaz en el registro
            Archivos stub = (Archivos) registro.lookup("Archivos");
            String archivo = "Casa.txt";
            boolean respuesta = stub.searchFile(archivo);
            System.out.println("El archivo " + archivo + " esta " + respuesta);
        } catch (Exception e) {
            System.out.println("Excepcion en el cliente");
            e.printStackTrace();
        }
    }
}
