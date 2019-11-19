/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg05external;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author Angel
 */
public class ListaUsuarios implements Serializable{
    private  LinkedList lista = new LinkedList();
    int valor;

    public ListaUsuarios(String[] usuarios, String[] passwords) 
    {
        for(int i = 0; i < usuarios.length; i ++)
        {
            lista.add(new Usuario(usuarios[i], passwords[i]));
        }
    }
    
    public void muestraUsuario()
    {
        ListIterator li = lista.listIterator();
        Usuario u;
        while(li.hasNext())
        {
            u = (Usuario) li.next();
            u.muestraUsuario();
        }
    }
}
