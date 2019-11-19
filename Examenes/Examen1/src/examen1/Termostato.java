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
public class Termostato implements Serializable{
        
    private int tipoClase;
    private int id;
    private int temperatura;
    
    public Termostato(int id, int temp)
    {
        this.tipoClase = 3;
        this.id = id;
        this.temperatura = temp;
    }
    
    public Termostato()
    {
        this.tipoClase = 3;
        this.id = 10;
        this.temperatura = 0;
    }
    
    public void imprimeOs()
    {
        System.out.println("\tTemperatura: " + temperatura);
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

    public int getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }
}
