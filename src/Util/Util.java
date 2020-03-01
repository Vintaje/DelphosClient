/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author vinta
 */
public class Util {

    public static void showMessage(JFrame parent, String message, String title, int icon) {
        JOptionPane.showMessageDialog(parent, message, title, icon);
    }

    public static void fieldsError(JFrame parent) {
        JOptionPane.showMessageDialog(parent, "You must fill every field", "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    public static void okDialog() {
        JOptionPane.showMessageDialog(null, "Everything its Ok", "Ok", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void errorDialog() {
        JOptionPane.showMessageDialog(null, "Something going bad", "Error", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void loginUser(String msg) {
        
        JOptionPane.showMessageDialog(null, msg, "All right", JOptionPane.INFORMATION_MESSAGE);
    }
}
