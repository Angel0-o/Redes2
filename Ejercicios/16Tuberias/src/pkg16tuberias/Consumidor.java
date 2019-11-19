/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg16tuberias;

import java.io.DataInputStream;
import java.io.InputStream;

/**
 *
 * @author Alumno
 */
public class Consumidor extends Thread{
    private double promedio_anterior = 0;
    private DataInputStream entrada;
    
    public Consumidor(InputStream is)
    {
        entrada = new DataInputStream(is);
    }
    
    public void run()
    {
        for(;;)
        {
            try {
                double prom = entrada.readDouble();
                if(Math.abs(prom - promedio_anterior) > 0.01)
                {
                    System.out.println("El promedio actual es " + prom);
                    promedio_anterior = prom;
                }
            } catch (Exception e) {
            }
        }
    }
}
