/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg14mutex;

import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Angel
 */
public class Mutex implements Runnable{

    int cont;
    ReentrantLock r1;
    
    public Mutex()
    {
        this.cont = 0;
        r1 = new ReentrantLock();
    }
    
    public int getCont() {
        return cont;
    }

    public void setCont(int cont) {
        this.cont = cont;
    }

    @Override
    public void run() {
        System.out.println("Comienza mutex...");
        r1.lock();
        int tmp = cont;
        try {
            Thread.sleep(100);
            tmp ++;
            cont = tmp;
        } catch (InterruptedException ex) {
            Logger.getLogger(Mutex.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            r1.unlock();
        }
    }
    
    public static void main(String[] args)
    {
        try {
            Mutex m = new Mutex();
            Thread t1 = new Thread(m);
            Thread t2 = new Thread(m);
            Thread t3 = new Thread(m);
            Thread t4 = new Thread(m);
            Thread t5 = new Thread(m);
            t1.start();
            t2.start();
            t3.start();
            t4.start();
            t5.start();
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
            System.out.println("Resultado: " + m.getCont());
        } catch (InterruptedException ex) {
            Logger.getLogger(Mutex.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
