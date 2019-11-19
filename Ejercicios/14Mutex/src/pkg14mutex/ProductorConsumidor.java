package pkg14mutex;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Angel
 */
public class ProductorConsumidor {
    public static void main(String[] args)
    {
        Shared s = new Shared();
        new Producer(s).start();
        new Consumer(s).start();
    }
}

class Shared
{
    private volatile char c;
    private volatile boolean available;
    private final Lock lock;
    private final Condition condition;
    
    Shared()
    {
        c = '0';//valor nulo
        available = false;
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }
    
    Lock getlock()
    {
        return lock;
    }
    
    char getShared()
    {
        lock.lock();
        try
        {
            while (!available) {
                try {
                    condition.await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Shared.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            available = false;
            condition.signal();
        }
        finally
        {
            lock.unlock();
            return c;
        }
    }
    
    void setShared(char c)
    {
        lock.lock();
        try
        {
            while (available) {
                try {
                    condition.await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Shared.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            this.c = c;
            available = true;
            condition.signal();
        }
        finally
        {
            lock.unlock();
        }
    }
}
