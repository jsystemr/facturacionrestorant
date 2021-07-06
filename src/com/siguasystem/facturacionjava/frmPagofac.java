/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siguasystem.facturacionjava;

import com.siguasystem.modelo.Detallefactura;
import com.siguasystem.modelo.DetallefacturaJpaController;
import com.siguasystem.modelo.Estadofact;
import com.siguasystem.modelo.Factura;
import com.siguasystem.modelo.FacturaJpaController;
import com.siguasystem.modelo.FacturaPK;
import java.awt.Color;
import java.math.BigDecimal;
import static java.math.MathContext.DECIMAL32;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

/**
 *
 * @author jramos
 */
public class frmPagofac extends javax.swing.JInternalFrame {

    /**
     * Creates new form frmPagofac
     */
    public boolean pago = false;
    public Integer id=0;
    public int nfac;
    public Date fecha;
    public BigDecimal efectivo;
    EntityManagerFactory emFac = Persistence.createEntityManagerFactory("Restorant");
    public Factura f;
    public List<Detallefactura> ldf;//=new ArrayList<detallefactura>();
    public FacturaJpaController jpfac;
    public DetallefacturaJpaController jpdfac;
    public FacturaPK fpk;
    EjecutarReporte report;

    public frmPagofac() {
        initComponents();
        jpfac = new FacturaJpaController(emFac);
        jpdfac = new DetallefacturaJpaController(emFac);
        report = new EjecutarReporte();
    }

    public frmPagofac(FacturaPK pf, BigDecimal tot) {
        initComponents();
        fpk = pf;
        txtfac.setText("" + pf.getIdFactura());
        txttotal.setValue(tot.round(DECIMAL32).doubleValue());
        txttotal.setText("" + tot.round(DECIMAL32).doubleValue());
        //txtefectivo.requestFocus();
        btnpay.setEnabled(false);
        jpdfac = new DetallefacturaJpaController(emFac);
        jpfac = new FacturaJpaController(emFac);
        report = new EjecutarReporte();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtcambio = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtfac = new javax.swing.JTextField();
        btnpay = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtefectivo = new javax.swing.JFormattedTextField();
        txttotal = new javax.swing.JFormattedTextField();
        Cerrar = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Total a Pagar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Narrow", 1, 36))); // NOI18N
        jPanel1.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial Narrow", 1, 36)); // NOI18N
        jLabel1.setText("Cambio:");

        txtcambio.setEditable(false);
        txtcambio.setFont(new java.awt.Font("Arial Narrow", 1, 36)); // NOI18N
        txtcambio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtcambio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcambioActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial Narrow", 1, 36)); // NOI18N
        jLabel2.setText("Efectivo:");

        txtfac.setEditable(false);
        txtfac.setFont(new java.awt.Font("Arial Narrow", 1, 36)); // NOI18N
        txtfac.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtfac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfacActionPerformed(evt);
            }
        });

        btnpay.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        btnpay.setText("Guarda e Imprime la factura");
        btnpay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpayActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial Narrow", 1, 36)); // NOI18N
        jLabel3.setText("Total:");

        jLabel4.setFont(new java.awt.Font("Arial Narrow", 1, 36)); // NOI18N
        jLabel4.setText("Factura Nro:");

        txtefectivo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        txtefectivo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtefectivo.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        txtefectivo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtefectivoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtefectivoFocusLost(evt);
            }
        });
        txtefectivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtefectivoActionPerformed(evt);
            }
        });
        txtefectivo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtefectivoKeyReleased(evt);
            }
        });

        txttotal.setEditable(false);
        txttotal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        txttotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txttotal.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N

        Cerrar.setBackground(new java.awt.Color(255, 0, 51));
        Cerrar.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        Cerrar.setForeground(new java.awt.Color(255, 255, 255));
        Cerrar.setText("Cerrar");
        Cerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txttotal)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                        .addGap(276, 276, 276))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Cerrar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtefectivo)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtfac))
                            .addComponent(btnpay, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtcambio, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtfac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(1, 1, 1)
                .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(62, 62, 62))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(txtefectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtcambio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnpay, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Cerrar, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtcambioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcambioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcambioActionPerformed

    private void txtfacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfacActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfacActionPerformed

    private void btnpayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpayActionPerformed
        // TODO add your handling code here:
        BigDecimal pay = new BigDecimal(txtefectivo.getText().replaceAll(",", ""));
        if (pay.doubleValue() >= 0) {
            try {
                efectivo = pay;
                pago = true;
                Factura f1 = jpfac.findFactura(id);
                f1.setEfectivo(pay);
                f1.setEstadofact(new Estadofact(2));
                jpfac.edit(f1);
                imprimirOrden();
                imprimirFactura();
                this.dispose();
            } catch (Exception ex) {
                Logger.getLogger(frmPagofac.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnpayActionPerformed

    public void imprimirOrden() {
        // TODO add your handling code here:
        try {
            Integer id = fpk.getIdFactura();
            if (id > 0) {
                if (jpdfac.getDetestadoCount(id) > 0) {
                    report.startOrdenCocinaprint("" + id);
                    System.out.println("" + jpdfac.actualizarEstadodet(id));
                } else {
                    //JOptionPane.showMessageDialog(null, "No hay Ordenes de Cocina pendientes!", "Orden de Cocina", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception e) {
        }
    }

    public void imprimirFactura() {
        // TODO add your handling code here:
        try {
            Integer id =fpk.getIdFactura();
            if (id != null) {
                if (id > 0) {
                    report.startReportprint("" + id);
                }
            } else {
                JOptionPane.showMessageDialog(null, "La factura no ha sido cobrada!, presione \"Guardar\" para Cobrar!", "Facturacion!", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println("Error->" + e.getMessage());
        }
    }

    private void txtefectivoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtefectivoFocusGained
        // TODO add your handling code here:
        txtefectivo.setBackground(Color.red);
    }//GEN-LAST:event_txtefectivoFocusGained

    private void txtefectivoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtefectivoFocusLost
        // TODO add your handling code here:
        txtefectivo.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtefectivoFocusLost

    private void txtefectivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtefectivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtefectivoActionPerformed

    private void txtefectivoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtefectivoKeyReleased
        // TODO add your handling code here:
        try {
            if (!txtefectivo.getText().isEmpty()) {
                BigDecimal x = new BigDecimal(txtefectivo.getText().replaceAll(",", ""));
                BigDecimal y = new BigDecimal(txttotal.getText().replaceAll(",", ""));
                if (x.doubleValue() > 0) {
                    BigDecimal cam = new BigDecimal(x.doubleValue()).subtract(new BigDecimal(y.doubleValue()), DECIMAL32).setScale(2, RoundingMode.HALF_UP);
                    if (cam.doubleValue() < 0) {
                        //JOptionPane.showMessageDialog(null,"El Efectivo debe ser mayor o Igual al Total a Pagar!","Error!",JOptionPane.WARNING_MESSAGE);
                        btnpay.setEnabled(false);
                        txtefectivo.setFocusable(true);
                    } else {
                        btnpay.setEnabled(true);
                        txtcambio.setText("" + cam);
                    }

                }
            }
        } catch (Exception e) {
            System.out.println("Error->" + e.getMessage());
        }
    }//GEN-LAST:event_txtefectivoKeyReleased

    private void CerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CerrarActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_CerrarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Cerrar;
    private javax.swing.JButton btnpay;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtcambio;
    public javax.swing.JFormattedTextField txtefectivo;
    public javax.swing.JTextField txtfac;
    private javax.swing.JFormattedTextField txttotal;
    // End of variables declaration//GEN-END:variables
}
