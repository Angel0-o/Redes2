package pkg14mutex;


import java.util.concurrent.locks.Lock;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Angel
 */
public class Producer extends Thread {
    private final Lock l;
    private final Shared s;
    
    Producer(Shared s)
    {
        this.s = s;
        l = s.getlock();
    }
    
    public void run()
    {
        for(char ch = 'A';ch <= 'Z'; ch ++)
        {
            l.lock();
            s.setShared(ch);
            System.out.println(ch + "Productor");
            l.unlock();
        }
    }
}
