/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siguasystem.facturacionjava;

import com.siguasystem.modelo.Producto;
import com.siguasystem.modelo.ProductoJpaController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

/**
 *
 * @author juan
 */
public class panelDet extends javax.swing.JPanel implements ActionListener {

    /**
     * Creates new form panelDet
     */
    public Integer addpro = 0;
    public articulo producto = new articulo();
    EntityManagerFactory emFac = Persistence.createEntityManagerFactory("Restorant");
    private ProductoJpaController finpro = new ProductoJpaController(emFac);//(Persistence.createEntityManagerFactory("Restorant"));
    public Producto pselec;
    private BigDecimal subtot;

    public panelDet() {
        initComponents();
        MostrarDatos(txtcod);
        // txtsubtot.setEnabled(false);
        //btnadd.addActionListener((ActionListener) this);
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
        jLabel2 = new javax.swing.JLabel();
        txtnompro = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtcan = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        txtprecio = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtdesc = new javax.swing.JSpinner();
        txtsubtot = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        txtisv = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        txtexist = new javax.swing.JSpinner();
        btnadd = new javax.swing.JButton();
        btncancel = new javax.swing.JButton();
        btnNewpro = new javax.swing.JButton();
        txtcod = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        txtcomen = new javax.swing.JTextField();

        jPanel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel1KeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Codigo del Producto:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Nombre/Descripcion:");

        txtnompro.setEditable(false);
        txtnompro.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Cantidad:");

        txtcan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtcan.setModel(new javax.swing.SpinnerNumberModel(1, 0, null, 1));
        txtcan.setEditor(new javax.swing.JSpinner.NumberEditor(txtcan, ""));
        txtcan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtcanFocusLost(evt);
            }
        });
        txtcan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcanKeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Precio:");

        txtprecio.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtprecio.setModel(new javax.swing.SpinnerNumberModel(0.0f, 0.0f, null, 1.0f));
        txtprecio.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtprecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtprecioKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtprecioKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Subtotal:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Descuento:");

        txtdesc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtdesc.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, null, 0.01d));

        txtsubtot.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtsubtot.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 0.0d, 1.0d));
        txtsubtot.setEnabled(false);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Isv:");

        txtisv.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtisv.setEnabled(false);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Existencia Actual:");

        txtexist.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        btnadd.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnadd.setText("Agregar");
        btnadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddActionPerformed(evt);
            }
        });

        btncancel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btncancel.setText("Cancelar");
        btncancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelActionPerformed(evt);
            }
        });

        btnNewpro.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnNewpro.setText("Crear Producto");
        btnNewpro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewproActionPerformed(evt);
            }
        });

        txtcod.setEditable(true);
        txtcod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcodActionPerformed(evt);
            }
        });
        txtcod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcodKeyReleased(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Comentarios:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtisv)
                            .addComponent(txtcan, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtprecio, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(txtsubtot))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtdesc))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtnompro))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcomen))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtexist, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(105, 105, 105)
                        .addComponent(btnadd)
                        .addGap(5, 5, 5)
                        .addComponent(btncancel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtcod, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNewpro)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtcod)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(btnNewpro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtnompro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtcan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtprecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtdesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtisv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtsubtot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtcomen, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnadd)
                    .addComponent(btncancel)
                    .addComponent(txtexist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddActionPerformed
        // TODO add your handling code here:
        System.out.println("Producto agregado");
        addpro = 1;
        producto.setCodigo(pselec.getIdProducto());
        producto.setNombre(txtnompro.getText());
        producto.setCantidad(Double.parseDouble(txtcan.getValue().toString()));
        producto.setDescuento(Double.parseDouble(txtdesc.getValue().toString()));
        producto.setIsv(true);
        producto.setPrecio(BigDecimal.valueOf(Double.parseDouble(txtprecio.getValue().toString())));
        producto.setValisv(Double.parseDouble(txtisv.getValue().toString()));
        producto.setEstado("0");
        producto.setComentarios(txtcomen.getText());
        cerrarDialogo();
    }//GEN-LAST:event_btnaddActionPerformed

    private void btncancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelActionPerformed
        cerrarDialogo();
    }//GEN-LAST:event_btncancelActionPerformed

    private void btnNewproActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewproActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNewproActionPerformed

    private void txtcodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcodActionPerformed
        // TODO add your handling code here:
        try {
            if (txtcod.getModel().getSize() > 0) {
                pselec = (Producto) txtcod.getSelectedItem();
                if (txtcod.getSelectedItem().toString() != null) {
                    txtnompro.setText(pselec.getNombre());
                    // txtnompro.requestFocus();
                    txtprecio.setValue(pselec.getPrecio());
                    txtcan.requestFocus();
                }
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }

    }//GEN-LAST:event_txtcodActionPerformed

    private void txtcodKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcodKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcodKeyReleased

    private void jPanel1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel1KeyPressed

    private void txtprecioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprecioKeyReleased
        //JDialog jd = (JDialog) this.getRootPane().getParent();
        //Global.cambiarFoco(jd);
        if (Double.parseDouble(txtcan.getValue().toString()) > 0) {
            txtsubtot.setValue(Double.parseDouble(txtcan.getValue().toString()) * Double.parseDouble(txtprecio.getValue().toString()));
            //subtot.add(new BigDecimal(Double.parseDouble(txtcan.getValue().toString()) * Double.parseDouble(txtprecio.getValue().toString())));
            //subtot.multiply(BigDecimal.valueOf(Double.parseDouble(txtcan.getValue().toString()))).multiply(BigDecimal.valueOf(Double.parseDouble(txtprecio.getValue().toString()))).setScale(2, RoundingMode.HALF_UP);
            //txtsubtot.setValue(subtot.doubleValue());
        }
    }//GEN-LAST:event_txtprecioKeyReleased

    private void txtcanFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcanFocusLost
        // TODO add your handling code here:
        if (Double.parseDouble(txtprecio.getValue().toString()) > 0) {
             txtsubtot.setValue(Double.parseDouble(txtcan.getValue().toString()) * Double.parseDouble(txtprecio.getValue().toString()));
           // subtot.multiply(BigDecimal.valueOf(Double.parseDouble(txtcan.getValue().toString()))).multiply(BigDecimal.valueOf(Double.parseDouble(txtprecio.getValue().toString()))).setScale(2, RoundingMode.HALF_UP);
            //txtsubtot.setValue(subtot.doubleValue());
        }
    }//GEN-LAST:event_txtcanFocusLost

    private void txtprecioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprecioKeyPressed
        // TODO add your handling code here:
        if (Double.parseDouble(txtcan.getValue().toString()) > 0) {
            //txtsubtot.setValue(Double.parseDouble(txtcan.getValue().toString()) * Double.parseDouble(txtprecio.getValue().toString()));
            //subtot.add(new BigDecimal(Double.parseDouble(txtcan.getValue().toString()) * Double.parseDouble(txtprecio.getValue().toString())));
            subtot.multiply(BigDecimal.valueOf(Double.parseDouble(txtcan.getValue().toString()))).multiply(BigDecimal.valueOf(Double.parseDouble(txtprecio.getValue().toString()))).setScale(2, RoundingMode.HALF_UP);
            txtsubtot.setValue(subtot.doubleValue());
        }
    }//GEN-LAST:event_txtprecioKeyPressed

    private void txtcanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcanKeyReleased
        // TODO add your handling code here:
        if (Double.parseDouble(txtcan.getValue().toString()) > 0) {
            //txtsubtot.setValue(Double.parseDouble(txtcan.getValue().toString()) * Double.parseDouble(txtprecio.getValue().toString()));
            //subtot.add(new BigDecimal(Double.parseDouble(txtcan.getValue().toString()) * Double.parseDouble(txtprecio.getValue().toString())));
            subtot.multiply(BigDecimal.valueOf(Double.parseDouble(txtcan.getValue().toString()))).multiply(BigDecimal.valueOf(Double.parseDouble(txtprecio.getValue().toString()))).setScale(2, RoundingMode.HALF_UP);
            txtsubtot.setValue(subtot.doubleValue());
        }
    }//GEN-LAST:event_txtcanKeyReleased

    public DefaultComboBoxModel ObtenerListapro(String cadena) {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        try {
            Query qsql = finpro.getEntityManager().createNativeQuery("select * from producto c where LOWER(c.nombre) LIKE lower(\'%" + cadena.trim() + "%\') or  LOWER(c.idproducto) LIKE lower(\'%"+ cadena.trim() + "%\')", Producto.class);
            List<Producto> flp = qsql.getResultList();
            modelo.addElement("");
            for (Producto it : flp) {
                Producto p = it;
                modelo.addElement(p);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error ");
            System.out.println("Coneccion incorrecta: " + ex);
        }
        return modelo;
    }

    public DefaultComboBoxModel ObtenerLista(String cadena) {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        try {
            for (Producto it : finpro.findProductoEntities()) {
                Producto p = it;
                modelo.addElement(p);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error ");
            System.out.println("Coneccion incorrecta: " + ex);
        }
        return modelo;
    }

    public void MostrarDatos(final JComboBox combobox) {
        combobox.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
                    String cadenaEscrita = combobox.getEditor().getItem().toString();
                    //if (evt.getKeyCode() >= 65 && evt.getKeyCode() <= 90 || evt.getKeyCode() >= 96 && evt.getKeyCode() <= 105 || evt.getKeyCode() == 8) {
                    combobox.setModel(ObtenerListapro(cadenaEscrita));
                    if (combobox.getItemCount() > 0) {
                        combobox.showPopup();
                        if (evt.getKeyCode() != 8) {
                            ((JTextComponent) combobox.getEditor().getEditorComponent()).select(cadenaEscrita.length(), combobox.getEditor().getItem().toString().length());

                        } else {
                            combobox.getEditor().setItem(cadenaEscrita);
                        }
                    } else {
                        combobox.addItem(cadenaEscrita);
                    }
                    // }
                }//else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                //   txtnompro.requestFocus();
                //}
            }
        });
        if (txtcod.getModel().getSize() > 0) {
            if (txtcod.getSelectedItem().toString() != null) {
                txtnompro.setText(((Producto) txtcod.getSelectedItem()).getDescripcion());
            }
        }

    }

    public void cerrarDialogo() {
        // TODO add your handling code here:
        JDialog jd = (JDialog) this.getRootPane().getParent();
        System.out.println("Salio del Detalle");
        jd.dispose();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNewpro;
    private javax.swing.JButton btnadd;
    public javax.swing.JButton btncancel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSpinner txtcan;
    private javax.swing.JComboBox<String> txtcod;
    private javax.swing.JTextField txtcomen;
    private javax.swing.JSpinner txtdesc;
    private javax.swing.JSpinner txtexist;
    private javax.swing.JSpinner txtisv;
    private javax.swing.JTextField txtnompro;
    private javax.swing.JSpinner txtprecio;
    private javax.swing.JSpinner txtsubtot;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        //btnadd.addActionListener(this);
    }
}
