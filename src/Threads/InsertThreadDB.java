/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import Connections.StaticConnection;
import Util.Util;
import javax.swing.JFrame;

/**
 *
 * @author vinta
 */
public class InsertThreadDB implements Runnable{
    private Thread thread;
    private Object objToSend;
    private short task;
    
    public InsertThreadDB(Object objToSend, short task){
        this.thread = new Thread(this);
        this.objToSend = objToSend;
        this.task = task;

    }
    
    public void run(){
        boolean response = StaticConnection.send(this.task, this.objToSend);
        System.out.println(response);
        if(response){
            Util.okDialog();
        }else{
            Util.errorDialog();
        }
    }
    
    public void start(){
        this.thread.start();
    }
    
    
}
