/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg11hilos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Alumno
 */
public class CEcoHilo {
    
    public static void main(String[] args) throws UnknownHostException, IOException
    {
        InetAddress srv = InetAddress.getByName("127.0.0.1");
        Socket cl = new Socket(srv, 9000);
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
        BufferedReader br = new BufferedReader(new InputStreamReader(cl.getInputStream()));
        BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
        String linea = "";
        System.out.println("Escribe un mensaje \n<Enter> para enviar\n<SALIR> para terminar\n");
        for(;;)
        {
            linea = br2.readLine();
            pw.println(linea);
            pw.flush();
            if (linea.indexOf("SALIR") >= 0) {
                System.out.println("Cliente se va....");
                cl.close();
                System.exit(0);
            }
            String eco = br.readLine();
            System.out.println("ECO: " + eco);
        }
    }
}
