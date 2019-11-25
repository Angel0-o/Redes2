/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinalftp;

import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author angel
 */
public class ServerRMI implements Archivos {

    private String path;

    public ServerRMI(String p, int puerto) {
        path = p;
    }

    public boolean searchFile(String fileF) {
        File directory = new File(path);
        for (File archivo : directory.listFiles()) {
            if (archivo.isDirectory()) {
                searchFile(archivo.getAbsolutePath());
            } else {
                File aux = archivo;
                if(aux.getName().equals(fileF))
                    return true;
            }
        }
        return false;
    }
    
    public static void main(String[] args) {
        try {
            //java.rmi.registry.LocateRegistry.createRegistry(1099);
            System.out.println("RMI Listo..");
        } catch (Exception e) {
            System.out.println("Excepcion del registro RMI");
            e.printStackTrace();
        }
        try {
            System.setProperty("java.rmi.server.codebase", "file:/C:/temp/archivos");
            ServerRMI obj = new ServerRMI("C:/FTP_R2/9000", 9000);
            // Busca objeto de la interfaz en el registro 
            Archivos stub = (Archivos) UnicastRemoteObject.exportObject(obj, 0);
            //Creamos el registro y o ponemos a escuchar en el puerto
            Registry registro = LocateRegistry.createRegistry(9000);
            //Ligamos el objeto remoto en el registro
            registro.bind("Archivos", stub);
            System.out.println("Servidor Listo..");
        } catch (Exception e) {
        }
    }
}
