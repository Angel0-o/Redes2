/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg09nobloqueante;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 *
 * @author Alumno
 */
public class SEcoTCPNB {
    
    public static void main(String[] args) throws IOException
    {
        String EECO = "";
        int pto = 9999;
        ServerSocketChannel s = ServerSocketChannel.open();
        s.configureBlocking(false);
        s.socket().bind(new InetSocketAddress(pto));
        System.out.println("Esperando clientes...");
        Selector sel = Selector.open();
        s.register(sel, SelectionKey.OP_ACCEPT);
        
        while(true)
        {
            sel.select();
            Iterator<SelectionKey> it = sel.selectedKeys().iterator();
            while(it.hasNext())
            {
                SelectionKey k = it.next();//Posible casteo
                it.remove();
                if(k.isAcceptable())
                {
                    SocketChannel cl = s.accept();
                    System.out.println("Cliente conectado desde " + cl.socket().getInetAddress() + " : " + cl.socket().getPort());
                    cl.configureBlocking(false);
                    cl.register(sel, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                    continue;
                }
                if(k.isReadable())
                {
                    SocketChannel ch = (SocketChannel) k.channel();
                    ByteBuffer b = ByteBuffer.allocate(2000);
                    b.clear();
                    int n = 0;
                    String msg = "";
                    n = ch.read(b);
                    b.flip();
                    if(n>0)
                        msg = new String(b.array(),0,n);
                    System.out.println("Mensaje de " + n + " bytes recibidos: " + msg);
                    if(msg.equalsIgnoreCase("SALIR"))
                    {
                        k.interestOps(SelectionKey.OP_WRITE);
                        ch.close();
                    }
                    else
                    {
                        EECO = "ECO-> " + msg;
                        k.interestOps(SelectionKey.OP_WRITE);
                    }
                    continue;
                }
                else if(k.isWritable())
                {
                    SocketChannel ch = (SocketChannel) k.channel();
                    ByteBuffer bb = ByteBuffer.wrap(EECO.getBytes());
                    ch.write(bb);
                    System.out.println("Mensaje de " + EECO.length() + " bytes enviados: " + EECO);
                    EECO = "";
                    k.interestOps(SelectionKey.OP_READ);
                    continue;
                }
            }
        }
    }
    
}
