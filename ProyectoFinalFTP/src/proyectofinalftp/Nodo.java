package proyectofinalftp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        ID = Integer.toString(port) + "_" + ip.substring(5, 9);
    }

    public boolean equals(Nodo node) {
        return node.getPort() == Port;
    }
}

class ListaNodos {

    private Nodo[] lista;
    int first;
    int last;
    int pointer;
    int size;

    public ListaNodos() {
        lista = new Nodo[20];
        first = 0;
    }

    public Nodo[] getLista() {
        return lista;
    }

    public void setLista(Nodo[] lista) {
        this.lista = lista;
    }

    public boolean isEmpty() {
        for (Nodo element : lista) {
            if (element != null) {
                return false;
            }
        }
        return true;
    }

    public void add(Nodo node) {
        if (isEmpty()) {
            lista[0] = node;
            pointer = 0;
            size = 1;
        } else {
            if (!contains(node)) {
                pointer += 1;
                last = pointer;
                size = last + 1;
                lista[pointer] = node;
            } else {
//                System.out.println("Nodo existente");
            }
        }
    }

    public Nodo get(int x) {
        Nodo aux = new Nodo("100.0.0.0", 0);
        if (lista[x] != null) {
            return lista[x];
        } else {
            System.out.println("ListaNodos.get()\tIndex null");
            return aux;
        }
    }

    public boolean contains(Nodo node) {
        for (int i = 0; i < size; i++) {
            if (lista[i].equals(node)) {
                return true;
            }
        }
        return false;
    }

    public void quicksort(int izq, int der) {
        int i = izq;
        int j = der;
        int pivote = lista[(i + j) / 2].getPort();
        do {
            while (lista[i].getPort() < pivote) {
                i++;
            }
            while (lista[j].getPort() > pivote) {
                j--;
            }
            if (i <= j) {
                Nodo aux = lista[i];
                lista[i] = lista[j];
                lista[j] = aux;
                i++;
                j--;
            }
        } while (i <= j);
        if (izq < j) {
            quicksort(izq, j);
        }
        if (i < der) {
            quicksort(i, der);
        }
    }

    public Nodo getNext(Nodo node) {
        if (contains(node)) {
            if (size == 1) {
                return node;
            }
            for (int i = 0; i < size; i++) {
                if (lista[i].equals(node)) {
                    if (i == last) {
                        return lista[first];
                    } else {
                        return lista[i + 1];
                    }
                }
            }
        }
        System.out.println("Nodo no encontrado");
        return node;
    }

    public Nodo getPrev(Nodo node) {
        if (contains(node)) {
            if (size == 1) {
                return node;
            }
            for (int i = 0; i < size; i++) {
                if (lista[i].equals(node)) {
                    if (i == first) {
                        return lista[last];
                    } else {
                        return lista[i - 1];
                    }
                }
            }
        }
        System.out.println("Nodo no encontrado");
        return node;
    }
    
    public String[] getIDS(){
        String[] aux = new String[size];
        for (int i = 0; i < size; i++) {
            aux[i] = lista[i].getID();
        }
        return aux;
    }

    public void printList() {
        System.out.println("Longitud: " + size);
        for (int i = 0; i < size; i++) {
            System.out.println("\tID: " + lista[i].getID() + "\tPort: " + lista[i].getPort() + "\tIP: " + lista[i].getIP());
        }
    }

    public void printNode(Nodo node) {
        System.out.println("\tID: " + node.getID() + "\tPort: " + node.getPort() + "\tIP: " + node.getIP());
    }
}
