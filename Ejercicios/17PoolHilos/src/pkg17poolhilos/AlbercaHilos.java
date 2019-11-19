/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg17poolhilos;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Alumno
 * La alaberca de hilos los crea y administra, pero mantiene los recursos, no los destruye
 */
public class AlbercaHilos {

    public static void main(String[] args) {
        System.out.println("Comienza la ejecucion");
        //Se crea la alberca con 10 hilos
        ExecutorService ex = Executors.newFixedThreadPool(10);
        TareaAlbercaHilos t;
        for(int i = 0; i < 200;i ++)
        {
            //Se crea un nuevo hilo
            t = new TareaAlbercaHilos("" + i);
            //Se ejecuta el nuevo hilo
            ex.execute(t);
        }
        ex.shutdown();
    }    
}

class TareaAlbercaHilos implements Runnable
{
    private int sleepTime;
    private String name;
    
    public TareaAlbercaHilos(String name)
    {
        this.name = name;
        sleepTime = 1000;
    }
    
    public void run() {
        try {
            System.out.println("El hilo de la Tarea " + this.name + " va a dormir durante " + sleepTime + " milisegundos");
            Thread.sleep(sleepTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Este hilo ya durmio bastante");
    }
    
}