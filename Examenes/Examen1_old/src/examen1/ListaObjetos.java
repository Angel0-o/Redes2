/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examen1;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author Angel
 */
public class ListaObjetos implements Serializable{
    
    static final long serialVersionUID = 42L;

    private int tipoClase;
    private int cantidad;
    private LinkedList objetos = new LinkedList();
    
    public ListaObjetos(int tipoC, int tamano, Alarma[] o1)
    {
        this.tipoClase = tipoC;
        this.cantidad = tamano;
        for(int i=0; i<tamano;i++)
            objetos.add(o1[i]);
    }
    
    public ListaObjetos(int tipoC, int tamano, Cortinas[] o1)
    {
        this.tipoClase = tipoC;
        this.cantidad = tamano;
        for(int i=0; i<tamano;i++)
            objetos.add(o1[i]);
    }
    
    public ListaObjetos(int tipoC, int tamano, DisMascota[] o1)
    {
        this.tipoClase = tipoC;
        this.cantidad = tamano;
        for(int i=0; i<tamano;i++)
            objetos.add(o1[i]);
    }
    
    public ListaObjetos(int tipoC, int tamano, Irrigador[] o1)
    {
        this.tipoClase = tipoC;
        this.cantidad = tamano;
        for(int i=0; i<tamano;i++)
            objetos.add(o1[i]);
    }
    
    public ListaObjetos(int tipoC, int tamano, Lampara[] o1)
    {
        this.tipoClase = tipoC;
        this.cantidad = tamano;
        for(int i=0; i<tamano;i++)
            objetos.add(o1[i]);
    }
    
    public ListaObjetos(int tipoC, int tamano, Luminaria[] o1)
    {
        this.tipoClase = tipoC;
        this.cantidad = tamano;
        for(int i=0; i<tamano;i++)
            objetos.add(o1[i]);
    }
    
    public ListaObjetos(int tipoC, int tamano, Refrigerador[] o1)
    {
        this.tipoClase = tipoC;
        this.cantidad = tamano;
        for(int i=0; i<tamano;i++)
            objetos.add(o1[i]);
    }
    
    public ListaObjetos(int tipoC, int tamano, Termostato[] o1)
    {
        this.tipoClase = tipoC;
        this.cantidad = tamano;
        for(int i=0; i<tamano;i++)
            objetos.add(o1[i]);
    }
    
    public int getTipoClase() {
        return tipoClase;
    }

    public int getCantidad() {
        return cantidad;
    }
    
    public Object getObjeto(int i){
        return objetos.get(i);
    }
    
    public void imprimeObjeto(){
        ListIterator li = objetos.listIterator();
        switch(tipoClase)
        {
            case 1:
                Refrigerador o1;

                System.out.println("TipoClase: " + this.tipoClase + "\nObjetos["+objetos.size()+"]:");
                while(li.hasNext()){
                    o1 = (Refrigerador) li.next();
                    o1.imprimeOs();
                }
            break;
            
            case 2:
                Cortinas o2;

                System.out.println("TipoClase: " + this.tipoClase + "\nObjetos["+objetos.size()+"]:");
                while(li.hasNext()){
                    o2 = (Cortinas) li.next();
                    o2.imprimeOs();
                }
            break;
            
            case 3:
                Termostato o3;

                System.out.println("TipoClase: " + this.tipoClase + "\nObjetos["+objetos.size()+"]:");
                while(li.hasNext()){
                    o3 = (Termostato) li.next();
                    o3.imprimeOs();
                }
            break;
            
            case 4:
                DisMascota o4;

                System.out.println("TipoClase: " + this.tipoClase + "\nObjetos["+objetos.size()+"]:");
                while(li.hasNext()){
                    o4 = (DisMascota) li.next();
                    o4.imprimeOs();
                }
            break;
            
            case 5:
                Irrigador o5;

                System.out.println("TipoClase: " + this.tipoClase + "\nObjetos["+objetos.size()+"]:");
                while(li.hasNext()){
                    o5 = (Irrigador) li.next();
                    o5.imprimeOs();
                }
            break;
            
            case 6:
                Alarma o6;

                System.out.println("TipoClase: " + this.tipoClase + "\nObjetos["+objetos.size()+"]:");
                while(li.hasNext()){
                    o6 = (Alarma) li.next();
                    o6.imprimeOs();
                }
            break;
            
            case 7:
                Lampara o7;

                System.out.println("TipoClase: " + this.tipoClase + "\nObjetos["+objetos.size()+"]:");
                while(li.hasNext()){
                    o7 = (Lampara) li.next();
                    o7.imprimeOs();
                }
            break;
            
            case 8:
                Luminaria o8;

                System.out.println("TipoClase: " + this.tipoClase + "\nObjetos["+objetos.size()+"]:");
                while(li.hasNext()){
                    o8 = (Luminaria) li.next();
                    o8.imprimeOs();
                }
            break;
        }
    }
}
