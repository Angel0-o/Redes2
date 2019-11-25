/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinalftp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.StandardProtocolFamily;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.Iterator;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import proyectofinalftp.Ventana.Envia;

/**
 *
 * @author Angel
 */
public class ServiceMulticast {

    public static void serviceListen(JLabel nodoA, JLabel nodoS, JComboBox lista, int myPort, int portNext, String host, Envia evento) throws IOException, InterruptedException {
        InetSocketAddress remote = new InetSocketAddress("228.1.1.1", 2000);
        //Interfaz de red
        NetworkInterface netInterface = NetworkInterface.getByName("wlan3");//wlan3 | wlan3
        //Creacion y configuracion del canal
        DatagramChannel channel = DatagramChannel.open(StandardProtocolFamily.INET);
        channel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
        channel.setOption(StandardSocketOptions.IP_MULTICAST_IF, netInterface);
        //Configuramos el canal para que sea no bloqueante
        channel.configureBlocking(false);
        //Abrimos el selector y lo configuramos para lectura y escritura
        Selector sel = Selector.open();
        channel.register(sel, SelectionKey.OP_READ);
        //Nos unismos al grupo
        InetAddress group = InetAddress.getByName("228.1.1.1");
        channel.join(group, netInterface);
        //Ligamos el canal para que escuche en un puerto determinado
        channel.socket().bind(new InetSocketAddress(2000));
        ByteBuffer b;
        int port;
        Nodo next, prev, myNode;
        ListaNodos nList = new ListaNodos();
        String[] IDs = null;
        while (true) {
            sel.select();
            Iterator<SelectionKey> it = sel.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey k = it.next();
                it.remove();
                if (k.isReadable()) {
                    DatagramChannel ch = (DatagramChannel) k.channel();
                    // Recepcion del puerto
                    b = ByteBuffer.allocate(4);
                    b.clear();
                    SocketAddress emisor = ch.receive(b);
                    b.flip();
                    port = b.getInt();
                    host = Arrays.asList(emisor.toString().split(":")).get(0);
                    host = host.substring(1, host.length() - 1);
                    //Creacion de nodos y lista
                    if (port == myPort) {
                        myNode = new Nodo(host, port);
                    } else {
                        myNode = null;
                    }
                    Nodo n1 = new Nodo(host, port);
                    nList.add(n1);
                    nList.quicksort(0, nList.size - 1);
                    if (myNode != null) {
                        next = nList.getNext(myNode);
                        prev = nList.getPrev(myNode);
                        nodoA.setText("Prev: " + prev.getPort());
                        nodoS.setText("Next: " + next.getPort());
                        portNext = next.getPort();
                        lista.setModel(new DefaultComboBoxModel(nList.getIDS()));
                        evento.setHo(host);
                        evento.setPo(portNext);
                    }
//                    nList.printList();
//                    System.out.println("Conectado: " + port);
                    System.out.println("host: " + host + " port: " + portNext);
                }
            }
        }
    }

    public static void serviceWrite(int port) throws IOException, InterruptedException {
        InetSocketAddress remote = new InetSocketAddress("228.1.1.1", 2000);
        //Interfaz de red
        NetworkInterface netInterface = NetworkInterface.getByName("wlan3");//wlan3 | wlan3 |wlan3
        //Creacion y configuracion del canal
        DatagramChannel channel = DatagramChannel.open(StandardProtocolFamily.INET);
        channel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
        channel.setOption(StandardSocketOptions.IP_MULTICAST_IF, netInterface);
        //Configuramos el canal para que sea no bloqueante
        channel.configureBlocking(false);
        //Abrimos el selector y lo configuramos para lectura y escritura
        Selector sel = Selector.open();
        channel.register(sel, SelectionKey.OP_WRITE);
        //Nos unismos al grupo
        InetAddress group = InetAddress.getByName("228.1.1.1");
        channel.join(group, netInterface);
        ByteBuffer b;

        while (true) {
            sel.select();
            Iterator<SelectionKey> it = sel.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey k = it.next();
                it.remove();
                if (k.isWritable()) {
                    DatagramChannel ch = (DatagramChannel) k.channel();
                    // Envio del puerto
                    b = ByteBuffer.allocate(4);
                    b.clear();
                    b.putInt(port);
                    b.flip();
                    ch.send(b, remote);
//                    System.out.println("Anunciando : " + port);
                    sleep(5000);
                }
            }
        }
    }

    public static void service_SerRMI(String path, int port) {
        try {
            System.out.println("Path: " + path + " port: " + port);
            System.setProperty("java.rmi.server.codebase", "file:/C:/temp/archivos");
            ServerRMI obj = new ServerRMI(path, port);
            // Busca objeto de la interfaz en el registro 
            Archivos stub = (Archivos) UnicastRemoteObject.exportObject(obj, 0);
            //Creamos el registro y o ponemos a escuchar en el puerto
//            System.setProperty("java.rmi.server.hostname","10.100.70.48");
            Registry registro = LocateRegistry.createRegistry(port);
            //Ligamos el objeto remoto en el registro
            registro.bind("Archivos", stub);
            System.out.println("Servidor RMI Listo..");
        } catch (AlreadyBoundException | RemoteException e) {
            System.out.println("Excepcion en el Servidor RMI");
        }
    }

    public static void service_CliRMI(String host, int port, String file, JTextPane logArea) {
        try {
            System.out.println("host: " + host + " port: " + port);
            Registry registro = LocateRegistry.getRegistry("127.0.0.1",port);
            System.out.println("1");
            //Buscar el objeto de la interfaz en el registro
            Archivos stub = (Archivos) registro.lookup("Archivos");
            System.out.println("2");
            boolean respuesta = stub.searchFile(file);
            System.out.println("3");
            if (respuesta) {
                logArea.setText("El archivo " + file + " se encuentra en  el nodo " + port);
            } else {
                logArea.setText("El archivo " + file + " no se encuentra en  el nodo " + port);
            }
        } catch (NotBoundException | RemoteException e) {
            System.out.println("Excepcion en el cliente RMI");
            e.printStackTrace();
        }
    }
    
    public static void service_StreamServer(){
        try {
            ServerSocket s = new ServerSocket(7000);

            //Iniciamos el ciclo infinito
            for (;;) {
                Socket cl = s.accept();
                System.out.println("Conexion establecida desde " + cl.getInetAddress() + "con Puerto " + cl.getPort());
                DataInputStream entrada = new DataInputStream(cl.getInputStream());
                byte[] b = new byte[1024];
                String nombre = entrada.readUTF();
                System.out.println("Recibimos el archivo " + nombre);
                long tamano = entrada.readLong();
                DataOutputStream salida = new DataOutputStream(new FileOutputStream(nombre));
                long recibidos = 0;
                int n, porcentaje = 0;

                while (recibidos < tamano) {
                    n = entrada.read(b);
                    salida.write(b, 0, n);
                    //System.out.println("Pase el write");
                    salida.flush();
                    recibidos = n + recibidos;
                    porcentaje = (int) (recibidos * 100 / tamano);
                    System.out.println("Porcentaje: " + porcentaje + "%\r");
                }//While
                System.out.println("Archivo recibido");
                salida.close();
                entrada.close();
                cl.close();
            }//For
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
