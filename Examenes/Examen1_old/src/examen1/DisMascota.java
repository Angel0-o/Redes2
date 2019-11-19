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
public class DisMascota implements Serializable{
        
    private int tipoClase;
    private int id;
    private Date horaDes;
    private int cantDes;
    private Date horaCom;
    private int cantCom;
    private Date horaCena;
    private int cantCena;
    
    public DisMascota(int id, Date horaD, int cantD, Date horaCo, int cantCo, Date horaCe, int cantCe)
    {
        this.tipoClase = 4;
        this.id = id;
        this.horaDes = horaD;
        this.cantDes = cantD;
        this.horaCom = horaCo;
        this.cantCom = cantCo;
        this.horaCena = horaCe;
        this.cantCena = cantCe;
    }
    
    public void imprimeOs()
    {
        System.out.println("\tHoraDesayuno: " + horaDes + "\ttHoraComida: " + horaCom + "\ttHoraCena: " + horaCena);
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

    public Date getHoraDes() {
        return horaDes;
    }

    public void setHoraDes(Date horaDes) {
        this.horaDes = horaDes;
    }

    public int getCantDes() {
        return cantDes;
    }

    public void setCantDes(int cantDes) {
        this.cantDes = cantDes;
    }

    public Date getHoraCom() {
        return horaCom;
    }

    public void setHoraCom(Date horaCom) {
        this.horaCom = horaCom;
    }

    public int getCantCom() {
        return cantCom;
    }

    public void setCantCom(int cantCom) {
        this.cantCom = cantCom;
    }

    public Date getHoraCena() {
        return horaCena;
    }

    public void setHoraCena(Date horaCena) {
        this.horaCena = horaCena;
    }

    public int getCantCena() {
        return cantCena;
    }

    public void setCantCena(int cantCena) {
        this.cantCena = cantCena;
    }
}
