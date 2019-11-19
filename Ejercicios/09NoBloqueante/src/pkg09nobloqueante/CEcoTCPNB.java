/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg09nobloqueante;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 *
 * @author Alumno
 */
public class CEcoTCPNB {
    
    public static void main(String[] args) throws IOException 
    {
        String host = "127.0.0.1";
        int pto = 9999;
        ByteBuffer bufer1 = null,bufer2 = null;
        InetSocketAddress dst = new InetSocketAddress(host, pto);
        SocketChannel canal = SocketChannel.open();
        BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
        canal.configureBlocking(false);
        Selector sel = Selector.open();
        canal.register(sel, SelectionKey.OP_CONNECT);
        canal.connect(dst);
        
        while(true)
        {
            sel.select();
            Iterator<SelectionKey> it = sel.selectedKeys().iterator();
            
            while(it.hasNext())
            {
                SelectionKey k = it.next();
                it.remove();
                
                if(k.isConnectable())
                {
                    SocketChannel ch = (SocketChannel) k.channel();
                    if(ch.isConnectionPending())
                    {
                        System.out.println("Estableciendo conexion, espere..");
                        ch.finishConnect();
                        System.out.println("Conexion establecida");
                        System.out.println("Escriba texto + <ENTER> para enviar\nsalir para terminar");
                    }
                    
                    ch.register(sel, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                    continue;
                }
                if(k.isReadable())
                {
                    SocketChannel ch = (SocketChannel) k.channel();
                    bufer1 = ByteBuffer.allocate(2000);
                    bufer1.clear();
                    int n = ch.read(bufer1);
                    bufer1.flip();
                    String eco = new String(bufer1.array(),0,n);
                    System.out.println("Eco de " + n + "bytes " + eco);
                    k.interestOps(SelectionKey.OP_WRITE);
                    continue;
                }else if(k.isWritable())
                {
                    SocketChannel ch = (SocketChannel) k.channel();
                    String datos = "";
                    datos = br.readLine();
                    
                    if(datos.equalsIgnoreCase("salir"))
                    {
                        System.out.println("Termina aplicacion");
                        byte[] mm = "salir".getBytes();
                        bufer2 = ByteBuffer.wrap(mm);
                        ch.write(bufer2);
                        k.interestOps(SelectionKey.OP_READ);
                        k.cancel();
                        ch.close();
                        System.exit(0);
                    }
                    byte[] mm = datos.getBytes();
                    System.out.println("Enviando eco de " + mm.length + "bytes");
                    bufer2 = ByteBuffer.wrap(mm);
                    ch.write(bufer2);
                    k.interestOps(SelectionKey.OP_READ);
                    continue;
                }
            }
        }
    }
}
