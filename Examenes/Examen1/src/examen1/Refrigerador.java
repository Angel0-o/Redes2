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
public class Refrigerador implements Serializable{
        
    private int tipoClase;
    private int id;
    private int temFrigo;
    private int temCentro;
    private int temCharola;
    
    public Refrigerador(int id, int tempF, int tempCe, int tempCh)
    {
        this.tipoClase = 1;
        this.id = id;
        this.temFrigo = tempF;
        this.temCentro = tempCe;
        this.temCharola = tempCh;
    }
    
    public Refrigerador()
    {
        this.tipoClase = 1;
        this.id = 10;
        this.temFrigo = 0;
        this.temCentro = 0;
        this.temCharola = 0;
    }
    
    public void imprimeOs()
    {
        System.out.println("\tTempFrigo: " + temFrigo + "\tTemCentro: " + temCentro + "\tTemCharola: " + temCharola);
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

    public int getTemFrigo() {
        return temFrigo;
    }

    public void setTemFrigo(int temFrigo) {
        this.temFrigo = temFrigo;
    }

    public int getTemCentro() {
        return temCentro;
    }

    public void setTemCentro(int temCentro) {
        this.temCentro = temCentro;
    }

    public int getTemCharola() {
        return temCharola;
    }

    public void setTemCharola(int temCharola) {
        this.temCharola = temCharola;
    }
}
