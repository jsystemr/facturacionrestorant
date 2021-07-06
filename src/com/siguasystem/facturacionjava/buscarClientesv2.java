/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siguasystem.facturacionjava;

import com.siguasystem.modelo.Cliente;
import com.siguasystem.modelo.ClienteJpaController;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jramos
 */
public class buscarClientesv2 extends javax.swing.JInternalFrame {

    /**
     * Creates new form buscarClientesv2
     */
    
     EntityManagerFactory emFac = Persistence.createEntityManagerFactory("Restorant");
    private DefaultComboBoxModel cbomodelti = new DefaultComboBoxModel();
    private Tablabuscacli bp = new Tablabuscacli();
    private Tabladetfacli bfac = new Tabladetfacli();
    private ClienteJpaController fincli = new ClienteJpaController(emFac);//(Persistence.createEntityManagerFactory("Restorant"));
    public Cliente csel;
    public Cliente cselfac;
    public buscarClientesv2() {
        initComponents();
        listado.setModel(bp.crearTabla());
        listado1.setModel(bfac.crearTabla());
        tamanioCol();
        tamanioCol2();
        listado.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (listado.getRowCount() > 0) {
                    String clisfac = listado.getModel().getValueAt(listado.getSelectedRow(), 1).toString();
                    System.out.println("Selecciono una fila..." + clisfac);
                    cselfac = fincli.findCliente(clisfac);
                    Filfaccli();
                }
            }
        });
         try {
             this.setSelected(true);
         } catch (PropertyVetoException ex) {
             Logger.getLogger(buscarClientesv2.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    public void tamanioCol() {
        listado.getColumnModel().getColumn(0).setPreferredWidth(10);
        listado.getColumnModel().getColumn(1).setPreferredWidth(50);
        listado.getColumnModel().getColumn(2).setPreferredWidth(250);
        listado.getColumnModel().getColumn(3).setPreferredWidth(250);
        listado.getColumnModel().getColumn(4).setPreferredWidth(100);
        listado.getColumnModel().getColumn(5).setPreferredWidth(100);
    }

    public void tamanioCol2() {
        listado1.getColumnModel().getColumn(0).setPreferredWidth(50);
        listado1.getColumnModel().getColumn(1).setPreferredWidth(150);
        listado1.getColumnModel().getColumn(2).setPreferredWidth(250);
        listado1.getColumnModel().getColumn(3).setPreferredWidth(350);

    }
    
    private void ProductoFilnom() {
        DefaultTableModel df = bp.crearTabla();
        List<Cliente> lpro = new ArrayList<Cliente>();
        try {
            String pt = txtnom.getText().toLowerCase();
            Query qsql = fincli.getEntityManager().createNativeQuery("select * from cliente c where LOWER(c.nombre) LIKE \'" + pt + "%\'", Cliente.class);//qsql.setParameter("n", txtnom.getText());
            lpro = qsql.getResultList();
            int i = 1;
            for (Iterator<Cliente> iterator = lpro.iterator(); iterator.hasNext();) {
                Cliente p = iterator.next();
                if (p.getIdCliente() != null) {
                    df.addRow(new Object[]{i++, p.getIdCliente(), p.getNombre(), p.getDireccion(), p.getCelular(), p.getTelefono()});
                }
            }
            listado.setModel(df);
            tamanioCol();
            lblnreg.setText("" + listado.getRowCount());
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    private void ProductoFilcodlike() {
        DefaultTableModel df = bp.crearTabla();
        List<Cliente> lpro = new ArrayList<Cliente>();
        try {
            String pt = txtcod.getText();
            Query qsql = fincli.getEntityManager().createNativeQuery("select * from cliente c where upper(c.idcliente) LIKE upper(\'" + pt + "%\')", Cliente.class);//qsql.setParameter("n", txtnom.getText());
            lpro = qsql.getResultList();
            int i = 1;
            for (Iterator<Cliente> iterator = lpro.iterator(); iterator.hasNext();) {
                Cliente p = iterator.next();
                //NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("es","es_HN"));
                // String precio = formatter.format(p.getPrecio());
                df.addRow(new Object[]{i++, p.getIdCliente(), p.getNombre(), p.getDireccion(), p.getCelular(), p.getTelefono()});
            }
            listado.setModel(df);
            tamanioCol();
            lblnreg.setText("" + listado.getRowCount());
        } catch (Exception e) {
        }
    }

    private void ProductoFilcellike() {
        DefaultTableModel df = bp.crearTabla();
        List<Cliente> lpro = new ArrayList<Cliente>();
        try {
            String pt = txttel.getText().toLowerCase();
            Query qsql = fincli.getEntityManager().createNativeQuery("select * from cliente c where LOWER(c.telefono) LIKE \'" + pt + "%\' or  LOWER(c.celular) LIKE \'" + pt + "%\'", Cliente.class);//qsql.setParameter("n", txtnom.getText());
            lpro = qsql.getResultList();
            int i = 1;
            for (Iterator<Cliente> iterator = lpro.iterator(); iterator.hasNext();) {
                Cliente p = iterator.next();
                //NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("es","es_HN"));
                // String precio = formatter.format(p.getPrecio());
                df.addRow(new Object[]{i++, p.getIdCliente(), p.getNombre(), p.getDireccion(), p.getCelular(), p.getTelefono()});
            }
            listado.setModel(df);
            tamanioCol();
            lblnreg.setText("" + listado.getRowCount());
        } catch (Exception e) {
        }
    }

    private void ProductoFilcod() {
        DefaultTableModel df = bp.crearTabla();
        Cliente lpro = new Cliente();
        try {
            int i = 1;
            lpro = fincli.findCliente(txtcod.getText());
            NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("es", "es_HN"));
            //String precio = formatter.format(p.getPrecioconta());
            //for (Iterator<MaestroProductos3tmp> iterator = lpro.iterator(); iterator.hasNext();) {
            Cliente p = lpro;
            df.addRow(new Object[]{i++, p.getIdCliente(), p.getNombre(), p.getDireccion(), p.getCelular(), p.getTelefono()});
            // }
            listado.setModel(df);
            tamanioCol();
        } catch (Exception e) {
        }
    }

    private void Filfaccli() {
        DefaultTableModel df = bfac.crearTabla();
        List<Object> lpro = new ArrayList<Object>();
        try {
            String pt = cselfac.getIdCliente();
            Query qsql = fincli.getEntityManager().createNativeQuery("select d.idfactura,d.fecha,d.nomproducto,d.comentarios from detallefactura d inner join factura f on d.idfactura=f.idfactura where f.cliente='" + pt + "' order by f.fecha DESC limit 0,50 ");//qsql.setParameter("n", txtnom.getText());
            lpro = qsql.getResultList();
            int i = 1;
            for (Iterator<Object> iterator = lpro.iterator(); iterator.hasNext();) {
                Object[] p = (Object[]) iterator.next();
                if (p != null) {
                    df.addRow(new Object[]{p[0].toString(), p[1].toString(), p[2].toString(), p[3].toString()});
                }
            }
            listado1.setModel(df);
            tamanioCol();
            lblnreg.setText("" + listado1.getRowCount());
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
            listado1.removeAll();
        }
    }
    
     public void buscarClientes() {
        // TODO add your handling code here:
        if (txtcod.getText().length() > 0) {
            ProductoFilcodlike();
            lblnreg1.setText("" + listado.getRowCount());
        } else {
            listado.setModel(bp.crearTabla());
            tamanioCol();
            lblnreg1.setText("" + listado.getRowCount());
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listado = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        lblnreg = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtnombre = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtdir = new javax.swing.JTextArea();
        txtidcliente = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtidcliente1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtrtn = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        listado1 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblnreg1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtcod = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtnom = new javax.swing.JTextField();
        btnfind = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txttel = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();

        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jPanel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jPanel1formKeyReleased(evt);
            }
        });
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Resultados"));
        jPanel2.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

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
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                listadoMouseReleased(evt);
            }
        });
        listado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                listadoKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(listado);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setText("Total Registros encontrados: ");

        lblnreg.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblnreg.setText("jLabel4");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N
        jPanel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("ID:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Nombre:");

        txtnombre.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtnombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtnombreFocusGained(evt);
            }
        });
        txtnombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtnombreKeyReleased(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Direccion:");

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setText("Agregar Cliente");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton2.setText("Actualizar Cliente");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        txtdir.setColumns(20);
        txtdir.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtdir.setLineWrap(true);
        txtdir.setRows(5);
        txtdir.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jScrollPane3.setViewportView(txtdir);

        txtidcliente.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtidcliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtidclienteFocusGained(evt);
            }
        });
        txtidcliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtidclienteKeyReleased(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Tel:");

        txtidcliente1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtidcliente1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtidcliente1FocusGained(evt);
            }
        });
        txtidcliente1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtidcliente1KeyReleased(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(204, 0, 51));
        jLabel12.setText("*Solo para ingresar un nuevo cliente.");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setText("RTN:");

        txtrtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtrtn.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtrtnFocusGained(evt);
            }
        });
        txtrtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtrtnKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtidcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtidcliente1))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtrtn, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(6, 6, 6))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                        .addComponent(txtnombre)
                        .addComponent(jLabel10)
                        .addComponent(txtidcliente1))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtidcliente)
                            .addComponent(jLabel7))
                        .addGap(0, 10, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(txtrtn))))
        );

        listado1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        listado1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                listado1MouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(listado1);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel5.setText("Pedidos  anteriores");
        jLabel5.setToolTipText("");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel8.setText("Total Registros encontrados: ");

        lblnreg1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblnreg1.setText("jLabel4");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblnreg))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(lblnreg1)
                        .addGap(6, 6, 6))
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(lblnreg1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblnreg)))
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Parametros de Busqueda"));
        jPanel3.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setText("Codigo:");

        txtcod.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txtcod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcodKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcodKeyReleased(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel2.setText("Nombre:");

        txtnom.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txtnom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnomKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtnomKeyReleased(evt);
            }
        });

        btnfind.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnfind.setText("Buscar");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel4.setText("Telefono/Celular:");

        txttel.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txttel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txttelKeyReleased(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(204, 0, 51));
        jLabel11.setText("*Para buscar escriba el nombre o codigo y presiona enter para buscar.");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
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
                        .addComponent(btnfind, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtcod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtnom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnfind)
                    .addComponent(jLabel4)
                    .addComponent(txttel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel1.add(jPanel3, java.awt.BorderLayout.NORTH);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1070, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(35, 35, 35)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(36, 36, 36)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 840, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void listadoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listadoMouseReleased
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            if (listado.getRowCount() > 0) {
                String pselstring = listado.getModel().getValueAt(listado.getSelectedRow(), 1).toString();
                csel = fincli.findCliente(pselstring);
            }
            this.cerrarDialogo();
        }
    }//GEN-LAST:event_listadoMouseReleased

    private void listadoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_listadoKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyChar() == KeyEvent.VK_ESCAPE) {
            cerrarDialogo();
        }
    }//GEN-LAST:event_listadoKeyReleased

    private void txtnombreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtnombreFocusGained
        // TODO add your handling code here:

    }//GEN-LAST:event_txtnombreFocusGained

    private void txtnombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyChar() == KeyEvent.VK_ESCAPE) {
            cerrarDialogo();
        }
    }//GEN-LAST:event_txtnombreKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (txtidcliente.getText().length() > 0 && txtnombre.getText().length() > 0) {
            Cliente cnew = new Cliente(txtidcliente.getText().toString());
            cnew.setNombre(txtnombre.getText());
            cnew.setDireccion(txtdir.getText());
            cnew.setRtncli(txtrtn.getText());
            cnew.setTelefono(txtidcliente1.getText());
            try {
                fincli.create(cnew);
                buscarClientes();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(panelFindcli.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (txtidcliente.getText().length() > 0 && txtnombre.getText().length() > 0) {
            Cliente cnew = fincli.findCliente(txtidcliente.getText().toString());
            cnew.setNombre(txtnombre.getText());
            cnew.setDireccion(txtdir.getText());
            cnew.setTelefono(txtidcliente1.getText());
            cnew.setRtncli(txtrtn.getText());
            try {
                fincli.edit(cnew);
                buscarClientes();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(panelFindcli.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtidclienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtidclienteFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidclienteFocusGained

    private void txtidclienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtidclienteKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyChar() == KeyEvent.VK_ESCAPE) {
            cerrarDialogo();
        }
    }//GEN-LAST:event_txtidclienteKeyReleased

    private void txtidcliente1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtidcliente1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidcliente1FocusGained

    private void txtidcliente1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtidcliente1KeyReleased
        // TODO add your handling code here:
        if (evt.getKeyChar() == KeyEvent.VK_ESCAPE) {
            cerrarDialogo();
        }
    }//GEN-LAST:event_txtidcliente1KeyReleased

    private void txtrtnFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtrtnFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtrtnFocusGained

    private void txtrtnKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrtnKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtrtnKeyReleased

    private void listado1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listado1MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_listado1MouseReleased

    private void txtcodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcodKeyPressed
        // TODO add your handling code here:
        // Global.cambiarFoco(this);
    }//GEN-LAST:event_txtcodKeyPressed

    private void txtcodKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcodKeyReleased
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            buscarClientes();
        }
        if (evt.getKeyChar() == KeyEvent.VK_ESCAPE) {
            cerrarDialogo();
        }
    }//GEN-LAST:event_txtcodKeyReleased

    private void txtnomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnomKeyPressed
        // TODO add your handling code here:
        //Global.cambiarFoco(this);
    }//GEN-LAST:event_txtnomKeyPressed

    private void txtnomKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnomKeyReleased
        // TODO add your handling code here:
        if (txtnom.getText().length() > 0) {
            if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
                ProductoFilnom();
            }

            lblnreg.setText("" + listado.getRowCount());
        } else {
            listado.setModel(bp.crearTabla());
            tamanioCol();
            lblnreg.setText("" + listado.getRowCount());
            System.out.println("" + listado.getRowCount());
        }
        if (evt.getKeyChar() == KeyEvent.VK_ESCAPE) {
            cerrarDialogo();
        }
    }//GEN-LAST:event_txtnomKeyReleased

    private void txttelKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttelKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyChar() == KeyEvent.VK_ESCAPE) {
            ProductoFilcellike();
        }

        if (evt.getKeyChar() == KeyEvent.VK_ESCAPE) {
            cerrarDialogo();
        }
    }//GEN-LAST:event_txttelKeyReleased

     public void cerrarDialogo() {
        // TODO add your handling code here:
      this.dispose();
       System.out.println("Salio del listado de Clientes");
    }
    
    private void jPanel1formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel1formKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyChar() == KeyEvent.VK_ESCAPE) {
            cerrarDialogo();
        }
    }//GEN-LAST:event_jPanel1formKeyReleased

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameClosed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnfind;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblnreg;
    private javax.swing.JLabel lblnreg1;
    private javax.swing.JTable listado;
    private javax.swing.JTable listado1;
    private javax.swing.JTextField txtcod;
    private javax.swing.JTextArea txtdir;
    private javax.swing.JTextField txtidcliente;
    private javax.swing.JTextField txtidcliente1;
    private javax.swing.JTextField txtnom;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txtrtn;
    private javax.swing.JTextField txttel;
    // End of variables declaration//GEN-END:variables
}
