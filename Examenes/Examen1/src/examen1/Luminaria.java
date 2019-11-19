/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examen1;

import java.io.Serializable;

/**
 *
 * @author Angel
 */
public class Luminaria implements Serializable{
        
    private int tipoClase;
    private int id;
    private String ubicacion;
    private int intensidad;
    private boolean estado;
    
    public Luminaria(int id, String ubi, int inte, boolean est)
    {
        this.tipoClase = 8;
        this.id = id;
        this.ubicacion = ubi;
        this.intensidad = inte;
        this.estado = est;
    }
    
    public Luminaria()
    {
        this.tipoClase = 8;
        this.id = 10;
        this.ubicacion = "";
        this.intensidad = 0;
        this.estado = false;
    }
    
    public void imprimeOs()
    {
        System.out.println("\tUbicacion: " + ubicacion + "\tIntensidad: " + intensidad + "\tEstado: " + estado);
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

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getIntensidad() {
        return intensidad;
    }

    public void setIntensidad(int intensidad) {
        this.intensidad = intensidad;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
