/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delphosclient;


import Client.Views.Login;
import Connections.StaticConnection;

/**
 *
 * @author vinta
 */
public class DelphosClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        StaticConnection.initialize();
        Login login = new Login();
        login.setVisible(true);
    }
    
}
