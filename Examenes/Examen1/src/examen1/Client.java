/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examen1;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.Buffer;
import java.util.Date;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author Angel
 */
public class Client {
    
    static final long serialVersionUID = 42L;
    
    public static void main(String[] args) throws IOException, ClassNotFoundException 
    {
        String usuario,pass;
        int usrT, lon, opcion;
        byte[] bufferO = new byte[4096];
        boolean banderaU = true;
        while(banderaU)
        {
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Escribe tu nombre de Usuario");
            usuario = teclado.readLine();
            System.out.println("Escribe tu Contraseña");
            pass = teclado.readLine();

            Usuario usuarioC = new Usuario(0,usuario, pass, usuario, 0);

            Socket c = new Socket("127.0.0.1", 6666);
            //Enviamos el usuario
            ObjectOutputStream salidaO = new ObjectOutputStream(c.getOutputStream());
            salidaO.writeObject(usuarioC);
            //Recibimos el tipo de usuario
            DataInputStream entradaD = new DataInputStream(c.getInputStream());
            DataOutputStream salidaD;
            usrT = entradaD.readInt();
            //Creamos flujos para el archivo
            DataOutputStream file = new DataOutputStream(new FileOutputStream("CasaC.txt"));
            DataInputStream entradaF;
            ObjectInputStream fileR;
            ObjectOutputStream fileW;
            ListaCasa auxC;
            switch(usrT)
            {
                case -1:
                    System.out.println("\nError en usuario o contraseña");
                break;
                case 0:
                    System.out.println("\nUsuario valido");
                    banderaU = false;
                break;
                case 1:
                    System.out.println("\nAdministrador valido");
                    banderaU = false;
                break;
            }
            lon = entradaD.readInt();
            entradaD.read(bufferO,0,lon);
            file.write(bufferO,0,lon);
            //Leyendo
            System.out.println("Leyendo objetos...");
            fileR = new ObjectInputStream(new FileInputStream("CasaC.txt"));
            auxC = (ListaCasa) fileR.readObject();
            //auxC.imprimeListaObjeto();
            //Carganado Datos en memoria
            ListaObjetos[] listaO = new ListaObjetos[9];
            listaO[0] = (ListaObjetos) auxC.getObjeto(0);
            listaO[1] = (ListaObjetos) auxC.getObjeto(1);
            listaO[2] = (ListaObjetos) auxC.getObjeto(2);
            listaO[3] = (ListaObjetos) auxC.getObjeto(3);
            listaO[4] = (ListaObjetos) auxC.getObjeto(4);
            listaO[5] = (ListaObjetos) auxC.getObjeto(5);
            listaO[6] = (ListaObjetos) auxC.getObjeto(6);
            listaO[7] = (ListaObjetos) auxC.getObjeto(7);
            listaO[8] = (ListaObjetos) auxC.getObjeto(8);
            fileR.close();
            
            banderaU = true;
            while(banderaU)
            {
                //Interfaz
                System.out.println("-----------------------------------");
                System.out.println("| Escoja una opcion para realizar |");
                System.out.println("-----------------------------------");
                System.out.println("| 1.- Alta                        |");
                System.out.println("| 2.- Baja                        |");
                System.out.println("| 3.- Cambio                      |");
                System.out.println("| 4.- Consulta                    |");
                System.out.println("| 5.- Termino                     |");
                System.out.println("-----------------------------------");
                opcion = Integer.parseInt(teclado.readLine());
                int tipoC, id;
                String dato;
                ListIterator li;
                switch (opcion) {
                    case 1://Altas
                        System.out.println("\n------------------------------");
                        System.out.println("|  Escriba la clase de objeto  |");
                        System.out.println("--------------------------------");
                        System.out.println("| 0.- Usuario                  |");
                        System.out.println("| 1.- Refrigerador             |");
                        System.out.println("| 2.- Cortinas                 |");
                        System.out.println("| 3.- Termostato               |");
                        System.out.println("| 4.- DisMascota               |");
                        System.out.println("| 5.- Irrigador                |");
                        System.out.println("| 6.- Alarma                   |");
                        System.out.println("| 7.- Lampara                  |");
                        System.out.println("| 8.- Luminaria                |");
                        System.out.println("--------------------------------");
                        tipoC = Integer.parseInt(teclado.readLine());
                        switch (tipoC) {
                            case 0:
                                if (usrT == 1) {
                                    Usuario u1 = new Usuario();
                                    LinkedList auxU = listaO[0].getObjetos();
                                    u1.setId(auxU.size());
                                    System.out.println("\n------------------------------------");
                                    System.out.println("--> Escriba el nombre de usuario");
                                    dato = teclado.readLine();
                                    u1.setUsr(dato);
                                    System.out.println("--> Escriba la contraseña de usuario");
                                    dato = teclado.readLine();
                                    u1.setPwd(dato);
                                    System.out.println("--> Escriba tu nombre");
                                    dato = teclado.readLine();
                                    u1.setNombre(dato);
                                    System.out.println("--> Escriba el tipo de usuario\n--> 0: Usuario, 1: Administrador");
                                    dato = teclado.readLine();
                                    u1.setTipo(Integer.parseInt(dato));
                                    auxU.add(u1);
                                    listaO[0].setCantidad(auxU.size());
                                    listaO[0].setObjetos(auxU);
                                    System.out.println("Alta exitosa");
                                } else {
                                    System.out.println("[ERROR]: X_X\n\tNo cuentas con los permisos");
                                }
                                break;
                            case 1:
                                Refrigerador r1 = new Refrigerador();
                                LinkedList auxR = listaO[1].getObjetos();
                                r1.setId(auxR.size());
                                System.out.println("\n-----------------------------------------");
                                System.out.println("--> Escriba la Temperatura del frigorífico");
                                dato = teclado.readLine();
                                r1.setTemFrigo(Integer.parseInt(dato));
                                System.out.println("--> Escriba la Temperatura del área central");
                                dato = teclado.readLine();
                                r1.setTemCentro(Integer.parseInt(dato));
                                System.out.println("--> Temperatura del área de la charola");
                                dato = teclado.readLine();
                                r1.setTemCharola(Integer.parseInt(dato));
                                auxR.add(r1);
                                listaO[1].setCantidad(auxR.size());
                                listaO[1].setObjetos(auxR);
                                System.out.println("Alta exitosa");
                                break;
                            case 2:
                                Cortinas c1 = new Cortinas();
                                LinkedList auxCo = listaO[2].getObjetos();
                                c1.setId(auxCo.size());
                                System.out.println("\n-----------------------------------------");
                                System.out.println("--> Escriba la Ubicación de la cortina");
                                dato = teclado.readLine();
                                c1.setUbicacion(dato);
                                System.out.println("--> Escriba la Altura a la que se encuentra la cortina");
                                dato = teclado.readLine();
                                c1.setPosicion(dato);
                                System.out.println("--> Escriba la Hora a la que la cortina se abre");
                                dato = teclado.readLine();
                                c1.setHoraAper(new Date(Long.parseLong(dato)));
                                System.out.println("--> Escriba la Hora a la que la cortina se cierra");
                                dato = teclado.readLine();
                                c1.setHoraCierre(new Date(Long.parseLong(dato)));
                                auxCo.add(c1);
                                listaO[2].setCantidad(auxCo.size());
                                listaO[2].setObjetos(auxCo);
                                System.out.println("Alta exitosa");
                                break;
                            case 3:
                                Termostato t1 = new Termostato();
                                LinkedList auxT = listaO[3].getObjetos();
                                t1.setId(auxT.size());
                                System.out.println("\n-----------------------------------------");
                                System.out.println("--> Escriba la Temperatura deseada de la casa");
                                dato = teclado.readLine();
                                t1.setTemperatura(Integer.parseInt(dato));
                                auxT.add(t1);
                                listaO[3].setCantidad(auxT.size());
                                listaO[3].setObjetos(auxT);
                                System.out.println("Alta exitosa");
                                break;
                            case 4:
                                DisMascota d1 = new DisMascota();
                                LinkedList auxD = listaO[4].getObjetos();
                                d1.setId(auxD.size());
                                System.out.println("\n-----------------------------------------");
                                System.out.println("--> Escriba la Hora del desayuno");
                                dato = teclado.readLine();
                                d1.setHoraDes(new Date(Long.parseLong(dato)));
                                System.out.println("--> Escriba la cantidad de alimento");
                                dato = teclado.readLine();
                                d1.setCantDes(Integer.parseInt(dato));
                                System.out.println("--> Escriba la Hora de la comida");
                                dato = teclado.readLine();
                                d1.setHoraCom(new Date(Long.parseLong(dato)));
                                System.out.println("--> Escriba la cantidad de alimento");
                                dato = teclado.readLine();
                                d1.setCantCom(Integer.parseInt(dato));
                                System.out.println("--> Escriba la Hora de la cena");
                                dato = teclado.readLine();
                                d1.setHoraCena(new Date(Long.parseLong(dato)));
                                System.out.println("--> Escriba la cantidad de alimento");
                                dato = teclado.readLine();
                                d1.setCantCena(Integer.parseInt(dato));
                                auxD.add(d1);
                                listaO[4].setCantidad(auxD.size());
                                listaO[4].setObjetos(auxD);
                                System.out.println("Alta exitosa");
                                break;
                            case 5:
                                Irrigador i1 = new Irrigador();
                                LinkedList auxI = listaO[5].getObjetos();
                                i1.setId(auxI.size());
                                System.out.println("\n-----------------------------------------");
                                System.out.println("--> Escriba la Hora de inicio de riego");
                                dato = teclado.readLine();
                                i1.setHoraRiego(new Date(Long.parseLong(dato)));
                                System.out.println("--> Escriba el tiempo en minutos ");
                                dato = teclado.readLine();
                                i1.setTempRiego(Integer.parseInt(dato));
                                auxI.add(i1);
                                listaO[5].setCantidad(auxI.size());
                                listaO[5].setObjetos(auxI);
                                System.out.println("Alta exitosa");
                                break;
                            case 6:
                                Alarma a1 = new Alarma();
                                LinkedList auxA = listaO[6].getObjetos();
                                a1.setId(auxA.size());
                                System.out.println("\n-----------------------------------------");
                                System.out.println("--> Escriba la Hora de inicio");
                                dato = teclado.readLine();
                                a1.setHoraInicio(new Date(Long.parseLong(dato)));
                                System.out.println("--> Escriba la Hora de termino");
                                dato = teclado.readLine();
                                a1.setHoraTermino(new Date(Long.parseLong(dato)));
                                System.out.println("--> Escriba el estado\nEncendido: 1, Apagado: 0 ");
                                dato = teclado.readLine();
                                if (Integer.parseInt(dato) == 1) {
                                    a1.setEstado(true);
                                } else {
                                    a1.setEstado(false);
                                }
                                auxA.add(a1);
                                listaO[6].setCantidad(auxA.size());
                                listaO[6].setObjetos(auxA);
                                System.out.println("Alta exitosa");
                                break;
                            case 7:
                                Lampara la1 = new Lampara();
                                LinkedList auxLA = listaO[7].getObjetos();
                                la1.setId(auxLA.size());
                                System.out.println("\n-----------------------------------------");
                                System.out.println("--> Escriba la ubicacion de la lampara");
                                dato = teclado.readLine();
                                la1.setUbicacion(dato);
                                System.out.println("--> Escriba el estado\nEncendido: 1, Apagado: 0 ");
                                dato = teclado.readLine();
                                if (Integer.parseInt(dato) == 1) {
                                    la1.setEstado(true);
                                } else {
                                    la1.setEstado(false);
                                }
                                auxLA.add(la1);
                                listaO[7].setCantidad(auxLA.size());
                                listaO[7].setObjetos(auxLA);
                                System.out.println("Alta exitosa");
                                break;
                            case 8:
                                Luminaria lu1 = new Luminaria();
                                LinkedList auxLU = listaO[8].getObjetos();
                                lu1.setId(auxLU.size());
                                System.out.println("\n-----------------------------------------");
                                System.out.println("--> Escriba la ubicacion de la lampara");
                                dato = teclado.readLine();
                                lu1.setUbicacion(dato);
                                System.out.println("--> Escriba la intensidad de la luz ");
                                dato = teclado.readLine();
                                lu1.setIntensidad(Integer.parseInt(dato));
                                System.out.println("--> Escriba el estado\nEncendido: 1, Apagado: 0 ");
                                dato = teclado.readLine();
                                if (Integer.parseInt(dato) == 1) {
                                    lu1.setEstado(true);
                                } else {
                                    lu1.setEstado(false);
                                }
                                auxLU.add(lu1);
                                listaO[8].setCantidad(auxLU.size());
                                listaO[8].setObjetos(auxLU);
                                System.out.println("Alta exitosa");
                                break;
                            default:
                                System.out.println("Esta opcion no es valida");
                                break;
                        }
                        break;
                    case 2://Bajas
                        System.out.println("\n------------------------------");
                        System.out.println("|  Escriba la clase de objeto  |");
                        System.out.println("--------------------------------");
                        System.out.println("| 0.- Usuario                  |");
                        System.out.println("| 1.- Refrigerador             |");
                        System.out.println("| 2.- Cortinas                 |");
                        System.out.println("| 3.- Termostato               |");
                        System.out.println("| 4.- DisMascota               |");
                        System.out.println("| 5.- Irrigador                |");
                        System.out.println("| 6.- Alarma                   |");
                        System.out.println("| 7.- Lampara                  |");
                        System.out.println("| 8.- Luminaria                |");
                        System.out.println("--------------------------------");
                        tipoC = Integer.parseInt(teclado.readLine());
                        switch (tipoC) {
                            case 0:
                                if (usrT == 1) {
                                    Usuario u1 = new Usuario();
                                    System.out.println("\n------------------------------------");
                                    System.out.println("--> Escriba el ID a eliminar");
                                    id = Integer.parseInt(teclado.readLine());

                                    li = listaO[0].getObjetos().listIterator();

                                    System.out.println("\nTipoClase: " + listaO[0].getTipoClase() + "\nUsuarios[" + listaO[0].getCantidad() + "]:");
                                    while (li.hasNext()) {
                                        u1 = (Usuario) li.next();
                                        if (u1.getId() == id) {
                                            u1.imprimeOs();

                                            LinkedList auxU = listaO[0].getObjetos();
                                            auxU.remove(id);
                                            listaO[0].setCantidad(auxU.size());
                                            listaO[0].setObjetos(auxU);
                                        }
                                    }
                                    System.out.println("Baja Exitosa");
                                } else {
                                    System.out.println("[ERROR]: X_X\n\tNo cuentas con los permisos");
                                }
                                break;
                            case 1:
                                Refrigerador r1 = new Refrigerador();
                                System.out.println("\n------------------------------------");
                                System.out.println("--> Escriba el ID a eliminar");
                                id = Integer.parseInt(teclado.readLine());

                                li = listaO[1].getObjetos().listIterator();

                                System.out.println("\nTipoClase: " + listaO[1].getTipoClase() + "\nRefrigeradors[" + listaO[1].getCantidad() + "]:");
                                while (li.hasNext()) {
                                    r1 = (Refrigerador) li.next();
                                    if (r1.getId() == id) {
                                        r1.imprimeOs();

                                        LinkedList auxU = listaO[1].getObjetos();
                                        auxU.remove(id);
                                        listaO[1].setCantidad(auxU.size());
                                        listaO[1].setObjetos(auxU);
                                    }
                                }
                                System.out.println("Baja Exitosa");
                                break;
                            case 2:
                                Cortinas c1 = new Cortinas();
                                System.out.println("\n------------------------------------");
                                System.out.println("--> Escriba el ID a eliminar");
                                id = Integer.parseInt(teclado.readLine());

                                li = listaO[2].getObjetos().listIterator();

                                System.out.println("\nTipoClase: " + listaO[2].getTipoClase() + "\nCortinass[" + listaO[2].getCantidad() + "]:");
                                while (li.hasNext()) {
                                    c1 = (Cortinas) li.next();
                                    if (c1.getId() == id) {
                                        c1.imprimeOs();

                                        LinkedList auxU = listaO[2].getObjetos();
                                        auxU.remove(id);
                                        listaO[2].setCantidad(auxU.size());
                                        listaO[2].setObjetos(auxU);
                                    }
                                }
                                System.out.println("Baja Exitosa");
                                break;
                            case 3:
                                Termostato t1 = new Termostato();
                                System.out.println("\n------------------------------------");
                                System.out.println("--> Escriba el ID a eliminar");
                                id = Integer.parseInt(teclado.readLine());

                                li = listaO[3].getObjetos().listIterator();

                                System.out.println("\nTipoClase: " + listaO[3].getTipoClase() + "\nTermostatos[" + listaO[3].getCantidad() + "]:");
                                while (li.hasNext()) {
                                    t1 = (Termostato) li.next();
                                    if (t1.getId() == id) {
                                        t1.imprimeOs();

                                        LinkedList auxU = listaO[3].getObjetos();
                                        auxU.remove(id);
                                        listaO[3].setCantidad(auxU.size());
                                        listaO[3].setObjetos(auxU);
                                    }
                                }
                                System.out.println("Baja Exitosa");
                                break;
                            case 4:
                                DisMascota d1 = new DisMascota();
                                System.out.println("\n------------------------------------");
                                System.out.println("--> Escriba el ID a eliminar");
                                id = Integer.parseInt(teclado.readLine());

                                li = listaO[4].getObjetos().listIterator();

                                System.out.println("\nTipoClase: " + listaO[4].getTipoClase() + "\nCortinass[" + listaO[4].getCantidad() + "]:");
                                while (li.hasNext()) {
                                    d1 = (DisMascota) li.next();
                                    if (d1.getId() == id) {
                                        d1.imprimeOs();

                                        LinkedList auxU = listaO[4].getObjetos();
                                        auxU.remove(id);
                                        listaO[4].setCantidad(auxU.size());
                                        listaO[4].setObjetos(auxU);
                                    }
                                }
                                System.out.println("Baja Exitosa");
                                break;
                            case 5:
                                Irrigador i1 = new Irrigador();
                                System.out.println("\n------------------------------------");
                                System.out.println("--> Escriba el ID a eliminar");
                                id = Integer.parseInt(teclado.readLine());

                                li = listaO[5].getObjetos().listIterator();

                                System.out.println("\nTipoClase: " + listaO[5].getTipoClase() + "\nCortinass[" + listaO[5].getCantidad() + "]:");
                                while (li.hasNext()) {
                                    i1 = (Irrigador) li.next();
                                    if (i1.getId() == id) {
                                        i1.imprimeOs();

                                        LinkedList auxU = listaO[5].getObjetos();
                                        auxU.remove(id);
                                        listaO[5].setCantidad(auxU.size());
                                        listaO[5].setObjetos(auxU);
                                    }
                                }
                                System.out.println("Baja Exitosa");
                                break;
                            case 6:
                                Alarma a1 = new Alarma();
                                System.out.println("\n------------------------------------");
                                System.out.println("--> Escriba el ID a eliminar");
                                id = Integer.parseInt(teclado.readLine());

                                li = listaO[6].getObjetos().listIterator();

                                System.out.println("\nTipoClase: " + listaO[6].getTipoClase() + "\nCortinass[" + listaO[6].getCantidad() + "]:");
                                while (li.hasNext()) {
                                    a1 = (Alarma) li.next();
                                    if (a1.getId() == id) {
                                        a1.imprimeOs();

                                        LinkedList auxU = listaO[6].getObjetos();
                                        auxU.remove(id);
                                        listaO[6].setCantidad(auxU.size());
                                        listaO[6].setObjetos(auxU);
                                    }
                                }
                                System.out.println("Baja Exitosa");
                                break;
                            case 7:
                                Lampara la1 = new Lampara();
                                System.out.println("\n------------------------------------");
                                System.out.println("--> Escriba el ID a eliminar");
                                id = Integer.parseInt(teclado.readLine());

                                li = listaO[7].getObjetos().listIterator();

                                System.out.println("\nTipoClase: " + listaO[7].getTipoClase() + "\nCortinass[" + listaO[7].getCantidad() + "]:");
                                while (li.hasNext()) {
                                    la1 = (Lampara) li.next();
                                    if (la1.getId() == id) {
                                        la1.imprimeOs();

                                        LinkedList auxU = listaO[7].getObjetos();
                                        auxU.remove(id);
                                        listaO[7].setCantidad(auxU.size());
                                        listaO[7].setObjetos(auxU);
                                    }
                                }
                                System.out.println("Baja Exitosa");
                                break;
                            case 8:
                                Luminaria lu1 = new Luminaria();
                                System.out.println("\n------------------------------------");
                                System.out.println("--> Escriba el ID a eliminar");
                                id = Integer.parseInt(teclado.readLine());

                                li = listaO[8].getObjetos().listIterator();

                                System.out.println("\nTipoClase: " + listaO[8].getTipoClase() + "\nCortinass[" + listaO[8].getCantidad() + "]:");
                                while (li.hasNext()) {
                                    lu1 = (Luminaria) li.next();
                                    if (lu1.getId() == id) {
                                        lu1.imprimeOs();

                                        LinkedList auxU = listaO[8].getObjetos();
                                        auxU.remove(id);
                                        listaO[8].setCantidad(auxU.size());
                                        listaO[8].setObjetos(auxU);
                                    }
                                }
                                System.out.println("Baja Exitosa");
                                break;
                            default:
                                System.out.println("Esta opcion no es valida");
                                break;
                        }
                        break;
                    case 3://Cambios
                        System.out.println("\n------------------------------");
                        System.out.println("|  Escriba la clase de objeto  |");
                        System.out.println("--------------------------------");
                        System.out.println("| 0.- Usuario                  |");
                        System.out.println("| 1.- Refrigerador             |");
                        System.out.println("| 2.- Cortinas                 |");
                        System.out.println("| 3.- Termostato               |");
                        System.out.println("| 4.- DisMascota               |");
                        System.out.println("| 5.- Irrigador                |");
                        System.out.println("| 6.- Alarma                   |");
                        System.out.println("| 7.- Lampara                  |");
                        System.out.println("| 8.- Luminaria                |");
                        System.out.println("--------------------------------");
                        tipoC = Integer.parseInt(teclado.readLine());
                        switch (tipoC) {
                            case 0:
                                if (usrT == 1) {
                                    Usuario u1 = new Usuario();
                                    System.out.println("\n------------------------------------");
                                    System.out.println("--> Escriba el ID a cambiar");
                                    id = Integer.parseInt(teclado.readLine());

                                    li = listaO[0].getObjetos().listIterator();

                                    System.out.println("\nTipoClase: " + listaO[0].getTipoClase() + "\nUsuarios[" + listaO[0].getCantidad() + "]:");
                                    while (li.hasNext()) {
                                        u1 = (Usuario) li.next();
                                        if (u1.getId() == id) {
                                            u1.imprimeOs();

                                            LinkedList auxU = listaO[0].getObjetos();
                                            u1.setId(id);
                                            System.out.println("\n------------------------------------");
                                            System.out.println("--> Escriba el nombre de usuario");
                                            dato = teclado.readLine();
                                            u1.setUsr(dato);
                                            System.out.println("--> Escriba la contraseña de usuario");
                                            dato = teclado.readLine();
                                            u1.setPwd(dato);
                                            System.out.println("--> Escriba tu nombre");
                                            dato = teclado.readLine();
                                            u1.setNombre(dato);
                                            System.out.println("--> Escriba el tipo de usuario\n--> 0: Usuario, 1: Administrador");
                                            dato = teclado.readLine();
                                            u1.setTipo(Integer.parseInt(dato));
                                            auxU.remove(id);
                                            auxU.add(id, u1);
                                            listaO[0].setCantidad(auxU.size());
                                            listaO[0].setObjetos(auxU);
                                        }
                                    }
                                    System.out.println("Cambio Exitoso");
                                } else {
                                    System.out.println("[ERROR]: X_X\n\tNo cuentas con los permisos");
                                }
                                break;
                            case 1:
                                Refrigerador r1 = new Refrigerador();
                                System.out.println("\n------------------------------------");
                                System.out.println("--> Escriba el ID a cambiar");
                                id = Integer.parseInt(teclado.readLine());

                                li = listaO[1].getObjetos().listIterator();

                                System.out.println("\nTipoClase: " + listaO[1].getTipoClase() + "\nRefrigeradors[" + listaO[1].getCantidad() + "]:");
                                while (li.hasNext()) {
                                    r1 = (Refrigerador) li.next();
                                    if (r1.getId() == id) {
                                        r1.imprimeOs();

                                        LinkedList auxR = listaO[1].getObjetos();
                                        r1.setId(id);
                                        System.out.println("\n-----------------------------------------");
                                        System.out.println("--> Escriba la Temperatura del frigorífico");
                                        dato = teclado.readLine();
                                        r1.setTemFrigo(Integer.parseInt(dato));
                                        System.out.println("--> Escriba la Temperatura del área central");
                                        dato = teclado.readLine();
                                        r1.setTemCentro(Integer.parseInt(dato));
                                        System.out.println("--> Temperatura del área de la charola");
                                        dato = teclado.readLine();
                                        r1.setTemCharola(Integer.parseInt(dato));
                                        auxR.remove(id);
                                        auxR.add(id, r1);
                                        listaO[1].setCantidad(auxR.size());
                                        listaO[1].setObjetos(auxR);
                                    }
                                }
                                System.out.println("Cambio Exitoso");
                                break;
                            case 2:
                                Cortinas c1 = new Cortinas();
                                System.out.println("\n------------------------------------");
                                System.out.println("--> Escriba el ID a cambiar");
                                id = Integer.parseInt(teclado.readLine());

                                li = listaO[2].getObjetos().listIterator();

                                System.out.println("\nTipoClase: " + listaO[2].getTipoClase() + "\nCortinass[" + listaO[2].getCantidad() + "]:");
                                while (li.hasNext()) {
                                    c1 = (Cortinas) li.next();
                                    if (c1.getId() == id) {
                                        c1.imprimeOs();

                                        LinkedList auxCo = listaO[2].getObjetos();
                                        c1.setId(id);
                                        System.out.println("\n-----------------------------------------");
                                        System.out.println("--> Escriba la Ubicación de la cortina");
                                        dato = teclado.readLine();
                                        c1.setUbicacion(dato);
                                        System.out.println("--> Escriba la Altura a la que se encuentra la cortina");
                                        dato = teclado.readLine();
                                        c1.setPosicion(dato);
                                        System.out.println("--> Escriba la Hora a la que la cortina se abre");
                                        dato = teclado.readLine();
                                        c1.setHoraAper(new Date(Long.parseLong(dato)));
                                        System.out.println("--> Escriba la Hora a la que la cortina se cierra");
                                        dato = teclado.readLine();
                                        c1.setHoraCierre(new Date(Long.parseLong(dato)));
                                        auxCo.remove(id);
                                        auxCo.add(id, c1);
                                        listaO[2].setCantidad(auxCo.size());
                                        listaO[2].setObjetos(auxCo);
                                    }
                                }
                                System.out.println("Cambio Exitoso");
                                break;
                            case 3:
                                Termostato t1 = new Termostato();
                                System.out.println("\n------------------------------------");
                                System.out.println("--> Escriba el ID a cambiar");
                                id = Integer.parseInt(teclado.readLine());

                                li = listaO[3].getObjetos().listIterator();

                                System.out.println("\nTipoClase: " + listaO[3].getTipoClase() + "\nTermostatos[" + listaO[3].getCantidad() + "]:");
                                while (li.hasNext()) {
                                    t1 = (Termostato) li.next();
                                    if (t1.getId() == id) {
                                        t1.imprimeOs();

                                        LinkedList auxT = listaO[3].getObjetos();
                                        t1.setId(id);
                                        System.out.println("\n-----------------------------------------");
                                        System.out.println("--> Escriba la Temperatura deseada de la casa");
                                        dato = teclado.readLine();
                                        t1.setTemperatura(Integer.parseInt(dato));
                                        auxT.remove(id);
                                        auxT.add(id, t1);
                                        listaO[3].setCantidad(auxT.size());
                                        listaO[3].setObjetos(auxT);
                                    }
                                }
                                System.out.println("Cambio Exitoso");
                                break;
                            case 4:
                                DisMascota d1 = new DisMascota();
                                System.out.println("\n------------------------------------");
                                System.out.println("--> Escriba el ID a cambiar");
                                id = Integer.parseInt(teclado.readLine());

                                li = listaO[4].getObjetos().listIterator();

                                System.out.println("\nTipoClase: " + listaO[4].getTipoClase() + "\nCortinass[" + listaO[4].getCantidad() + "]:");
                                while (li.hasNext()) {
                                    d1 = (DisMascota) li.next();
                                    if (d1.getId() == id) {
                                        d1.imprimeOs();

                                        LinkedList auxD = listaO[4].getObjetos();
                                        d1.setId(id);
                                        System.out.println("\n-----------------------------------------");
                                        System.out.println("--> Escriba la Hora del desayuno");
                                        dato = teclado.readLine();
                                        d1.setHoraDes(new Date(Long.parseLong(dato)));
                                        System.out.println("--> Escriba la cantidad de alimento");
                                        dato = teclado.readLine();
                                        d1.setCantDes(Integer.parseInt(dato));
                                        System.out.println("--> Escriba la Hora de la comida");
                                        dato = teclado.readLine();
                                        d1.setHoraCom(new Date(Long.parseLong(dato)));
                                        System.out.println("--> Escriba la cantidad de alimento");
                                        dato = teclado.readLine();
                                        d1.setCantCom(Integer.parseInt(dato));
                                        System.out.println("--> Escriba la Hora de la cena");
                                        dato = teclado.readLine();
                                        d1.setHoraCena(new Date(Long.parseLong(dato)));
                                        System.out.println("--> Escriba la cantidad de alimento");
                                        dato = teclado.readLine();
                                        d1.setCantCena(Integer.parseInt(dato));
                                        auxD.remove(id);
                                        auxD.add(id, d1);
                                        listaO[4].setCantidad(auxD.size());
                                        listaO[4].setObjetos(auxD);
                                    }
                                }
                                System.out.println("Cambio Exitoso");
                                break;
                            case 5:
                                Irrigador i1 = new Irrigador();
                                System.out.println("\n------------------------------------");
                                System.out.println("--> Escriba el ID a cambiar");
                                id = Integer.parseInt(teclado.readLine());

                                li = listaO[5].getObjetos().listIterator();

                                System.out.println("\nTipoClase: " + listaO[5].getTipoClase() + "\nCortinass[" + listaO[5].getCantidad() + "]:");
                                while (li.hasNext()) {
                                    i1 = (Irrigador) li.next();
                                    if (i1.getId() == id) {
                                        i1.imprimeOs();

                                        LinkedList auxI = listaO[5].getObjetos();
                                        i1.setId(id);
                                        System.out.println("\n-----------------------------------------");
                                        System.out.println("--> Escriba la Hora de inicio de riego");
                                        dato = teclado.readLine();
                                        i1.setHoraRiego(new Date(Long.parseLong(dato)));
                                        System.out.println("--> Escriba el tiempo en minutos ");
                                        dato = teclado.readLine();
                                        i1.setTempRiego(Integer.parseInt(dato));
                                        auxI.remove(id);
                                        auxI.add(id, i1);
                                        listaO[5].setCantidad(auxI.size());
                                        listaO[5].setObjetos(auxI);
                                    }
                                }
                                System.out.println("Cambio Exitoso");
                                break;
                            case 6:
                                Alarma a1 = new Alarma();
                                System.out.println("\n------------------------------------");
                                System.out.println("--> Escriba el ID a cambiar");
                                id = Integer.parseInt(teclado.readLine());

                                li = listaO[6].getObjetos().listIterator();

                                System.out.println("\nTipoClase: " + listaO[6].getTipoClase() + "\nCortinass[" + listaO[6].getCantidad() + "]:");
                                while (li.hasNext()) {
                                    a1 = (Alarma) li.next();
                                    if (a1.getId() == id) {
                                        a1.imprimeOs();

                                        LinkedList auxA = listaO[6].getObjetos();
                                        a1.setId(id);
                                        System.out.println("\n-----------------------------------------");
                                        System.out.println("--> Escriba la Hora de inicio");
                                        dato = teclado.readLine();
                                        a1.setHoraInicio(new Date(Long.parseLong(dato)));
                                        System.out.println("--> Escriba la Hora de termino");
                                        dato = teclado.readLine();
                                        a1.setHoraTermino(new Date(Long.parseLong(dato)));
                                        System.out.println("--> Escriba el estado\nEncendido: 1, Apagado: 0 ");
                                        dato = teclado.readLine();
                                        if (Integer.parseInt(dato) == 1) {
                                            a1.setEstado(true);
                                        } else {
                                            a1.setEstado(false);
                                        }
                                        auxA.remove(id);
                                        auxA.add(id, a1);
                                        listaO[6].setCantidad(auxA.size());
                                        listaO[6].setObjetos(auxA);
                                    }
                                }
                                System.out.println("Cambio Exitoso");
                                break;
                            case 7:
                                Lampara la1 = new Lampara();
                                System.out.println("\n------------------------------------");
                                System.out.println("--> Escriba el ID a cambiar");
                                id = Integer.parseInt(teclado.readLine());

                                li = listaO[7].getObjetos().listIterator();

                                System.out.println("\nTipoClase: " + listaO[7].getTipoClase() + "\nCortinass[" + listaO[7].getCantidad() + "]:");
                                while (li.hasNext()) {
                                    la1 = (Lampara) li.next();
                                    if (la1.getId() == id) {
                                        la1.imprimeOs();

                                        LinkedList auxLA = listaO[7].getObjetos();
                                        la1.setId(id);
                                        System.out.println("\n-----------------------------------------");
                                        System.out.println("--> Escriba la ubicacion de la lampara");
                                        dato = teclado.readLine();
                                        la1.setUbicacion(dato);
                                        System.out.println("--> Escriba el estado\nEncendido: 1, Apagado: 0 ");
                                        dato = teclado.readLine();
                                        if (Integer.parseInt(dato) == 1) {
                                            la1.setEstado(true);
                                        } else {
                                            la1.setEstado(false);
                                        }
                                        auxLA.remove(id);
                                        auxLA.add(id, la1);
                                        listaO[7].setCantidad(auxLA.size());
                                        listaO[7].setObjetos(auxLA);
                                    }
                                }
                                System.out.println("Cambio Exitoso");
                                break;
                            case 8:
                                Luminaria lu1 = new Luminaria();
                                System.out.println("\n------------------------------------");
                                System.out.println("--> Escriba el ID a cambiar");
                                id = Integer.parseInt(teclado.readLine());

                                li = listaO[8].getObjetos().listIterator();

                                System.out.println("\nTipoClase: " + listaO[8].getTipoClase() + "\nCortinass[" + listaO[8].getCantidad() + "]:");
                                while (li.hasNext()) {
                                    lu1 = (Luminaria) li.next();
                                    if (lu1.getId() == id) {
                                        lu1.imprimeOs();

                                        LinkedList auxLU = listaO[8].getObjetos();
                                        lu1.setId(id);
                                        System.out.println("\n-----------------------------------------");
                                        System.out.println("--> Escriba la ubicacion de la lampara");
                                        dato = teclado.readLine();
                                        lu1.setUbicacion(dato);
                                        System.out.println("--> Escriba la intensidad de la luz ");
                                        dato = teclado.readLine();
                                        lu1.setIntensidad(Integer.parseInt(dato));
                                        System.out.println("--> Escriba el estado\nEncendido: 1, Apagado: 0 ");
                                        dato = teclado.readLine();
                                        if (Integer.parseInt(dato) == 1) {
                                            lu1.setEstado(true);
                                        } else {
                                            lu1.setEstado(false);
                                        }
                                        auxLU.remove(id);
                                        auxLU.add(id, lu1);
                                        listaO[8].setCantidad(auxLU.size());
                                        listaO[8].setObjetos(auxLU);
                                    }
                                }
                                System.out.println("Cambio Exitoso");
                                break;
                            default:
                                System.out.println("Esta opcion no es valida");
                                break;
                        }
                        break;
                    case 4://Consultas
                        System.out.println("\n------------------------------");
                        System.out.println("|  Escriba la clase de objeto  |");
                        System.out.println("--------------------------------");
                        System.out.println("| 0.- Usuario                  |");
                        System.out.println("| 1.- Refrigerador             |");
                        System.out.println("| 2.- Cortinas                 |");
                        System.out.println("| 3.- Termostato               |");
                        System.out.println("| 4.- DisMascota               |");
                        System.out.println("| 5.- Irrigador                |");
                        System.out.println("| 6.- Alarma                   |");
                        System.out.println("| 7.- Lampara                  |");
                        System.out.println("| 8.- Luminaria                |");
                        System.out.println("--------------------------------");
                        tipoC = Integer.parseInt(teclado.readLine());
                        switch (tipoC) {
                            case 0:
                                if (usrT == 1) {
                                    Usuario u1 = new Usuario();
                                    System.out.println("\n------------------------------------");
                                    System.out.println("--> Escriba el ID a buscar");
                                    id = Integer.parseInt(teclado.readLine());

                                    li = listaO[0].getObjetos().listIterator();

                                    System.out.println("\nTipoClase: " + listaO[0].getTipoClase() + "\nUsuarios[" + listaO[0].getCantidad() + "]:");
                                    while (li.hasNext()) {
                                        u1 = (Usuario) li.next();
                                        if (u1.getId() == id) {
                                            u1.imprimeOs();
                                        }
                                    }
                                    System.out.println("Busqueda Finalizada");
                                } else {
                                    System.out.println("[ERROR]: X_X\n\tNo cuentas con los permisos");
                                }
                                break;
                            case 1:
                                Refrigerador r1 = new Refrigerador();
                                System.out.println("\n------------------------------------");
                                System.out.println("--> Escriba el ID a buscar");
                                id = Integer.parseInt(teclado.readLine());

                                li = listaO[1].getObjetos().listIterator();

                                System.out.println("\nTipoClase: " + listaO[1].getTipoClase() + "\nRefrigeradors[" + listaO[1].getCantidad() + "]:");
                                while (li.hasNext()) {
                                    r1 = (Refrigerador) li.next();
                                    if (r1.getId() == id) {
                                        r1.imprimeOs();
                                    }
                                }
                                System.out.println("Busqueda Finalizada");
                                break;
                            case 2:
                                Cortinas c1 = new Cortinas();
                                System.out.println("\n------------------------------------");
                                System.out.println("--> Escriba el ID a buscar");
                                id = Integer.parseInt(teclado.readLine());

                                li = listaO[2].getObjetos().listIterator();

                                System.out.println("\nTipoClase: " + listaO[2].getTipoClase() + "\nCortinass[" + listaO[2].getCantidad() + "]:");
                                while (li.hasNext()) {
                                    c1 = (Cortinas) li.next();
                                    if (c1.getId() == id) {
                                        c1.imprimeOs();
                                    }
                                }
                                System.out.println("Busqueda Finalizada");
                                break;
                            case 3:
                                Termostato t1 = new Termostato();
                                System.out.println("\n------------------------------------");
                                System.out.println("--> Escriba el ID a buscar");
                                id = Integer.parseInt(teclado.readLine());

                                li = listaO[3].getObjetos().listIterator();

                                System.out.println("\nTipoClase: " + listaO[3].getTipoClase() + "\nTermostatos[" + listaO[3].getCantidad() + "]:");
                                while (li.hasNext()) {
                                    t1 = (Termostato) li.next();
                                    if (t1.getId() == id) {
                                        t1.imprimeOs();
                                    }
                                }
                                System.out.println("Busqueda Finalizada");
                                break;
                            case 4:
                                DisMascota d1 = new DisMascota();
                                System.out.println("\n------------------------------------");
                                System.out.println("--> Escriba el ID a buscar");
                                id = Integer.parseInt(teclado.readLine());

                                li = listaO[4].getObjetos().listIterator();

                                System.out.println("\nTipoClase: " + listaO[4].getTipoClase() + "\nCortinass[" + listaO[4].getCantidad() + "]:");
                                while (li.hasNext()) {
                                    d1 = (DisMascota) li.next();
                                    if (d1.getId() == id) {
                                        d1.imprimeOs();
                                    }
                                }
                                System.out.println("Busqueda Finalizada");
                                break;
                            case 5:
                                Irrigador i1 = new Irrigador();
                                System.out.println("\n------------------------------------");
                                System.out.println("--> Escriba el ID a buscar");
                                id = Integer.parseInt(teclado.readLine());

                                li = listaO[5].getObjetos().listIterator();

                                System.out.println("\nTipoClase: " + listaO[5].getTipoClase() + "\nCortinass[" + listaO[5].getCantidad() + "]:");
                                while (li.hasNext()) {
                                    i1 = (Irrigador) li.next();
                                    if (i1.getId() == id) {
                                        i1.imprimeOs();
                                    }
                                }
                                System.out.println("Busqueda Finalizada");
                                break;
                            case 6:
                                Alarma a1 = new Alarma();
                                System.out.println("\n------------------------------------");
                                System.out.println("--> Escriba el ID a buscar");
                                id = Integer.parseInt(teclado.readLine());

                                li = listaO[6].getObjetos().listIterator();

                                System.out.println("\nTipoClase: " + listaO[6].getTipoClase() + "\nCortinass[" + listaO[6].getCantidad() + "]:");
                                while (li.hasNext()) {
                                    a1 = (Alarma) li.next();
                                    if (a1.getId() == id) {
                                        a1.imprimeOs();
                                    }
                                }
                                System.out.println("Busqueda Finalizada");
                                break;
                            case 7:
                                Lampara la1 = new Lampara();
                                System.out.println("\n------------------------------------");
                                System.out.println("--> Escriba el ID a buscar");
                                id = Integer.parseInt(teclado.readLine());

                                li = listaO[7].getObjetos().listIterator();

                                System.out.println("\nTipoClase: " + listaO[7].getTipoClase() + "\nCortinass[" + listaO[7].getCantidad() + "]:");
                                while (li.hasNext()) {
                                    la1 = (Lampara) li.next();
                                    if (la1.getId() == id) {
                                        la1.imprimeOs();
                                    }
                                }
                                System.out.println("Busqueda Finalizada");
                                break;
                            case 8:
                                Luminaria lu1 = new Luminaria();
                                System.out.println("\n------------------------------------");
                                System.out.println("--> Escriba el ID a buscar");
                                id = Integer.parseInt(teclado.readLine());

                                li = listaO[8].getObjetos().listIterator();

                                System.out.println("\nTipoClase: " + listaO[8].getTipoClase() + "\nCortinass[" + listaO[8].getCantidad() + "]:");
                                while (li.hasNext()) {
                                    lu1 = (Luminaria) li.next();
                                    if (lu1.getId() == id) {
                                        lu1.imprimeOs();
                                    }
                                }
                                System.out.println("Busqueda Finalizada");
                                break;
                            default:
                                System.out.println("Esta opcion no es valida");
                                break;
                        }
                        break;
                    case 5://Salir
                        banderaU = false;
                        //Escriniendo en el archivo
                        fileW = new ObjectOutputStream(new FileOutputStream("CasaC.txt"));
                        ListaCasa listaC = new ListaCasa(listaO.length, listaO);
                        fileW.writeObject(listaC);
                        file.close();
                        //Enviar archivo
                        
                        salidaD = new  DataOutputStream(c.getOutputStream());
                        entradaF = new DataInputStream(new FileInputStream("CasaC.txt"));
                        lon = entradaF.read(bufferO);
                        System.out.println("Bytes enviados: " + lon);
                        salidaD.writeInt((lon));
                        salidaD.write(bufferO);
                        //entradaF.close();
                        salidaD.close();

                        break;
                    default:
                        System.out.println("Esta opcion no es valida");
                        break;
                }
            }
        }
    }
}
