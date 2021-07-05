/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siguasystem.facturacionjava;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.siguasystem.modelo.CorrelativossarJpaController;
import com.siguasystem.modelo.*;
import com.siguasystem.modelos.exceptions.NonexistentEntityException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jramos
 */
public class frmCorrelativos extends javax.swing.JInternalFrame {

    /**
     * Creates new form frmCorrelativos
     */
    ///Conexion Local
    public EntityManagerFactory em = Persistence.createEntityManagerFactory("Restorant");
    ///Proveedor
    public CorrelativossarJpaController jpcorrela = new CorrelativossarJpaController(em);
    List<Correlativossar> lcor = new ArrayList<Correlativossar>();
    TablabuscaCorrela bp = new TablabuscaCorrela();
    Correlativossar cor;//new Correlativossar();
    
    public frmCorrelativos() {
        initComponents();
        tbcorrela.setModel(bp.crearTabla());
        buscarCorrela10();
        btnCRUD1.btnsave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setDatos();
                if (txtid.getValue().equals("0")) {
                    jpcorrela.create(cor);
                }else{
                    try {
                        jpcorrela.edit(cor);
                    } catch (Exception ex) {
                        Logger.getLogger(frmCorrelativos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
               buscarCorrela10();
             
            }
        });
        btnCRUD1.btnupd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            setDatos();
               try {
                   jpcorrela.edit(cor);
               } catch (Exception ex) {
                   Logger.getLogger(frmCorrelativos.class.getName()).log(Level.SEVERE, null, ex);
               }
               buscarCorrela10();
            }
        });
        
        btnCRUD1.btndesact.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Correlativossar cor=jpcorrela.findCorrelativossar(Integer.parseInt(txtid.getValue().toString()));
                    cor.setEstado(0);
                    jpcorrela.edit(cor);
                    buscarCorrela10();
                } catch (Exception ex) {
                    Logger.getLogger(frmCorrelativos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void setDatos() throws NumberFormatException {
        cor.setCai(txtcai.getText());
        cor.setFormatonumero(txtformat.getText());
        cor.setFechacreacion(new Date());
        cor.setFechafin(ffin.getDate());
        cor.setFechaini(fini.getDate());
        cor.setRangofin(Integer.parseInt(rfinal.getValue().toString()));
        cor.setRangoini(Integer.parseInt(rinicial.getValue().toString()));
        if (chkactivo.isSelected()) {
            cor.setEstado(1);
        }else{
            cor.setEstado(0);
        }
    }

    public void buscarCorrela10() throws HeadlessException {
        // TODO add your handling code here:
        try {
            DefaultTableModel df = bp.crearTabla();
            ///if (!txtid.getText().isEmpty()) {
            lcor = jpcorrela.findCorrelativossarEntities();
            int i = 0;
            for (Iterator<Correlativossar> iterator = lcor.iterator(); iterator.hasNext();) {
                Correlativossar p = iterator.next();
                df.addRow(new Object[]{p.getId(), p.getCai(), p.getFormatonumero(),p.getFechafin().toLocaleString(),p.getRangofin()});
            }
            tbcorrela.setModel(df);
            ///}
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtcai = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbcorrela = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtformat = new javax.swing.JTextField();
        rfinal = new javax.swing.JSpinner();
        rinicial = new javax.swing.JSpinner();
        txtid = new javax.swing.JSpinner();
        btnCRUD1 = new com.siguasystem.facturacionjava.btnCRUD();
        fini = new com.toedter.calendar.JDateChooser();
        ffin = new com.toedter.calendar.JDateChooser();
        chkactivo = new javax.swing.JCheckBox();
        btnnewcor = new javax.swing.JButton();
        btndel = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);

        jLabel1.setText("ID:");

        jLabel2.setText("CAI:");

        tbcorrela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbcorrela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbcorrelaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbcorrela);

        jLabel3.setText("Fecha Inicio:");

        jLabel4.setText("Fecha Finalizacion:");

        jLabel5.setText("Rango Inicial:");

        jLabel6.setText("Rango Final:");

        jLabel7.setText("Formato de Numeracion:");

        rfinal.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        rinicial.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        chkactivo.setText("Activo/Inactivo");
        chkactivo.setToolTipText("Check marcado esta Activo");

        btnnewcor.setText("Agregar Nuevo");
        btnnewcor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnewcorActionPerformed(evt);
            }
        });

        btndel.setText("Eliminar Correlativo");
        btndel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnCRUD1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtformat, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                                    .addComponent(fini, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ffin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(37, 37, 37)
                                    .addComponent(txtcai, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(rfinal, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                                    .addComponent(rinicial))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chkactivo)
                        .addGap(46, 46, 46))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btndel, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnnewcor, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 366, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkactivo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtcai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(txtformat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(fini, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(ffin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rinicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(rfinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addComponent(btnCRUD1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btndel, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnnewcor, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbcorrelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbcorrelaMouseClicked
        // TODO add your handling code here:
          if (evt.getClickCount() == 2 && !evt.isConsumed()) {
           int id =Integer.parseInt(tbcorrela.getModel().getValueAt(tbcorrela.getSelectedRow(), 0).toString());
          cor=jpcorrela.findCorrelativossar(id);
          txtid.setValue(cor.getId());txtcai.setText(cor.getCai());
          txtformat.setText(cor.getFormatonumero());
          fini.setDate(cor.getFechaini());ffin.setDate(cor.getFechafin());
          rinicial.setValue(cor.getRangoini());rfinal.setValue(cor.getRangofin());
          if (cor.getEstado()==1)
          {chkactivo.setSelected(true);}
          else{chkactivo.setSelected(false);}
          }
    }//GEN-LAST:event_tbcorrelaMouseClicked

    private void btnnewcorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnewcorActionPerformed
        // TODO add your handling code here:
         cor=new Correlativossar();
         txtid.setValue(0);txtcai.setText("");
         txtformat.setText("");
         fini.setDate(new Date());
         ffin.setDate(new Date());
         rinicial.setValue(rfinal.getValue());
         chkactivo.setSelected(false);
    }//GEN-LAST:event_btnnewcorActionPerformed

    private void btndelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndelActionPerformed
        // TODO add your handling code here:
        if (Integer.parseInt(txtid.getValue().toString())>0) {
            try {
                jpcorrela.destroy(Integer.parseInt(txtid.getValue().toString()));
                JOptionPane.showMessageDialog(null, "Correlativo Eliminado!", "Eliminado", JOptionPane.INFORMATION_MESSAGE);
                buscarCorrela10();
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(frmCorrelativos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btndelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.siguasystem.facturacionjava.btnCRUD btnCRUD1;
    private javax.swing.JButton btndel;
    private javax.swing.JButton btnnewcor;
    private javax.swing.JCheckBox chkactivo;
    private com.toedter.calendar.JDateChooser ffin;
    private com.toedter.calendar.JDateChooser fini;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner rfinal;
    private javax.swing.JSpinner rinicial;
    private javax.swing.JTable tbcorrela;
    private javax.swing.JTextField txtcai;
    private javax.swing.JTextField txtformat;
    private javax.swing.JSpinner txtid;
    // End of variables declaration//GEN-END:variables
}
