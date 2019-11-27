/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinalftp;

import java.io.File;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JTextPane;

/**
 *
 * @author angel
 */
public class ServerRMI implements Archivos {

    private String path;
    private int myPort;
    private int portNext;
    private JTextPane logArea;

    public JTextPane getLogArea() {
        return logArea;
    }

    public void setLogArea(JTextPane logArea) {
        this.logArea = logArea;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getMyPort() {
        return myPort;
    }

    public void setMyPort(int myPort) {
        this.myPort = myPort;
    }

    public int getPortNext() {
        return portNext;
    }

    public void setPortNext(int portNext) {
        this.portNext = portNext;
    }

    public ServerRMI(String p, int pu, int pnx, JTextPane log) {
        path = p;
        myPort = pu;
        portNext = pnx;
        logArea = log;
    }

    public boolean searchFile(String fileF) {
        File directory = new File(path);
        for (File archivo : directory.listFiles()) {
            if (archivo.isDirectory()) {
                searchFile(archivo.getAbsolutePath());
            } else {
                File aux = archivo;
                if (aux.getName().equals(fileF)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String askFor(String fileF, int originPort) {
        String msg = "X_X";
        msg+="Searching...";
        logArea.setText(msg);
        System.out.println("Searching....");
        if (searchFile(fileF)) {
            msg = fileF + " -> Found on : " + myPort;
            logArea.setText(msg);
            System.out.println(msg + "<br>");
//            if(originPort != myPort)
//                startCliRMI(fileF,originPort);
            return msg;
        } else {
            msg = fileF + " -> Not Found on : " + myPort;
            logArea.setText(msg);
            System.out.println(msg + "<br>");
            startCliRMI(fileF, originPort);
        }
        return msg;
    }

    public void startCliRMI(String fileF, int originPort) {
        try {
            String resp, aux;
            Registry registro = LocateRegistry.getRegistry("127.0.0.1", portNext);
            //Buscar el objeto de la interfaz en el registro
            Archivos stub = (Archivos) registro.lookup("Archivos");
            resp = stub.askFor(fileF,originPort);
            aux = logArea.getText();
            logArea.setText(portNext + " : " +resp);
//            logArea.add(resp, logArea);
        } catch (NotBoundException | RemoteException e) {
            System.out.println("Excepcion en el cliente RMI");
            e.printStackTrace();
        }
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
//            ServerRMI obj = new ServerRMI("C:/FTP_R2/9000", 9000);
            // Busca objeto de la interfaz en el registro 
//            Archivos stub = (Archivos) UnicastRemoteObject.exportObject(obj, 0);
            //Creamos el registro y o ponemos a escuchar en el puerto
            Registry registro = LocateRegistry.createRegistry(9000);
            //Ligamos el objeto remoto en el registro
//            registro.bind("Archivos", stub);
            System.out.println("Servidor Listo..");
        } catch (Exception e) {
        }
    }
}
