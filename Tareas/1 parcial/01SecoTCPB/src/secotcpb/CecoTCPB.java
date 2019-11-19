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

public class CecoTCPB {
    
    public static void main(String[] args) 
    {
        try {
            BufferedReader b1 = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Escribe la direccion del servidor");
            String host = b1.readLine();
            System.out.println("Escribe el puerto del servidor");
            int pto = Integer.parseInt(b1.readLine());
            //Creamos el socket y nos conectamos
            Socket cl = new Socket(host, pto);
            BufferedReader b2 = new BufferedReader(new InputStreamReader(cl.getInputStream()));
            //Nos conectamos
            String mensaje = b2.readLine();
            System.out.println("Recibimos un mensaje desde el servidor \nMensaje: " + mensaje);
            System.out.println("Escribe un mensajepara el servidor");
            String msg = b1.readLine();
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
            pw.println(msg);
            //Creamos flujos y socket
            pw.flush();
            pw.close();
            b1.close();
            b2.close();
            cl.close();
        } catch (IOException ex) {
            Logger.getLogger(CecoTCPB.class.getName()).log(Level.SEVERE, null, ex);
        }//catch
    }//main
}//class
