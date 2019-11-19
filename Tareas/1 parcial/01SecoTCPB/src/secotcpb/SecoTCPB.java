/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package secotcpb;

/**
 *
 * @author alumno
 */
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SecoTCPB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            //Se crea Server Socket
            ServerSocket s = new ServerSocket(1234);
            System.out.println("Esperando clientes...");
            //Iniciamos ciclo infinito
            for(;;)
            {
                //Bloqueo
                Socket cl = s.accept();
                System.out.println("Cliete conectado desde direccion: " + cl.getInetAddress() + " Puerto: " + cl.getPort());
                String mensaje = "Hola mundo";
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
                //Se envia el mensaje
                pw.println(mensaje);
                pw.flush();
                BufferedReader b2 = new BufferedReader(new InputStreamReader(cl.getInputStream()));
                String msg = b2.readLine();
                System.out.println("Mensaje del cliente: " + msg);
                //Se limpia el flujo
                b2.close();
                pw.close();
            }//for
        } catch (IOException ex) {
            Logger.getLogger(SecoTCPB.class.getName()).log(Level.SEVERE, null, ex);
        }//catch
    }//main
    
}//class
