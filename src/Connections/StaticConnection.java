/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connections;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author vinta
 */
public class StaticConnection {

    private final static int PORT = 5000;
    public static Socket server;
    public static ObjectOutputStream send;
    public static ObjectInputStream receive;

    public static void initialize() {
        try {
            server = new Socket(InetAddress.getLocalHost(), PORT);
            send = new ObjectOutputStream(server.getOutputStream());
            receive = new ObjectInputStream(server.getInputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public synchronized static boolean send(Object object) {
        try {
            
            sendObject(object);
            return (boolean) receiveItem();
        } catch (Exception ex) {
        }

        return false;
    }

    public synchronized static Object get(short task, Object object) {
        try {
            sendObject(task);
            if (object != null) {
                sendObject(object);
            }
            return receiveItem();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static synchronized Object receiveItem() throws IOException, ClassNotFoundException {
        return receive.readObject();
    }
    
    public static synchronized void sendObject(Object object) throws IOException{
        send.writeObject(object);
        send.flush();
    }

    public static boolean loginUser() {

        return false;
    }

}
