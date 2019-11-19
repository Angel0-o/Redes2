/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg16tuberias;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.Thread.sleep;
import java.util.Random;
/**
 *
 * @author Alumno
 */
public class Productor extends Thread{
    private DataOutputStream salida;
    private Random aleatorio = new Random();
    
    public Productor(OutputStream os)
    {
        salida = new DataOutputStream(os);
    }
    
    public void run()
    {
        for(;;)
        {
            try {
                double num = aleatorio.nextDouble();
                salida.writeDouble(num);
                salida.flush();
                sleep(Math.abs(aleatorio.nextInt()%1000));
                } catch (Exception e) {
            }
        }
    }
}

class Filtro extends Thread
{
    private DataInputStream entrada;
    private DataOutputStream salida;
    private double total = 0;
    private int cuenta = 0;
    
    public Filtro(InputStream is, OutputStream os)
    {
        entrada = new DataInputStream(is);
        salida = new DataOutputStream(os);
    }
    
    public void run()
    {
        for (;;) {
            try {
                double x = entrada.readDouble();
                total += x;
                cuenta ++;
                if(cuenta != 0)
                {
                    salida.writeDouble(total/cuenta);
                    salida.flush();
                }
            } catch (Exception e) {
            }
        }
    }
}
