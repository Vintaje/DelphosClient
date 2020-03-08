/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import Admin.Views.AdminControl;
import Client.Views.StudentManager;
import Client.Views.TeacherManager;
import Connections.StaticConnection;
import Constants.ClientCst;
import Models.Grade;
import Models.Mark;
import Models.Participante;
import Models.StaticResources.LoggedUser;
import Models.StaticResources.Security;
import Models.User;
import Util.Util;

import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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

    }

    //If we need to insert something just set the object and the task
    public ManageTaskThread(Object objToSend, short task) {
        this.thread = new Thread(this);
        this.objToSend = objToSend;
        this.task = task;

    }

    @Override
    public void run() {
        try {
            StaticConnection.sendObject(this.task);
            System.out.println("Tarea enviada: " + this.task);
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
                case ClientCst.GET_GRADES:
                    getGrades();
                    break;
                case ClientCst.GET_MY_GRADES:
                    getMyGrades();
                    break;
                case ClientCst.GET_MY_STUDENTS:
                    getMyStudents();
                    break;
                case ClientCst.GET_MY_TEACHERS:
                    getMyTearchers();
                    break;
                case ClientCst.GET_MARKS:
                    getMarks();
                    break;
                case ClientCst.ACTIVATE_USER:
                    activateUser();
                    break;
                default:
                    defaultOption();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void login() {
        try {
            String msg = "";

            StaticConnection.sendObject(this.objToSend);

            User res = (User) StaticConnection.receiveItem();
            System.out.println(res);
            LoggedUser.setLogged(res);
            
            switch (res.getRol()) {
                case -1:
                    msg = "I think you're not registered on Delphos. So... Fishy....";
                    LoggedUser.setLogged(null);
                    break;
                case 0:
                    msg = "Loggin unavaliable, your user isnt activated by an administrator";
                    LoggedUser.setLogged(null);
                    break;
                case 1:
                    msg = "Welcome to Delphos Student";
                    new StudentManager().setVisible(true);
                    window.dispose();
                    break;
                case 2:
                    msg = "Welcome to Delphos Teacher";
                    new TeacherManager().setVisible(true);
                    window.dispose();
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void defaultOption() {
        try {

            if (LoggedUser.getLogged() != null) {
                Object obj = Security.cifrarConClaveSimetrica(objToSend, LoggedUser.getLogged().getSecretKey());
                StaticConnection.sendObject(obj);
            } else {
                StaticConnection.sendObject(objToSend);
            }
            boolean response = false;
            Object obj = StaticConnection.receiveItem();
            if (LoggedUser.getLogged() != null) {
                response = (boolean) Security.descifrar(LoggedUser.getLogged().getSecretKey(), obj);
            }else{
                response = (boolean) obj;
            }
            if (response) {
                Util.okDialog();
            } else {
                Util.errorDialog();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void start() {
        this.thread.start();
    }

    private void getRoles() {

    }

    private void getGrades() {
        try {
            Object obj = StaticConnection.receiveItem();
            ArrayList<Grade> gradeList = (ArrayList<Grade>) Security.descifrar(LoggedUser.getLogged().getSecretKey(), obj);
            ((AdminControl) window).buildGradeList(gradeList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void getUsers() {
        try {
            ArrayList<User> userList = (ArrayList<User>) Security.descifrar(LoggedUser.getLogged().getSecretKey(), StaticConnection.receiveItem());

            ((AdminControl) window).createTables(userList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void getMyGrades() {
        try {
            StaticConnection.sendObject(LoggedUser.getLogged().getId());
            Object obj = StaticConnection.receiveItem();
            ArrayList<Grade> grades = (ArrayList<Grade>) obj;
            ((TeacherManager) window).setGradeList(grades);
            ((TeacherManager) window).getGrades();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void getMyStudents() {
        try {
            Object obj = Security.cifrarConClaveSimetrica(objToSend, LoggedUser.getLogged().getSecretKey());
            StaticConnection.sendObject(obj);
            ArrayList<Participante> students = (ArrayList<Participante>) StaticConnection.receive.readObject();
            ((TeacherManager) window).setStudentList(students);
            ((TeacherManager) window).getStudents();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void getMyTearchers() {
        try {

            Object obj = Security.cifrarConClaveSimetrica(objToSend, LoggedUser.getLogged().getSecretKey());
            StaticConnection.sendObject(obj);
            ArrayList<User> students = (ArrayList<User>) StaticConnection.receive.readObject();
            ((StudentManager) window).setTeacherList(students);
            ((StudentManager) window).getTeachers();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void getMarks() {
        try {
            Object obj = Security.cifrarConClaveSimetrica(objToSend, LoggedUser.getLogged().getSecretKey());
            StaticConnection.sendObject(obj);
            Mark mark = (Mark) StaticConnection.receiveItem();
            ((StudentManager) window).getMarkField().setText("Mark: " + mark.getMark());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void activateUser() {
        try {
            Object obj = Security.cifrarConClaveSimetrica(objToSend, LoggedUser.getLogged().getSecretKey());
            StaticConnection.sendObject(obj);

            obj = StaticConnection.receiveItem();
            System.out.println(obj);
            Object resb = Security.descifrar(LoggedUser.getLogged().getSecretKey(), obj);
            boolean res = (boolean) resb;
            if (res) {
                Util.okDialog();
            } else {
                Util.errorDialog();
            }
            new ManageTaskThread(null, ClientCst.GET_USERS, this.window).start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
