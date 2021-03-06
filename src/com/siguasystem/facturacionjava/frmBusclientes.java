/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siguasystem.facturacionjava;


import com.siguasystem.modelo.Cliente;
import com.siguasystem.modelo.ClienteJpaController;
import com.siguasystem.modelo.Producto;
import com.siguasystem.modelo.ProductoJpaController;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;





/**
 *
 * @author juan
 */
public class frmBusclientes extends javax.swing.JInternalFrame {

    /**
     *
     */
    private DefaultComboBoxModel cbomodelti = new DefaultComboBoxModel();
    private Tablabuscapro bp = new Tablabuscapro();
    EntityManagerFactory emFac = Persistence.createEntityManagerFactory("Restorant");
    private ClienteJpaController fincli = new ClienteJpaController(emFac);//(Persistence.createEntityManagerFactory("Restorant"));
    public Cliente csel;

    public frmBusclientes() {
        initComponents();
        listado.setModel(bp.crearTabla());
        tamanioCol();
        lblnreg.setText("0");
        //Global.cambiarFoco(this);
    }

    public void tamanioCol() {
        listado.getColumnModel().getColumn(0).setPreferredWidth(10);
        listado.getColumnModel().getColumn(1).setPreferredWidth(50);
        listado.getColumnModel().getColumn(2).setPreferredWidth(250);
        listado.getColumnModel().getColumn(3).setPreferredWidth(50);
        listado.getColumnModel().getColumn(4).setPreferredWidth(100);
        listado.getColumnModel().getColumn(5).setPreferredWidth(50);
        listado.getColumnModel().getColumn(5).setPreferredWidth(100);
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
        txtcod = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtnom = new javax.swing.JTextField();
        btnfind = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txttel = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listado = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        lblnreg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Busqueda de Clientes");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Parametros de Busqueda"));

        jLabel1.setText("Codigo:");

        txtcod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcodKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcodKeyReleased(evt);
            }
        });

        jLabel2.setText("Nombre:");

        txtnom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnomKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtnomKeyReleased(evt);
            }
        });

        btnfind.setText("Buscar");

        jLabel4.setText("Telefono/Celular:");

        txttel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txttelKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtcod, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtnom, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txttel, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnfind, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtcod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtnom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnfind)
                    .addComponent(jLabel4)
                    .addComponent(txttel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Resultados"));

        listado.setModel(new javax.swing.table.DefaultTableModel(
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
        listado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                listadoMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(listado);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setText("Total Registros encontrados: ");

        lblnreg.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblnreg.setText("jLabel4");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 885, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblnreg)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblnreg)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtcodKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcodKeyReleased
        // TODO add your handling code here:
        if (txtcod.getText().length() > 0) {
            ProductoFilcodlike();  lblnreg.setText(""+listado.getRowCount());
        }else{
           listado.setModel(bp.crearTabla());
           tamanioCol();
            lblnreg.setText(""+listado.getRowCount());
        }
    }//GEN-LAST:event_txtcodKeyReleased

    private void txtnomKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnomKeyReleased
        // TODO add your handling code here:
         if (txtnom.getText().length() > 0) {
            ProductoFilnom();  lblnreg.setText(""+listado.getRowCount());
        }else{
           listado.setModel(bp.crearTabla());
             tamanioCol();
          lblnreg.setText(""+listado.getRowCount());
             System.out.println(""+listado.getRowCount());
        }
    }//GEN-LAST:event_txtnomKeyReleased

    private void txtcodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcodKeyPressed
        // TODO add your handling code here:
        Global.cambiarFoco(this);
    }//GEN-LAST:event_txtcodKeyPressed

    private void txtnomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnomKeyPressed
        // TODO add your handling code here:
         Global.cambiarFoco(this);
    }//GEN-LAST:event_txtnomKeyPressed

    private void txttelKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttelKeyReleased
        // TODO add your handling code here:
        ProductoFilcellike();
    }//GEN-LAST:event_txttelKeyReleased

    private void listadoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listadoMousePressed
        // TODO add your handling code here:
        if (evt.getClickCount()==2) {
             if (listado.getRowCount() > 0) {
                String pselstring =  listado.getModel().getValueAt(listado.getSelectedRow(),1).toString();
                csel=fincli.findCliente(pselstring);
            }
            this.dispose();
        }
    }//GEN-LAST:event_listadoMousePressed

     private void ProductoFilnom() {
        DefaultTableModel df = bp.crearTabla();
        List<Cliente> lpro = new ArrayList<Cliente>();
        try {
            String pt=txtnom.getText().toLowerCase();
            Query qsql=fincli.getEntityManager().createNativeQuery("select * from cliente c where LOWER(c.nombre) LIKE \'"+pt+"%\'",Cliente.class);//qsql.setParameter("n", txtnom.getText());
            lpro =qsql.getResultList();
            int i=1;
            for (Iterator<Cliente> iterator = lpro.iterator(); iterator.hasNext();) {
                Cliente p = iterator.next();
                if (p.getIdCliente()!=null) {
                    df.addRow(new Object[]{i++,p.getIdCliente(),p.getNombre(),p.getDireccion(),p.getCelular(),p.getTelefono()});
               } 
            }
            listado.setModel(df);
            tamanioCol();
             lblnreg.setText(""+listado.getRowCount());
        } catch (Exception e) {
            System.out.println("Error:"+e.getMessage());
        }
    }
     
    private void ProductoFilcodlike() {
        DefaultTableModel df = bp.crearTabla();
        List<Cliente> lpro = new ArrayList<Cliente>();
        try {
            String pt=txtcod.getText().toLowerCase();
            Query qsql=fincli.getEntityManager().createNativeQuery("select * from cliente c where LOWER(c.idcliente) LIKE \'"+pt+"%\'",Cliente.class);//qsql.setParameter("n", txtnom.getText());
            lpro =qsql.getResultList();
            int i=1;
            for (Iterator<Cliente> iterator = lpro.iterator(); iterator.hasNext();) {
                Cliente p = iterator.next();
                //NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("es","es_HN"));
               // String precio = formatter.format(p.getPrecio());
               df.addRow(new Object[]{i++,p.getIdCliente(),p.getNombre(),p.getDireccion(),p.getCelular(),p.getTelefono()});
            }
            listado.setModel(df);
            tamanioCol();
             lblnreg.setText(""+listado.getRowCount());
        } catch (Exception e) {
        }
    }
    
     private void ProductoFilcellike() {
        DefaultTableModel df = bp.crearTabla();
        List<Cliente> lpro = new ArrayList<Cliente>();
        try {
            String pt=txttel.getText().toLowerCase();
            Query qsql=fincli.getEntityManager().createNativeQuery("select * from cliente c where LOWER(c.telefono) LIKE \'"+pt+"%\' or  LOWER(c.celular) LIKE \'"+pt+"%\'",Cliente.class);//qsql.setParameter("n", txtnom.getText());
            lpro =qsql.getResultList();
            int i=1;
            for (Iterator<Cliente> iterator = lpro.iterator(); iterator.hasNext();) {
                Cliente p = iterator.next();
                //NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("es","es_HN"));
               // String precio = formatter.format(p.getPrecio());
               df.addRow(new Object[]{i++,p.getIdCliente(),p.getNombre(),p.getDireccion(),p.getCelular(),p.getTelefono()});
            }
            listado.setModel(df);
            tamanioCol();
             lblnreg.setText(""+listado.getRowCount());
        } catch (Exception e) {
        }
    }
     
    private void ProductoFilcod() {
        DefaultTableModel df = bp.crearTabla();
        Cliente lpro = new Cliente();
        try {
            int i=1;
           lpro = fincli.findCliente(txtcod.getText());
           NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("es","es_HN"));
            //String precio = formatter.format(p.getPrecioconta());
            //for (Iterator<MaestroProductos3tmp> iterator = lpro.iterator(); iterator.hasNext();) {
               Cliente p = lpro;
                df.addRow(new Object[]{i++,p.getIdCliente(),p.getNombre(),p.getDireccion(),p.getCelular(),p.getTelefono()});
           // }
            listado.setModel(df);
            tamanioCol();
        } catch (Exception e) {
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmBusclientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmBusclientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmBusclientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmBusclientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmBusclientes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnfind;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblnreg;
    private javax.swing.JTable listado;
    private javax.swing.JTextField txtcod;
    private javax.swing.JTextField txtnom;
    private javax.swing.JTextField txttel;
    // End of variables declaration//GEN-END:variables
}
