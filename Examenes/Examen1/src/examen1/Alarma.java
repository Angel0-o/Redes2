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
public class Alarma implements Serializable{
        
    private int tipoClase;
    private int id;
    private Date horaInicio;
    private Date horaTermino;
    private boolean estado;
    
    public Alarma(int id, Date horaInicio, Date horaTermino, boolean estado)
    {
        this.tipoClase = 6;
        this.id = id;
        this.horaInicio = horaInicio;
        this.horaTermino = horaTermino;
        this.estado = estado;
    }
    
    public Alarma()
    {
        this.tipoClase = 6;
        this.id = 10;
        this.horaInicio = null;
        this.horaTermino = null;
        this.estado = false;
    }
    
    public void imprimeOs()
    {
        System.out.println("\tHoraInicio: " + horaInicio + "\tHoraTermino: " + horaTermino + "\tEstado: " + estado);
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

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraTermino() {
        return horaTermino;
    }

    public void setHoraTermino(Date horaTermino) {
        this.horaTermino = horaTermino;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
