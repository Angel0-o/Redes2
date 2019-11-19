/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmicalculadora;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Alumno
 */
public class ClienteRMI {
    private ClienteRMI() {
    }

    public static void main(String[] args) {
        String host = "127.0.0.1";
        try {
            Registry registro = LocateRegistry.getRegistry(host);
            //Buscar el objeto Calculadora en el registro
            Calculadora stub = (Calculadora) registro.lookup("Calculadora");
            int x = 5, y = 4;
            int respuesta = stub.suma(x, y);
            System.out.println("Resultado de " + x + " + " + y + " = " + respuesta);
            respuesta = stub.resta(x, y);
            System.out.println("Resultado de " + x + " - " + y + " = " + respuesta);
            respuesta = stub.multiplicacion(x, y);
            System.out.println("Resultado de " + x + " * " + y + " = " + respuesta);
            respuesta = stub.division(x, y);
            System.out.println("Resultado de " + x + " / " + y + " = " + respuesta);
        } catch (Exception e) {
            System.out.println("Excepcion en el cliente");
            e.printStackTrace();
        }
    }
}
