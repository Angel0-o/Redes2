/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg04serializacion;

import java.io.Serializable;

/**
 *
 * @author Alumno
 */
public class Usuario implements Serializable{

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApaterno() {
        return apaterno;
    }

    public void setApaterno(String apaterno) {
        this.apaterno = apaterno;
    }

    public String getAmaterno() {
        return amaterno;
    }

    public void setAmaterno(String amaterno) {
        this.amaterno = amaterno;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
    
    String nombre;
    String apaterno;
    String amaterno;
    transient String pwd;
    int edad;
    
    public Usuario(String nombre, String apaterno, String amaterno, String pwd, int edad)
    {
        this.amaterno = amaterno;
        this.apaterno = apaterno;
        this.nombre = nombre;
        this.edad = edad;
        this.pwd = pwd;
    }
}
