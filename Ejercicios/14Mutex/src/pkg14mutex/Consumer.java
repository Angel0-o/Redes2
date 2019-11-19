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
public class Consumer extends Thread{
    private final Lock l;
    private final Shared s;

    Consumer(Shared s) {
        this.s = s;
        l = s.getlock();
    }

    public void run() {
        char ch;
        do
        {
            l.lock();
            ch = s.getShared();
            System.out.println(ch + " Consumidor");
            l.unlock();
        }while(ch != 'Z');
    }
}
