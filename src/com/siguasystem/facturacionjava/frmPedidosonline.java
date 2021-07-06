/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siguasystem.facturacionjava;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.siguasystem.facturacionjava.Pedidos;
import com.siguasystem.modelo.Correlativossar;
import com.siguasystem.modelo.Factura;
import com.siguasystem.modelo.FacturaJpaController;
import com.siguasystem.modelo.Producto;
import com.siguasystem.modelo.ProductoJpaController;
import com.siguasystem.modelo.pedidos.PedidosJpaController;
import com.siguasystem.modelo.pedidos.Productochan;
import com.siguasystem.modelo.pedidos.ProductochanJpaController;
import com.siguasystem.modelo.pedidos.exceptions.NonexistentEntityException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

/**
 *
 * @author joramos
 */
public class frmPedidosonline extends javax.swing.JInternalFrame {

    private Tablabuscapedidos tabped = new Tablabuscapedidos();
    private Tablapedidodet tabpeddet = new Tablapedidodet();
    EntityManagerFactory emPed = Persistence.createEntityManagerFactory("pedidosOnline");
    EntityManagerFactory emlocal = Persistence.createEntityManagerFactory("Restorant");
    PedidosJpaController jpa = new PedidosJpaController(emPed);
    ProductochanJpaController jproline = new ProductochanJpaController(emPed);
    ProductoJpaController jprolocal = new ProductoJpaController(emlocal);
    public FacturaJpaController jpfac = new FacturaJpaController(emlocal);
    frmFacturaORMonline frmfaO = null;
    String id = "";
    String codcli = "";
    String nomcli="";
    String coordenadas="";
    String factual = DateFormat.getDateInstance().format(new Date()).toString();
    DateFormat formatfac = new SimpleDateFormat("dd/MM/yyyy", Locale.US);//);
    detallefactura dtf;
   String idpeddido="";
    MenuprincipalPedidosOnline mp;

    /**
     * Creates new form frmPedidosonline
     */
    public frmPedidosonline() {
        initComponents();
        tabpedidos.setModel(tabped.crearTabla());
        tabdetalleped.setModel(tabpeddet);
        cargardesdeBD();
        mp = (MenuprincipalPedidosOnline ) getTopLevelAncestor();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabpedidos = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabdetalleped = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblnreg = new javax.swing.JLabel();
        lbblid = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        btnexport = new javax.swing.JButton();
        btnCleartb = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);

        jButton1.setText("Ver Pedidos");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tabpedidos.setModel(new javax.swing.table.DefaultTableModel(
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
        tabpedidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabpedidosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabpedidos);

        tabdetalleped.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tabdetalleped);

        jLabel1.setText("Listado de Pedidos:");

        jLabel2.setText("Detalle del pedido:");

        jLabel3.setText("Total de Pedidos: #");

        jButton2.setText("Procesar Pedido");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnexport.setText("Enviar Productos a Pedidos Online");
        btnexport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnexportActionPerformed(evt);
            }
        });

        btnCleartb.setText("Limpiar Productos Online");
        btnCleartb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCleartbActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblnreg, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbblid))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(30, 30, 30)
                        .addComponent(btnexport)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCleartb)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(btnexport)
                    .addComponent(btnCleartb))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(lbblid))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblnreg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //cargardesdeLink();
        cargardesdeBD();
    }//GEN-LAST:event_jButton1ActionPerformed

    public void cargardesdeBD() {
        try {
            // TODO add your handling code here:
            //Prepara la tabla para cargar los elementos
            DefaultTableModel df = tabped.crearTabla();
            List<com.siguasystem.modelo.pedidos.Pedidos> lped = jpa.findPedidosEstado(0);
            for (com.siguasystem.modelo.pedidos.Pedidos p : lped) {
                df.addRow(new Object[]{p.getId(), p.getCorreo(), "Latitud:"+p.getLatitud()+" Longitud:"+p.getLongitud(), p.getRnt(), "" + p.getTelefono(), p.getProductos()});
                System.out.println("ID:" + p.getId() + "Correo:" + p.getCorreo() + "Telefono:" + p.getTelefono());
                System.out.println("Productos:" + p.getProductos());
            }
            lblnreg.setText("" + df.getRowCount());
            tabpedidos.setModel(df);
        } catch (Exception ex) {
            Logger.getLogger(frmPedidosonline.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cargardesdeLink() {
        try {
            // TODO add your handling code here:
            //Prepara la tabla para cargar los elementos
            DefaultTableModel df = tabped.crearTabla();
            Gson json = new Gson();
            String txtjson = readUrl("http://localhost:8080/pedidosPendientes");
            Pedidos[] lped = json.fromJson(txtjson, Pedidos[].class);
            for (Pedidos p : lped) {
                df.addRow(new Object[]{p.getId(), p.getCorreo(), p.getNombrecliente(), p.getRnt(), "" + p.getTelefono(), p.getProductos()});
                System.out.println("ID:" + p.getId() + "Correo:" + p.getCorreo() + "Telefono:" + p.getTelefono());
                System.out.println("Productos:" + p.getProductos());
            }
            tabpedidos.setModel(df);
        } catch (Exception ex) {
            Logger.getLogger(frmPedidosonline.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void tabpedidosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabpedidosMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            if (tabpedidos.getRowCount() > 0) {
                id = tabpedidos.getModel().getValueAt(tabpedidos.getSelectedRow(), 0).toString();
                codcli=tabpedidos.getModel().getValueAt(tabpedidos.getSelectedRow(), 4).toString();
                nomcli=tabpedidos.getModel().getValueAt(tabpedidos.getSelectedRow(), 1).toString();
                coordenadas=tabpedidos.getModel().getValueAt(tabpedidos.getSelectedRow(), 2).toString();
                cargarDetallePedidos();
            }
        }
    }//GEN-LAST:event_tabpedidosMouseClicked

    public void cargarDetallePedidos() {
        System.out.println("ID PEdido:" + id);
        lbblid.setText(id);
        String[] detalle = tabpedidos.getModel().getValueAt(tabpedidos.getSelectedRow(), 5).toString().split(";");
        //Creando el detalle
        DefaultTableModel df = tabpeddet.crearTabla();
        int i = 0;
        dtf=new detallefactura();
        for (String d : detalle) {
            if (!d.isEmpty()) {
                i++;
                String cod = "";
                String nombre = "";
                String cant = "";
                String precio = "";
                String comen = "";
                if (d.contains("Comentarios")) {
                    //Con descripcion
                    cod = d.substring(d.indexOf("Cod") + 4, d.indexOf("Nombre")).trim();
                    nombre = d.substring(d.indexOf("Nombre") + 7, d.indexOf("Cantidad")).trim();
                    cant = d.substring(d.indexOf("Cantidad") + 9, d.indexOf("Precio")).trim();
                    precio = d.substring(d.indexOf("Precio") + 7, d.indexOf("Comentarios")).trim();
                    comen = d.substring(d.indexOf("Comentarios") + 12, d.indexOf("Total")).trim();
                    //Asignacion a Clase

                } else {
                    //Sin descripcion y sin comentarios
                    cod = d.substring(d.indexOf("Cod") + 4, d.indexOf("Nombre")).trim();
                    nombre = d.substring(d.indexOf("Nombre") + 7, d.indexOf("Cantidad")).trim();
                    cant = d.substring(d.indexOf("Cantidad") + 9, d.indexOf("Precio")).trim();
                    precio = d.substring(d.indexOf("Precio") + 7, d.indexOf("Total")).trim();
                    comen = "";//d.substring(d.indexOf("Total")+6,d.length()).trim();
                }
                //Agrega el detalle para la factura.
                dtf.addPArticulos(agregarDetalle(cod,nombre, cant, precio, comen));
                df.addRow(new Object[]{i, cod, nombre, cant, precio, comen});
            }
        }
        tabdetalleped.setModel(df);
    }

    public articulo agregarDetalle(String cod,String nompro, String cant, String precio, String comen) {
        Query qsql = jpfac.getEntityManager().createNativeQuery("select * from producto c where Lower(c.idproducto)='" + cod.toLowerCase().trim() + "' limit 0,1", Producto.class);//qsql.setParameter("n", txtnom.getText());
           List<Producto> prosel = (List<Producto>) qsql.getResultList();
            articulo padd = new articulo();
            if(prosel.size()==0){
                padd.setCodigo(cod.trim());
               padd.setNombre(nompro);
               padd.setCantidad(Double.parseDouble(cant));
               padd.setIsv(false);
                if (padd.isIsv()) {
                    padd.setPrecio(BigDecimal.valueOf((prosel.get(0).getPrecio() / (1 + prosel.get(0).getImpuesto().doubleValue()))));
                    //padd.setValisv((padd.getPrecio().doubleValue()/ (1 + prosel.get(0).getImpuesto().doubleValue())) * prosel.get(0).getImpuesto().doubleValue());
                }else{
                    padd.setPrecio(BigDecimal.valueOf(Double.parseDouble(precio)));
                    padd.setValisv(0.00);
                }
               padd.setComentarios(comen);
               padd.setDescuento(0.00);
               padd.setSubtotal(padd.getPrecio().doubleValue()*padd.getCantidad());
               padd.setEstado("0");
                return padd;
            }
            if (prosel.get(0) != null) {
               padd.setCodigo(cod.trim());
               padd.setNombre(prosel.get(0).getNombre());
               padd.setCantidad(Double.parseDouble(cant));
               padd.setIsv(false);
                if (padd.isIsv()) {
                    padd.setPrecio(BigDecimal.valueOf((prosel.get(0).getPrecio() / (1 + prosel.get(0).getImpuesto().doubleValue()))));
                }else{
               padd.setPrecio(BigDecimal.valueOf((prosel.get(0).getPrecio())));
                }
               padd.setComentarios(comen);
               padd.setIsv(false);
               padd.setValisv((prosel.get(0).getPrecio() / (1 + prosel.get(0).getImpuesto().doubleValue())) * prosel.get(0).getImpuesto().doubleValue());
               padd.setDescuento(0.00);
               padd.setSubtotal(prosel.get(0).getPrecio()*padd.getCantidad());
               padd.setEstado("0");
            }
     return padd;
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (!id.isEmpty()) {
            try {
                mp = (MenuprincipalPedidosOnline ) getTopLevelAncestor();
                //mp.abrirProgreso();
                abrirFacturaOrm();
                JOptionPane.showMessageDialog(null, "Pedido Procesado Correctamente!");
                //Actualiza el listado de pedidos
                cargardesdeBD();
                //Actualiza el pedido seleccionado, se debe dar
                //doble clic al pedido para procesarlo
                //actualizarPedidoonline();
            } catch (Exception ex) {
                Logger.getLogger(frmPedidosonline.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un pedido con doble clic.");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private Integer ultimaFact() {
        Integer x = 0;
        try {
            Query qsql = jpfac.getEntityManager().createNativeQuery("select * from factura c where idfactura=(select max(idfactura) from factura)", Factura.class);//qsql.setParameter("n", txtnom.getText());
            qsql.setHint(QueryHints.REFRESH, HintValues.TRUE);//Actualiza
            Factura f2 = (Factura) qsql.getSingleResult();
            ///Verificar en la tabla correlativos cual es el ultimo numero
            ///Debido a que las facturas se pueden vencer por fecha o por
            /// que llegaron al limite de numeracion.
            Query qsql2 = jpfac.getEntityManager().createNativeQuery("SELECT * FROM correlativossar c where c.estado=1 order by fechacreacion", Correlativossar.class);
            Correlativossar cor = (Correlativossar) qsql2.getSingleResult();
            if (f2.getIdFactura() < cor.getRangoini()) {
                x = cor.getRangoini();
            } else {
                x = f2.getIdFactura();
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return x;
    }

    public void actualizarPedidoonline() throws Exception, NumberFormatException {
        if (!id.isEmpty()) {
            com.siguasystem.modelo.pedidos.Pedidos ped = jpa.findPedidos(Long.parseLong(id));
            ped.setEstado((short) 1);
            jpa.edit(ped);
           //Actualiza el listado de pedidos
            cargardesdeBD();
        }
    }

    public void abrirFacturaOrm() {
       
        /******************************/
        MenuprincipalPedidosOnline mn= (MenuprincipalPedidosOnline) getTopLevelAncestor();
        idpeddido=id;
        frmfaO = new frmFacturaORMonline(dtf,mn,1,codcli,nomcli,coordenadas,idpeddido);
        frmfaO.setClosable(true);
        frmfaO.setLocation(0, 0);
        frmfaO.pack();
        this.getDesktopPane().add(frmfaO);
        frmfaO.setVisible(true);
    }
    private void btnexportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnexportActionPerformed
        // TODO add your handling code here:
        List<Producto> lprolocal = jprolocal.findProductoEntities(40,1);
        int x = 0;
        for (Producto p : lprolocal) {
            try {
                x++;
                Productochan pchan = new Productochan();
                pchan.setId(Long.parseLong("" + x));
                pchan.setNombre(p.getNombre());
                pchan.setIdproducto(p.getIdProducto());
                pchan.setCantidad(1);
                pchan.setPrecio(p.getPrecio());
                pchan.setDescripcion(p.getDescripcion());
                pchan.setCategoria(p.getCategoria());
                System.out.println("Producto enviado:" + p);
                jproline.create(pchan);
            } catch (Exception ex) {
                Logger.getLogger(frmPedidosonline.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        JOptionPane.showMessageDialog(null, "Productos exportados Correctamente!");
    }//GEN-LAST:event_btnexportActionPerformed

    private void btnCleartbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCleartbActionPerformed
        // TODO add your handling code here:
        int i=0;
       // if (tabpedidos.getRowCount()>0) {
       //      JOptionPane.showMessageDialog(null, "Hay Pedidos pendientes, debe procesarlos antes de limpiar los productos!");
       //      return;
       // }
        for (Productochan p : jproline.findProductochanEntities()) {
            try {
                jproline.destroy(p.getId());
                i++;
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(frmPedidosonline.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         if (i>0) {
               JOptionPane.showMessageDialog(null, "Tabla Productos Limpiada Correctamente!"); 
        }
    }//GEN-LAST:event_btnCleartbActionPerformed

    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1) {
                buffer.append(chars, 0, read);
            }

            return buffer.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCleartb;
    private javax.swing.JButton btnexport;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbblid;
    private javax.swing.JLabel lblnreg;
    private javax.swing.JTable tabdetalleped;
    private javax.swing.JTable tabpedidos;
    // End of variables declaration//GEN-END:variables
}
