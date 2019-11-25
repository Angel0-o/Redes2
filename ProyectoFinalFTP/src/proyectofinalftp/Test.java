package proyectofinalftp;


import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Angel
 */
public class Test {
    public static void main(String args[]) { 
        Nodo n1 = new Nodo("172.29.32.35", 9000);
        Nodo n2 = new Nodo("172.29.32.35", 9001);
        Nodo n3 = new Nodo("172.29.32.35", 9002);
        Nodo n4 = new Nodo("172.29.32.35", 9003);
        Nodo n5 = new Nodo("172.29.32.35", 9004);
        ListaNodos lista = new ListaNodos();
        lista.add(n1);
        lista.printNode(lista.getNext(n1));
        lista.printNode(lista.getPrev(n1));
        lista.add(n4);
        lista.printNode(lista.getNext(n4));
        lista.printNode(lista.getPrev(n4));
        lista.add(n3);
        lista.printNode(lista.getNext(n3));
        lista.printNode(lista.getPrev(n3));
        lista.add(n1);
        lista.add(n2);
        lista.add(n5);
        lista.printList();
        lista.quicksort(0, lista.size - 1);
        lista.printList();
        lista.printNode(lista.getNext(n1));
        lista.printNode(lista.getPrev(n1));
        lista.printNode(lista.getNext(n5));
        lista.printNode(lista.getPrev(n5));
   } 
}
