package gui;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;


public class Dashboard extends javax.swing.JPanel {

   
    public Dashboard() {
        initComponents();

        SwingUtilities.invokeLater(()->{
        ImageIcon icon = new ImageIcon("/resource/10173282_8496.jpg");
        Image img = icon.getImage();

        Image scaledImg = img.getScaledInstance(jLabel1.getWidth(), jLabel1.getHeight(), Image.SCALE_SMOOTH);

        jLabel1.setIcon(new ImageIcon(scaledImg));
        });

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/10173282_8496.jpg"))); // NOI18N
        add(jLabel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
