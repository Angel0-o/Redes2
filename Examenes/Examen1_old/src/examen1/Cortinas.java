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
public class Cortinas implements Serializable{
    
    private int tipoClase;
    private int id;
    private String ubicacion;
    private String posicion;
    private Date horaAper;
    private Date horaCierre;
    
    public Cortinas(int id, String ubi, String pos, Date horaA, Date horaC)
    {
        this.tipoClase = 2;
        this.id = id;
        this.ubicacion = ubi;
        this.posicion = pos;
        this.horaAper = horaA;
        this.horaCierre = horaC;
    }
    
    public void imprimeOs()
    {
        System.out.println("\tUbicacion: " + ubicacion + "\tPosicion: " + posicion + "\tHoraApertura: " + horaAper + "\tHoraCierre: " + horaCierre);
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

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public Date getHoraAper() {
        return horaAper;
    }

    public void setHoraAper(Date horaAper) {
        this.horaAper = horaAper;
    }

    public Date getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(Date horaCierre) {
        this.horaCierre = horaCierre;
    }
}
