/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connections;

import Models.StaticResources.LoggedUser;
import Models.StaticResources.Security;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.security.InvalidKeyException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *
 * @author vinta
 */
public class StaticConnection {

    private final static int PORT = 5000;
    public static Socket server;
    public static ObjectOutputStream send;
    public static ObjectInputStream receive;
    public static SecretKey secretKey;

    public static void initialize() {
        try {
            server = new Socket(InetAddress.getLocalHost(), PORT);
            send = new ObjectOutputStream(server.getOutputStream());
            receive = new ObjectInputStream(server.getInputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

   


    public static synchronized Object receiveItem() throws IOException, ClassNotFoundException {
        Object obj = receive.readObject();
       
        return obj;
    }
    
    public static synchronized void sendObject(Object object) throws IOException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException{
       
        send.writeObject(object);
        
        
    }

    public static boolean loginUser() {

        return false;
    }

}
