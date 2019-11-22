/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinalftp;

import java.util.ArrayList;
import java.util.Collections;
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
        ID = Integer.toString(port) + "_" + ip.substring(9, 12);
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

    public void agrega(Nodo node) {
        if(!listaObjetos.contains(node))
            listaObjetos.add(node);
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
        int index = listaObjetos.indexOf(node.getPort());
        int indexF = index + 1;
        if (index == -1 | listaObjetos.size() == 1) {
            System.out.println("Not Found: lon: " + listaObjetos.size() + "\t index: " + indexF + "\tNodo: " + listaObjetos.getFirst().getPort());
            return node;
        }
        if (listaObjetos.size() > 1) {
            if (indexF >= listaObjetos.size()) {
                System.out.println("Next --> if: lon: " + listaObjetos.size() + "\t index: " + indexF + "\tNodo: " + listaObjetos.getFirst().getPort());
                return listaObjetos.getFirst();
            } else {
                System.out.println("Next --> else: lon: " + listaObjetos.size() + "\t index: " + indexF + "\tNodo: " + listaObjetos.get(indexF).getPort());
                return listaObjetos.get(indexF);
            }
        }
        return listaObjetos.get(indexF);
    }

    public Nodo getPrev(Nodo node) {
        int index = listaObjetos.indexOf(node);
        int indexF = index - 1;
        if (index == -1 | listaObjetos.size() == 1) {
            return node;
        }
        if (listaObjetos.size() > 1) {
            if (indexF < 0) {
                System.out.println("Prev --> if: lon: " + listaObjetos.size() + "\t index: " + indexF + "\tNodo: " + listaObjetos.getLast().getPort());
                return listaObjetos.getLast();
            } else {
                System.out.println("Prev --> else: lon: " + listaObjetos.size() + "\t index: " + indexF + "\tNodo: " + listaObjetos.get(indexF).getPort());
                return listaObjetos.get(indexF);
            }
        }
        return listaObjetos.get(indexF);
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

    public void imprime(ListaNodos lista) {
        Iterator nodeIterator = listaObjetos.iterator();
        Nodo aux;
        System.out.println("Longitud: " + listaObjetos.size());
        while (nodeIterator.hasNext()) {
            aux = (Nodo) nodeIterator.next();
            System.out.println("ID: " + aux.getID() + "\tPort: " + aux.getPort() + "\tIP: " + aux.getIP());
        }
    }
}

class ArrayNodos {

    private ArrayList<Integer> listaN;

    public ArrayNodos() {
        listaN = new ArrayList<>();
    }

    public void ordena() {
        Collections.sort(listaN);
    }

    public void agrega(int port) {
        if(!listaN.contains(port))
            listaN.add(port);
    }

    public int getNext(int port) {
        int index = listaN.indexOf(port);
        int indexF = index + 1;
        if (index == -1 | listaN.size() == 1) {
            System.out.println("Not Found: lon: " + listaN.size() + "\t index: " + indexF + "\tNodo: " + port + " ->" + listaN.get(index));
            return listaN.get(index);
        }
        if (listaN.size() > 1) {
            if (indexF >= listaN.size()) {
                System.out.println("Next --> if: lon: " + listaN.size() + "\t index: " + indexF + "\tNodo: " + port + " ->" + listaN.get(0));
                return listaN.get(0);
            } else {
                System.out.println("Next --> else: lon: " + listaN.size() + "\t index: " + indexF + "\tNodo: " + port + " ->" + listaN.get(indexF));
                return listaN.get(indexF);
            }
        }
        return listaN.get(indexF);
    }
    
    public int getPrev(int port) {
        int index = listaN.indexOf(port);
        int indexF = index - 1;
        if (index == -1 | listaN.size() == 1) {
            System.out.println("Not Found: lon: " + listaN.size() + "\t index: " + indexF + "\tNodo: " + port + " ->"  + listaN.get(index));
            return listaN.get(index);
        }
        if (listaN.size() > 1) {
            if (indexF < 0) {
                System.out.println("Prev --> if: lon: " + listaN.size() + "\t index: " + indexF + "\tNodo: " + port + " ->"  + listaN.get(listaN.size() - 1));
                return listaN.get(listaN.size() - 1);
            } else {
                System.out.println("Prev --> else: lon: " + listaN.size() + "\t index: " + indexF + "\tNodo: " + port + " ->"  + listaN.get(indexF));
                return listaN.get(indexF);
            }
        }
        return listaN.get(indexF);
    }

}
