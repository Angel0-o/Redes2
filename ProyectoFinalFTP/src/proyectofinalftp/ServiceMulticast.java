/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinalftp;

import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
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
import java.util.Iterator;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextPane;

/**
 *
 * @author Angel
 */
public class ServiceMulticast {

    public static void serviceListen(JLabel nodoA, JLabel nodoS, JComboBox lista, int myPort) throws IOException, InterruptedException {
        InetSocketAddress remote = new InetSocketAddress("228.1.1.1", 2000);
        //Interfaz de red
        NetworkInterface netInterface = NetworkInterface.getByName("wlan1");//wlan1 | wlan1
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
                    //Creacion de nodos y lista
                    if (port == myPort) {
                        myNode = new Nodo(emisor.toString(), port);
                    } else {
                        myNode = null;
                    }
                    Nodo n1 = new Nodo(emisor.toString(), port);
                    nList.add(n1);
                    nList.quicksort(0, nList.size - 1);
                    if (myNode != null) {
                        next = nList.getNext(myNode);
                        prev = nList.getPrev(myNode);
                        nodoA.setText("Prev: " + prev.getPort());
                        nodoS.setText("Next: " + next.getPort());
                        lista.setModel(new DefaultComboBoxModel(nList.getIDS()));
                    }
                    nList.printList();
                    System.out.println("Conectado: " + port);
                }
            }
        }
    }

    public static void serviceWrite(int port) throws IOException, InterruptedException {
        InetSocketAddress remote = new InetSocketAddress("228.1.1.1", 2000);
        //Interfaz de red
        NetworkInterface netInterface = NetworkInterface.getByName("wlan1");//wlan1 | wlan1 |wlan3
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
                    System.out.println("Anunciando : " + port);
                    sleep(5000);
                }
            }
        }
    }

    public static void service_SerRMI(String path, int port, JTextPane logArea) {
        try {
            System.setProperty("java.rmi.server.codebase", "file:/C:/temp/archivos");
            ServerRMI obj = new ServerRMI(path, port);
            // Busca objeto de la interfaz en el registro 
            Archivos stub = (Archivos) UnicastRemoteObject.exportObject(obj, 0);
            //Creamos el registro y o ponemos a escuchar en el puerto
            Registry registro = LocateRegistry.createRegistry(9000);
            //Ligamos el objeto remoto en el registro
            registro.bind("Archivos", stub);
            System.out.println("Servidor RMI Listo..");
        } catch (AlreadyBoundException | RemoteException e) {
            System.out.println("Excepcion en el Servidor RMI");
        }
    }
    
    public static void service_CliRMI(String host, int port, String file){
        try {
            Registry registro = LocateRegistry.getRegistry(host, port);
            //Buscar el objeto de la interfaz en el registro
            Archivos stub = (Archivos) registro.lookup("Archivos");
            boolean respuesta = stub.searchFile(file);
            System.out.println("El archivo " + file + " esta " + respuesta);
        } catch (NotBoundException | RemoteException e) {
            System.out.println("Excepcion en el cliente RMI");
        }
    }
}
