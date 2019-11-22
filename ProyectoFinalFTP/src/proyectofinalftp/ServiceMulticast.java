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
import java.util.Iterator;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;

/**
 *
 * @author Angel
 */
public class ServiceMulticast {

    public static void serviceListen(JLabel nodoA, JLabel nodoS, JComboBox lista) throws IOException, InterruptedException {
        InetSocketAddress remote = new InetSocketAddress("228.1.1.1", 2000);
        //Interfaz de red
        NetworkInterface netInterface = NetworkInterface.getByName("eth2");//eth2 | wlan1
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
        int next, prev;
        ListaNodos nList = new ListaNodos();
        ArrayNodos listPort = new ArrayNodos();
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
                    Nodo n1 = new Nodo(emisor.toString(), port);
                    nList.agrega(n1);
//                    nList.insertOrder(n1);
//                    nList.imprime(nList);
                    listPort.agrega(port);
                    listPort.ordena();
                    next = listPort.getNext(port);
                    prev = listPort.getPrev(port);
                    System.out.println("Prev: " + prev);
                    System.out.println("Next: " + next);
                    nodoA.setText("Prev: " + prev);
                    nodoS.setText("Next: " + next);
                    IDs = nList.getIDs(IDs);
                    lista.setModel(new DefaultComboBoxModel(IDs));
                    nList.imprime(nList);
                    System.out.println("Conectado: " + port);
                    continue;
                }
            }
        }
    }

    public static void serviceWrite(int port) throws IOException, InterruptedException {
        InetSocketAddress remote = new InetSocketAddress("228.1.1.1", 2000);
        //Interfaz de red
        NetworkInterface netInterface = NetworkInterface.getByName("eth2");//eth2 | wlan1 |wlan3
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
                    continue;
                }
            }
        }
    }

    public static void serviceChargeNodes(int port) {
        Nodo node = new Nodo();
    }
}
