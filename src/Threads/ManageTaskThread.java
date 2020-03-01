/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import Client.Administrator.AdminControl;
import Connections.StaticConnection;
import Constants.ClientCst;
import Models.User;
import Util.Util;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author vinta
 */
public class ManageTaskThread implements Runnable {

    private Thread thread;
    private Object objToSend;
    private short task;
    private JFrame window;
    private User user;

    //If we need to dispose a Form, just set it as parameter and set the task 
    public ManageTaskThread(Object objToSend, short task, JFrame window) {
        this.objToSend = objToSend;
        this.task = task;
        this.window = window;
        this.thread = new Thread(this);
    }

    public ManageTaskThread(Object objToSend, short task, JFrame window, User user) {
        this.objToSend = objToSend;
        this.task = task;
        this.window = window;
        this.user = user;
        this.thread = new Thread(this);
        System.out.println(this.user);
    }

    //If we need to insert something just set the object and the task
    public ManageTaskThread(Object objToSend, short task) {
        this.thread = new Thread(this);
        this.objToSend = objToSend;
        this.task = task;

    }

    @Override
    public void run() {

        switch (this.task) {
            case ClientCst.LOGIN:
                login();
                break;
            case ClientCst.GET_USERS:
                getUsers();
                break;
            case ClientCst.GET_ROLES:
                getRoles();
                break;
            default:
                defaultOption();
        }
    }

    public void login() {
        String msg = "";
        byte code = (byte) StaticConnection.get(this.task, this.objToSend);
        switch (code) {
            case -1:
                msg = "I think you're not registered on Delphos. So... Fishy....";
                break;
            case 0:
                msg = "Loggin unavaliable, your user isnt activated by an administrator";
                break;
            case 1:
                msg = "Welcome to Delphos Student";
                break;
            case 2:
                msg = "Welcome to Delphos Teacher";
                break;
            case 3:
                msg = "You are the Boss, welcome Admin";
                new AdminControl().setVisible(true);
                window.dispose();
                break;
            case 4:
                msg = "Welcome Delphos Teacher and The Boss";
                new AdminControl().setVisible(true);
                window.dispose();
                break;
        }
        Util.loginUser(msg);
    }

    public void defaultOption() {
        boolean response = StaticConnection.send(this.task, this.objToSend);

        if (response) {
            Util.okDialog();
        } else {
            Util.errorDialog();
        }
    }

    public void start() {
        this.thread.start();
    }

    private void getRoles() {
        ArrayList<String> roles = (ArrayList<String>) StaticConnection.get(this.task, null);
        System.out.println(roles.size());

        String s = (String) JOptionPane.showInputDialog(
                window,
                "Set the new role", "New Role",
                JOptionPane.QUESTION_MESSAGE, null,
                roles.toArray(),
                roles.get(0));

        for (int i = 0; i < roles.size(); i++) {
            if (s.equals(roles.get(i))) {
                user.setRol((byte) i);
            }
        }
        try {
            StaticConnection.sendObject(ClientCst.ACTIVATE_USER);
            StaticConnection.sendObject(user);
            DefaultTableModel model = ((AdminControl)window).getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                if((int)model.getValueAt(i, 0) == user.getId()){
                    model.setValueAt(user.getRol(), i, 5);
                }
            }
            model.fireTableDataChanged();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void getUsers() {
        ArrayList<User> userList = (ArrayList<User>) StaticConnection.get(this.task, null);

        ((AdminControl) window).buildTable(userList);
    }

}
