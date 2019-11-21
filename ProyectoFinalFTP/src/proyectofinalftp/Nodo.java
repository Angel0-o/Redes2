/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinalftp;

import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Angel
 */
public class Nodo {

    private String IP;
    private int Port;
    private String ID;

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public int getPort() {
        return Port;
    }

    public void setPort(int Port) {
        this.Port = Port;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Nodo() {
    }

    public Nodo(String ip, int port) {
        Port = port;
        IP = ip;
        ID = Integer.toString(port) + "_" + ip.substring(0, 9);
    }
}

class ListaNodos {

    private LinkedList<Nodo> listaObjetos;

//        public ListaNodos(int lon, Nodo[] noLis) {
//            for (int i = 0; i < lon; i++) {
//                listaObjetos.add(noLis[i]);
//            }
//        }
    public ListaNodos() {
        listaObjetos = new LinkedList();
    }

    public void insertOrder(Nodo node) {
        if (!listaObjetos.isEmpty()) {
            if (!listaObjetos.contains(node)) {
                for (int i = 0; i < listaObjetos.size(); i++) {
                    if (node.getPort() < listaObjetos.get(i).getPort()) {
                        listaObjetos.add(i, node);
                    }
                    if ((i + 1 == listaObjetos.size()) && node.getPort() > listaObjetos.get(i).getPort()) {
                        listaObjetos.addLast(node);
                    }
                }
            }
        } else {
            listaObjetos.add(node);
        }
    }

    public Nodo getNext(Nodo node) {
        int index = listaObjetos.indexOf(node);
        System.out.println("lon: " + listaObjetos.size() + "\t index: " + index);
        if(index == -1)
            return node;
        if(listaObjetos.size() == 1)
            return listaObjetos.get(index);
        if (index != -1) {
            return listaObjetos.get(index + 1);
        }else if (index == listaObjetos.size() - 1) {
            return listaObjetos.getFirst();
        } else {
            return listaObjetos.get(index);
        }
    }

    public Nodo getPrev(Nodo node) {
        int index = listaObjetos.indexOf(node);
        System.out.println("lon: " + listaObjetos.size() + "\t index: " + index);
        if(index == -1)
            return node;
        if(listaObjetos.size() == 1)
            return listaObjetos.get(index);
        if (index != -1) {
            return listaObjetos.get(index - 1);
        } else if (index == 0) {
            return listaObjetos.getLast();
        } else {
            return listaObjetos.get(index);
        }
    }

    public String[] getIDs(String[] listID) {
        listID = new String[listaObjetos.size()];
        Iterator nodeIterator = listaObjetos.iterator();
        int i = 0;
        Nodo aux;
        while (nodeIterator.hasNext()) {
            aux = (Nodo) nodeIterator.next();
            listID[i] = aux.getID();
            i++;
        }
        return listID;
    }
    
    public void imprime(ListaNodos lista){
        Iterator nodeIterator = listaObjetos.iterator();
        Nodo aux;
        System.out.println("Longitud: " + listaObjetos.size());
        while (nodeIterator.hasNext()) {
            aux = (Nodo) nodeIterator.next();
            System.out.println("ID: " + aux.getID() + "\tPort: " + aux.getPort() + "\tIP: " + aux.getIP());
        }
    }
}
