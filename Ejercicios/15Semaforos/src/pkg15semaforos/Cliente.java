/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg15semaforos;

import java.util.Random;

/**
 *
 * @author Alumno
 */
public class Cliente extends Thread{
    private Restaurante r;
    private int idCliente;
    private static final Random aleatorio = new Random();
    
    public Cliente(Restaurante r, int idCliente)
    {
        this.r = r;
        this.idCliente = idCliente;
    }
    
    public void run()
    {
        r.obtenerMesa(idCliente);
        try {
            int tiempoComida = aleatorio.nextInt(30) + 1;
            System.out.println("Cliente #" + idCliente + " come por " + tiempoComida + " segundos");
            Thread.sleep(tiempoComida * 1000);
            System.out.println("Cliente #" + idCliente + " termino de comer");
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            r.regresaMesa(idCliente);
        }
    }
}
