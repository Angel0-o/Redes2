/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinalftp;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Angel
 */
public interface Archivos extends Remote{
    boolean searchFile (String fileF) throws RemoteException;
    String askFor(String fileF, int originPort) throws RemoteException;
}
