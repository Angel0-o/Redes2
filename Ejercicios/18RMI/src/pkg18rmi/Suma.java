/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg18rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Angel
 */
public interface Suma extends Remote{
    int suma (int a, int b) throws RemoteException;
}
