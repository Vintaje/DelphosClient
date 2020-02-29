/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connections;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author vinta
 */
public class StaticConnection {

    private final static String ADDRESS = "localhosst";
    private final static int PORT = 5000;
    public static Socket server;
    public static ObjectOutputStream send;
    public static ObjectInputStream receive;

    static {
        try {
            server = new Socket(InetAddress.getLocalHost(), PORT);
            send = new ObjectOutputStream(server.getOutputStream());
            receive = new ObjectInputStream(server.getInputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean send(short task, Object object) {
        try {
            send.writeShort(task);
            send.writeObject(object);
            return (boolean) receive.readObject();
        } catch (Exception ex) {
        }

        return false;
    }

    public static boolean loginUser() {

        return false;
    }

}
