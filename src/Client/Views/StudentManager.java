/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.Views;

import Constants.ClientCst;
import Models.Mark;
import Models.Participante;
import Models.StaticResources.LoggedUser;
import Models.User;
import Threads.ManageTaskThread;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

/**
 *
 * @author vinta
 */
public class StudentManager extends javax.swing.JFrame {

    private ArrayList<User> teachers;

    /**
     * Creates new form StudentManager
     */
    public StudentManager() {
        initComponents();
        new ManageTaskThread(LoggedUser.getLogged().getId(), ClientCst.GET_MY_TEACHERS, this).start();
    }

    public void setTeacherList(ArrayList<User> teachers) {
        this.teachers = teachers;
    }

    public JTextField getMarkField() {
        return this.tfMark;
    }

    public JCheckBox getMarkSignature() {
        return this.cbOkSignature;
    }

    public synchronized void getTeachers() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                createTeacherList();
            }
        }, 200);
    }

    public synchronized void createTeacherList() {
        System.out.println(teachers.size());
        if (!teachers.isEmpty()) {
            DefaultListModel model = new DefaultListModel();
            for (User student : teachers) {
                model.addElement(student.getName());
            }
            Teachers.setModel(model);
            ListSelectionModel cell = Teachers.getSelectionModel();
            cell.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            cell.addListSelectionListener((ListSelectionEvent e) -> {
                new ManageTaskThread(new Mark(0, LoggedUser.getLogged().getId(), teachers.get(Teachers.getSelectedIndex()).getId(), ""), ClientCst.GET_MARKS, this).start();
            });
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        Teachers = new javax.swing.JList<>();
        tfMark = new javax.swing.JTextField();
        btClose = new javax.swing.JButton();
        cbOkSignature = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setViewportView(Teachers);

        tfMark.setEditable(false);

        btClose.setText("Close");
        btClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCloseActionPerformed(evt);
            }
        });

        cbOkSignature.setText("Signature OK");
        cbOkSignature.setEnabled(false);
        cbOkSignature.setFocusable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                            .addComponent(tfMark)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(cbOkSignature)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btClose)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tfMark, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                        .addComponent(btClose)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbOkSignature)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCloseActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btCloseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> Teachers;
    private javax.swing.JButton btClose;
    private javax.swing.JCheckBox cbOkSignature;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField tfMark;
    // End of variables declaration//GEN-END:variables
}
