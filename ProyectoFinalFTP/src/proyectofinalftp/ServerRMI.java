/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinalftp;

import java.io.File;
import java.rmi.RemoteException;

/**
 *
 * @author angel
 */
public class ServerRMI implements Archivos {

    private String path;

    public ServerRMI(String p) {
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
}
