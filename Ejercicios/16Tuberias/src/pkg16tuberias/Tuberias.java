/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg16tuberias;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 *
 * @author Alumno
 */
public class Tuberias {

    public static void main(String[] args) {
        try {
            PipedOutputStream po1 = new PipedOutputStream();
            PipedInputStream pi1 = new PipedInputStream(po1);
            PipedOutputStream po2 = new PipedOutputStream();
            PipedInputStream pi2 = new PipedInputStream(po2);
            Productor p = new Productor(po1);
            Filtro f = new Filtro(pi1,po2);
            Consumidor c = new Consumidor(pi2);
            p.start();
            f.start();
            c.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
