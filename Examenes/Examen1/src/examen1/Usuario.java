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
public class Usuario implements Serializable{
    
    static final long serialVersionUID = 42L;
    
    private int tipoClase;
    private int id;
    private String usr;
    private String pwd;
    private String nombre;
    private int tipo;
    
    public Usuario(int id, String usuario, String password, String nombre, int tipo)
    {
        this.tipoClase = 0;
        this.id = id;
        this.usr = usuario;
        this.pwd = password;
        this.nombre = nombre;
        this.tipo = tipo;
    }
    
    public Usuario()
    {
        this.tipoClase = 0;
        this.id = 11;
        this.usr = "";
        this.pwd = "";
        this.nombre = "";
        this.tipo = 0;
    }
    
    public void imprimeOs()
    {
        System.out.println("\tUsuario: " + usr + "\tPassword: " + "*******" + "\tNombre: " + nombre + "\tTipo: " + tipo);
    }

    public int getTipoClase() {
        return tipoClase;
    }

    public void setTipoClase(int tipoClase) {
        this.tipoClase = tipoClase;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}

class ListaUsuarios implements Serializable{
    
    static final long serialVersionUID = 42L;
    private int tipoClase;
    private int cantidad;
    private LinkedList usuarios = new LinkedList();
    
    public ListaUsuarios(int tamano, Usuario[] o1)
    {
        this.tipoClase = 0;
        for(int i=0; i<tamano;i++)
            usuarios.add(o1[i]);
    }
    
    public int getTamano(){
        return usuarios.size();
    }
    
    public Usuario getObjeto(int i){
        return (Usuario) usuarios.get(i);
    }
    
    public void imprimeUsuario(){
        ListIterator li = usuarios.listIterator();
        Usuario o1;
        
        System.out.println("TipoClase: " + this.tipoClase + "\nUsuarios["+usuarios.size()+"]:");
        while(li.hasNext()){
            o1 = (Usuario) li.next();
            o1.imprimeOs();
        }
    }
}