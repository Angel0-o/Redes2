/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg05external;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 *
 * @author Angel
 */
public class Usuario implements Externalizable{
    
    private String usuario;
    private String password;
    
    public Usuario(String u, String p)
    {
        System.out.println("Crando usuario(" + u + ", "+ p + ")");
        usuario = u;
        password = p;
    }
    
    public Usuario()
    {
        System.out.println("Creando usuario vacio");
    }
    
    public void writeExternal(ObjectOutput out) throws IOException
    {
        System.out.println("Usuario.writeExternal");
        //Explicitamente indicamos los atributos a almacenar
        out.writeObject(usuario);
    }
    
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
    {
        System.out.println("Usuario.readExternal");
        //Explicitamente indicamos los atributos a recuperar
        usuario = (String) in.readObject();
    }
    
    public void muestraUsuario()
    {
        String cad = "Usuario: " + usuario + "Password: ";
        if(password == null)
            cad = cad + "No disponible";
        else
            cad = cad + password;
        System.out.println(cad);
    }
}
