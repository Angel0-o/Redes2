/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmicalculadora;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Alumno
 */
public interface Calculadora extends Remote{
    int suma (int a, int b) throws RemoteException;
    int resta (int a, int b) throws RemoteException;
    int multiplicacion (int a, int b) throws RemoteException;
    int division (int a, int b) throws RemoteException;
    
}
