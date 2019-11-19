/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg15semaforos;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alumno
 */
public class Restaurante {

    private Semaphore mesas;
    //Un semaforo es una variable no negativa que permite a una cantida de hilos usar la parte critica del codigo
    public Restaurante(int contadorMesas)
    {
        //Se crea semaforo con las mesas disponibles
        this.mesas = new Semaphore(contadorMesas);
    }
    
    public void obtenerMesa(int idCliente)
    {
        try {
            System.out.println("Cliente #" + idCliente + " esta intentando conseguir mesa");
            //Adquiere permiso del semaforo para usar una mesa
            mesas.acquire();
            System.out.println("Cliente #" + idCliente + " consigue una mesa");
        } catch (InterruptedException ex) {
            Logger.getLogger(Restaurante.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void regresaMesa(int idCliente)
    {
        System.out.println("Cliente #" + idCliente + " devolvio mesa");
        //Incrementando el valor del semaforo
        mesas.release();
    }
    
    public static void main(String[] args) {
        Restaurante r = new Restaurante(2);
        for(int i = 1;i <= 5; i ++)
        {
            Cliente c = new Cliente(r,i);
            c.start();
        }
    }
    
}
