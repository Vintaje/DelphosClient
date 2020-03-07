/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.StaticResources;

import Models.User;

/**
 *
 * @author vinta
 */
public class LoggedUser {
    private static User logged;

    
    public static User getLogged() {
        return logged;
    }

    public static void setLogged(User logged) {
        LoggedUser.logged = logged;
    }
    
    
}
