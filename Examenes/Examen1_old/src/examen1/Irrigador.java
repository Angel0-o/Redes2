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
public class Irrigador implements Serializable{
        
    private int tipoClase;
    private int id;
    private Date horaRiego;
    private int tempRiego;
    
    public Irrigador(int id, Date horaR, int tempR)
    {
        this.tipoClase = 5;
        this.id = id;
        this.horaRiego = horaR;
        this.tempRiego = tempR;
    }
    
    public void imprimeOs()
    {
        System.out.println("\tHoraRiego: " + horaRiego + "\tTemperaturaRiego: " + tempRiego);
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

    public Date getHoraRiego() {
        return horaRiego;
    }

    public void setHoraRiego(Date horaRiego) {
        this.horaRiego = horaRiego;
    }

    public int getTempRiego() {
        return tempRiego;
    }

    public void setTempRiego(int tempRiego) {
        this.tempRiego = tempRiego;
    }
}
