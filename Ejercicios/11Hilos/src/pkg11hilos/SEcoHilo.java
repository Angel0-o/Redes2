/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg11hilos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alumno
 */
public class SEcoHilo {
    
    public static void main(String[] args) throws IOException {
        ServerSocket s = new ServerSocket(9000);
        System.out.println("Servidor listo en el puerto " + s.getLocalPort());
        for(;;)
        {
            Socket cl = s.accept();
            System.out.println("Cliente conectado....");
            Manejador m = new Manejador(cl);
            m.start();
        }
    }
}

class Manejador extends Thread
{
    Socket cl;
    
    public Manejador(Socket cl)
    {
        this.cl = cl;
    }
    
    public void run()
    {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
            BufferedReader br = new BufferedReader(new InputStreamReader(cl.getInputStream()));
            String linea = "";
            for(;;)
            {
                linea = br.readLine();
                System.out.println("Recibiendo mensaje " + linea);
                if(linea.indexOf("SALIR") >= 0)
                {
                    System.out.println("Cliente se va....");
                    cl.close();
                    break;
                }
                pw.println(linea);
                pw.flush();
            }
        } catch (IOException ex) {
            Logger.getLogger(Manejador.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            pw.close();
        }
    }
}
