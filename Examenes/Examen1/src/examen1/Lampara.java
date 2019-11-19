/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examen1;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Angel
 */
public class Lampara implements Serializable{
    
    private int tipoClase;
    private int id;
    private String ubicacion;
    private boolean estado;
    
    public Lampara(int id, String ubi, boolean est)
    {
        this.tipoClase = 7;
        this.id = id;
        this.ubicacion = ubi;
        this.estado = est;
    }
    
    public Lampara()
    {
        this.tipoClase = 7;
        this.id = 10;
        this.ubicacion = "";
        this.estado = false;
    }
    
    public void imprimeOs()
    {
        System.out.println("\tUbicacion: " + ubicacion + "\tEstado: " + estado);
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

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
