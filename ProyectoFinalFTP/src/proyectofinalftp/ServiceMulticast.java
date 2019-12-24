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

    public static void serviceListen(JLabel nodoA, JLabel nodoS, JComboBox lista, int myPort, int portNext, String host, Envia evento, ServerRMI SRMI) throws IOException, InterruptedException {
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
        int wait = 0;
        boolean flagRMI = false;
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
                    //Se añaden los nodos y se ordenan
                    Nodo n1 = new Nodo(host, port);
                    nList.add(n1);
                    nList.quicksort(0, nList.size - 1);
                    //aquí comienzan a establecerse el nodo previo y siguiente
                    if (myNode != null) {
                        next = nList.getNext(myNode);
                        prev = nList.getPrev(myNode);
                        nodoA.setText("Prev: " + prev.getPort());
                        nodoS.setText("Next: " + next.getPort());
                        portNext = next.getPort();
                        lista.setModel(new DefaultComboBoxModel(nList.getIDS()));
                        evento.setHo(host);
                        evento.setPo(portNext);
                        evento.setMypo(myPort);
                        SRMI.setMyPort(myPort);
                        SRMI.setPortNext(portNext);
                        //Parte donde se espera a ejecutar el Servidor RMI
                        wait += 1;
                        if (wait > 5 && !flagRMI) {
                            ServiceMulticast.service_SerRMI(SRMI);
                            flagRMI = true;
                        }
                    }
//                    nList.printList();
//                    System.out.println("Conectado: " + port);
//                    System.out.println("host: " + SRMI.getMyPort() + " port: " + SRMI.getPortNext());
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

    public static void service_SerRMI(ServerRMI SRMI) throws InterruptedException {
        try {
//            sleep(20000);
            System.out.println("Path: " + SRMI.getPath() + " port: " + SRMI.getPortNext());
            System.setProperty("java.rmi.server.codebase", "file:/C:/temp/archivos");
            ServerRMI obj = SRMI;
            // Busca objeto de la interfaz en el registro 
            Archivos stub = (Archivos) UnicastRemoteObject.exportObject(obj, 0);
            //Creamos el registro y o ponemos a escuchar en el puerto
            Registry registro = LocateRegistry.createRegistry(obj.getMyPort());
            //Ligamos el objeto remoto en el registro
            registro.bind("Archivos", stub);
            System.out.println("Servidor RMI Listo..");
        } catch (AlreadyBoundException | RemoteException e) {
            System.out.println("Excepcion en el Servidor RMI");
            e.printStackTrace();
        }
    }

    public static void service_CliRMI(String host, int port, int myPort, String file, JTextPane logArea) {
        String ho = "127.0.0.1";
        try {
            String msg = "";
            System.out.println(port);
            Registry registro = LocateRegistry.getRegistry(ho, port);
            //Buscar el objeto de la interfaz en el registro
            Archivos stub = (Archivos) registro.lookup("Archivos");
            boolean respuesta = stub.searchFile(file);
            if (respuesta) {
                msg+=file + " -> Found on : " + port + "<br>";
                logArea.setText(msg);
//                logArea.setText();
            } else {
                msg += file + " -> Not Found on : " + port + "<br>";
                logArea.setText(msg);
                msg += stub.askFor(file, myPort);
                logArea.setText(msg);
            }
        } catch (NotBoundException | RemoteException e) {
            System.out.println("Excepcion en el service cliente RMI");
            e.printStackTrace();
        }
    }

    public static void service_StreamServer() {
        try {
            ServerSocket s = new ServerSocket(7000);

            //Iniciamos el ciclo infinito
            for (;;) {
                Socket cl = s.accept();
                
            }//For
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
