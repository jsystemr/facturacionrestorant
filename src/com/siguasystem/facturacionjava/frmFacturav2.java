/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siguasystem.facturacionjava;

import com.siguasystem.modelo.Cliente;
import com.siguasystem.modelo.ClienteJpaController;
import com.siguasystem.modelo.Detallefactura;
import com.siguasystem.modelo.DetallefacturaJpaController;
import com.siguasystem.modelo.DetallefacturaPK;
import com.siguasystem.modelo.Empresa;
import com.siguasystem.modelo.EmpresaJpaController;
import com.siguasystem.modelo.Estadofact;
import com.siguasystem.modelo.Factura;
import com.siguasystem.modelo.FacturaJpaController;
import com.siguasystem.modelo.FacturaPK;
import com.siguasystem.modelo.Producto;
import com.siguasystem.modelo.Tipofactura;
import com.siguasystem.modelo.TipofacturaJpaController;
import com.siguasystem.modelo.Tiporden;
import com.siguasystem.modelo.TipordenJpaController;
import com.siguasystem.modelo.Usuario;
import com.siguasystem.modelo.UsuarioJpaController;
import com.siguasystem.modelos.exceptions.NonexistentEntityException;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.HeadlessException;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

/**
 *
 * @author juan
 */
public class frmFacturav2 extends javax.swing.JInternalFrame implements ActionListener {

    /**
     * Creates new form frmFactura
     */
    EntityManagerFactory emFac = Persistence.createEntityManagerFactory("Restorant");
    Tabladetfac dt = new Tabladetfac();
    frmDetalle frmdt;
    String factual = DateFormat.getDateInstance().format(new Date()).toString();
    DateFormat formatfac = new SimpleDateFormat("dd/MM/yyyy", Locale.US);//);
    detallefactura dtf = new detallefactura();
    articulo padd;
    Factura factura = new Factura();
    frmBusclientes frmbuscacli = new frmBusclientes();
    EjecutarReporte report = new EjecutarReporte();
    FacturaPK pkfact;//Toma valor si la factura existen
    DetallefacturaPK pkdfact;//Toma valor si el detalle existe
    Integer nuevafac = 0;
    Integer estado = 1;

    private List<Cliente> lscli = new ArrayList<Cliente>();
    private List<Detallefactura> lsdetfac = new ArrayList<Detallefactura>();
    private Cliente clisel;
    private ClienteJpaController fincli; //= new ClienteJpaController(Persistence.createEntityManagerFactory("Restorant"));
    public TipofacturaJpaController ltipfac;//= new TipofacturaJpaController(Persistence.createEntityManagerFactory("Restorant"));
    public TipordenJpaController ltipor;//= new TipordenJpaController(Persistence.createEntityManagerFactory("Restorant"));
    public EmpresaJpaController lempre;//=new EmpresaJpaController(Persistence.createEntityManagerFactory("Restorant"));
    public FacturaJpaController jpfac;
    public DetallefacturaJpaController jpdfac;
    public UsuarioJpaController jpusu;

    public frmFacturav2() {
        initComponents();
        //this.setAlwaysOnTop(false);
        detfac.setModel(dt.crearTabla());
        // setSize(this.getToolkit().getScreenSize().width, this.getToolkit().getScreenSize().width);
        txtfecha.setText(formatfac.format(new Date()));
        //Global.cambiarFoco(this);
        asginarFocoEnter();
        tamanioCol();
    }
    
     public void asginarFocoEnter() {
        cambiarFoco(cbotienda); cambiarFoco(cbotipofac);
        cambiarFoco(cbotipoorden);
        cambiarFoco(txtnombre);cambiarFoco(txtdir);
        cambiarFoco(txtidcli);
    }

      public void cambiarFoco(Component f) {
        Set forwardKeys = f.getFocusTraversalKeys(
                KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS);
        Set newForwardKeys = new HashSet(forwardKeys);
        newForwardKeys.add(KeyStroke.getKeyStroke(com.sun.glass.events.KeyEvent.VK_ENTER, 0));
        f.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
                newForwardKeys);
    }
    public void tamanioCol() {
        detfac.getColumnModel().getColumn(0).setPreferredWidth(10);
        detfac.getColumnModel().getColumn(1).setPreferredWidth(50);
        detfac.getColumnModel().getColumn(2).setPreferredWidth(250);
        detfac.getColumnModel().getColumn(3).setPreferredWidth(350);
        detfac.getColumnModel().getColumn(4).setPreferredWidth(100);
        detfac.getColumnModel().getColumn(5).setPreferredWidth(50);
        detfac.getColumnModel().getColumn(5).setPreferredWidth(100);
    }

    public frmFacturav2(FacturaPK pkf) {
        initComponents();
        //this.setAlwaysOnTop(false);
        detfac.setModel(dt.crearTabla());
        // setSize(this.getToolkit().getScreenSize().width, this.getToolkit().getScreenSize().width);
        txtfecha.setText(formatfac.format(pkf.getFecha()));
        txtnfact.setValue(pkf.getIdFactura());
        pkfact = pkf;
        btnsavefac.setText("Actualizar");
        Global.cambiarFoco(this);
        tamanioCol();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtnfact = new javax.swing.JSpinner();
        txtfecha = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        cbotienda = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        cbotipofac = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cbotipoorden = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtnombre = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txtidcli = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtdir = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        txtatendido = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btnaddet = new javax.swing.JButton();
        btnsavefac = new javax.swing.JButton();
        btnprintorden = new javax.swing.JButton();
        btnaddet3 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        txttot = new javax.swing.JFormattedTextField();
        btnprintfac = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        detfac = new javax.swing.JTable();
        txtusu = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Facturación");
        setPreferredSize(new java.awt.Dimension(1024, 770));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
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
                formInternalFrameOpened(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Encabezado Factura", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N
        jPanel1.setAutoscrolls(true);
        jPanel1.setPreferredSize(new java.awt.Dimension(850, 718));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Nro. Factura:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Fecha:");

        txtnfact.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtnfact.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        txtnfact.setEnabled(false);

        txtfecha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        txtfecha.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtfecha.setEnabled(false);
        txtfecha.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Tienda:");

        cbotienda.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Tipo de Factura:");

        cbotipofac.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbotipofac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cbotipofacKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Tipo de Orden:");

        cbotipoorden.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N
        jPanel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("ID:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Nombre:");

        txtnombre.setEditable(false);
        txtnombre.setBackground(new java.awt.Color(255, 255, 255));
        txtnombre.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtnombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtnombreFocusGained(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Direccion:");

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setText("Buscar Cliente");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtidcli.setAutoscrolls(true);
        txtidcli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidcliActionPerformed(evt);
            }
        });
        txtidcli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtidcliKeyReleased(evt);
            }
        });

        txtdir.setEditable(false);
        txtdir.setColumns(20);
        txtdir.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtdir.setLineWrap(true);
        txtdir.setRows(5);
        txtdir.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtdir.setEnabled(false);
        jScrollPane3.setViewportView(txtdir);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtidcli, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtnombre)
                        .addComponent(jLabel7)
                        .addComponent(txtidcli, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setText("Atendido por:");

        txtatendido.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnaddet.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnaddet.setText("+");
        btnaddet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddetActionPerformed(evt);
            }
        });

        btnsavefac.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnsavefac.setText("Guardar");
        btnsavefac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsavefacActionPerformed(evt);
            }
        });

        btnprintorden.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnprintorden.setText("Orden Pendiente");
        btnprintorden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprintordenActionPerformed(evt);
            }
        });

        btnaddet3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnaddet3.setText("Anular");
        btnaddet3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddet3ActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel17.setText("Total:");

        txttot.setEditable(false);
        txttot.setBackground(new java.awt.Color(255, 255, 255));
        txttot.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        txttot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txttot.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txttot.setEnabled(false);
        txttot.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        btnprintfac.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnprintfac.setText("Guardar e  Imprimir");
        btnprintfac.setActionCommand("Guardar e Imprimir");
        btnprintfac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprintfacActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(btnsavefac)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnprintfac, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnprintorden)
                .addGap(0, 0, 0)
                .addComponent(btnaddet3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txttot)
                .addContainerGap())
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(3, 3, 3)
                    .addComponent(btnaddet, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(708, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnprintorden, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnsavefac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnaddet3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnprintfac, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(1, 1, 1)))
                .addGap(6, 6, 6))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txttot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addComponent(btnaddet, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalle", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N
        jPanel2.setAutoscrolls(true);

        detfac.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        detfac.setModel(new javax.swing.table.DefaultTableModel(
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
        detfac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                detfacKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(detfac);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                .addContainerGap())
        );

        txtusu.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Usuario:");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setText("Hora:");

        jTextField7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtusu)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtatendido)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField7)
                        .addGap(13, 13, 13))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbotipofac, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtnfact)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addGap(1, 1, 1)
                                .addComponent(txtfecha, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbotienda, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(cbotipoorden, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(txtnfact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(cbotienda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbotipofac, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(cbotipoorden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)))
                .addGap(2, 2, 2)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtusu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(txtatendido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61))
        );

        jScrollPane2.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 996, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 683, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnaddet3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddet3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnaddet3ActionPerformed

    private void btnprintordenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprintordenActionPerformed
        imprimirOrden();
    }//GEN-LAST:event_btnprintordenActionPerformed

    private void btnsavefacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsavefacActionPerformed
        // TODO add your handling code here:
        try {
            if (guardarFactura(null)) {
                return;
            }
            /*Date date = formatfac.parse(txtfecha.getText());
            //JOptionPane.showMessageDialog(rootPane,"Factura->"+date.toString()+"->"+txtnfact.getValue()+"->"+LocalDate.now());
            JDialog jdet = new JDialog();
            Integer nfa=Integer.parseInt( txtnfact.getValue().toString());
            jdet.setContentPane(new panelPagofactura(nfa, date, new BigDecimal(txttot.getValue().toString())));
            jdet.setLocation(200, 200);
            jdet.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jdet.pack();
            jdet.setModal(true);
            jdet.setVisible(true);
            if (((panelPagofactura) jdet.getContentPane()).pago == true) {
               if (guardarFactura(((panelPagofactura) jdet.getContentPane()).efectivo)) {
                   return;
                }
           } else {
             guardarFacturaOpen(((panelPagofactura) jdet.getContentPane()).efectivo);
            }
            this.dispose();
             */
        } catch (Exception e) {
            System.out.println("Error-" + e.getMessage());
        }
    }//GEN-LAST:event_btnsavefacActionPerformed

    public boolean guardarFactura(BigDecimal efe) throws ParseException, Exception, NumberFormatException, HeadlessException {
        try {
            boolean g = false;
            Date date = formatfac.parse(txtfecha.getText());
            Factura f1 = jpfac.findFactura(new FacturaPK(Integer.parseInt(txtnfact.getValue().toString()), date));
            if (btnsavefac.getText().equals("Guardar")) {
                txtnfact.setValue(ultimaFact("factura") + 1);
            }
            if (dtf.totalarticulos() == 0) {
                JOptionPane.showMessageDialog(this, "Intente ingresando un producto a la lista!", "La factura no tiene Detalle!", JOptionPane.WARNING_MESSAGE);
                return true;
            }
            /*if (jpusu.findUsuario(txtusu.getText().toString()).getNivel()==1) {
                
            }else{
            
            }*/
            //Condicion para evaluar el nivel del usuario
            if (1 == 1) {
                if (btnsavefac.getText().equals("Guardar")) {
                    saveFactura(date, efe);
                    //Si la factura se almaceno correctamente se imprime la factura
                    btnsavefac.setEnabled(false);
                    btnprintfac.setEnabled(false);
                    g = true;
                } else {
                    updateFactura(date, efe, f1);
                    //Si la factura se almaceno correctamente se imprime la factura
                    btnsavefac.setEnabled(false);
                    btnprintfac.setEnabled(false);
                    g = true;
                }
            } else {
                if (btnsavefac.getText().equals("Guardar")) {
                    saveFactura(date, efe);
                    //Si la factura se almaceno correctamente se imprime la factura
                    btnsavefac.setEnabled(false);
                    btnprintfac.setEnabled(false);
                    g = true;
                } else {
                    JOptionPane.showMessageDialog(this, "La factura con Nro." + txtnfact.getValue().toString() + " ya Existe!, Intente con otro numero!", "La factura ya existe!", JOptionPane.WARNING_MESSAGE);
                }
            }
            imprimirOrden();
            if (g) {
                imprimirFactura();
            }
            this.dispose();
        } catch (Exception e) {
            System.out.println("Error->" + e.getMessage());
        }

        //Desea crear una nueva factura
        /*if (JOptionPane.showInternalConfirmDialog(this, "Desea crear una nueva factura?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
        System.out.println("Factura Nueva Creada!");
        }*/
        return false;
    }

    public void saveFactura(Date date, BigDecimal efe) throws NumberFormatException, Exception {

        pkfact = new FacturaPK(Integer.parseInt(txtnfact.getValue().toString()), date);
        factura.setFacturaPK(pkfact);
        factura.setEfectivo(efe);
        Tipofactura seltpf = ((Tipofactura) cbotipofac.getSelectedItem());
        Empresa selemp = (Empresa) cbotienda.getSelectedItem();
        Tiporden seltipo = (Tiporden) cbotipoorden.getSelectedItem();
        factura.setCliente(clisel);
        if (efe == null) {
            factura.setEstadofact(new Estadofact(1));
        } else {
            factura.setEstadofact(new Estadofact(2));
        }
        factura.setEmpresa(selemp);
        factura.setTipofact(seltpf);
        factura.setIdorden(seltipo);
        factura.setUsuario(new Usuario("juan"));
        factura.setTotalfac(new BigDecimal(dtf.totalarticulos()));
        //factura.setEfectivo(new BigDecimal(Double.parseDouble(txt)));
        System.out.println(factura);
        jpfac.create(factura);
        //JOptionPane.showMessageDialog(rootPane, "Factura Almacenada! ->" + date);
        for (articulo dtfac : dtf.getLParticulos()) {
            Detallefactura dtftmp = new Detallefactura();
            dtftmp.setDetallefacturaPK(new DetallefacturaPK(dtfac.getId(), pkfact.getIdFactura(), pkfact.getFecha()));
            dtftmp.setProducto(dtfac.getCodigo());
            dtftmp.setNomproducto(dtfac.getNombre());
            dtftmp.setCantidad((int) dtfac.getCantidad());
            dtftmp.setPrecio(dtfac.getPrecio());
            dtftmp.setComentarios(dtfac.getComentarios());
            dtftmp.setDescuento(new BigDecimal(dtfac.getDescuento()));
            dtftmp.setIsv(new BigDecimal(dtfac.getValisv()));
            //Estado controla si la orden ya fue impresa
            if (dtfac.getEstado().equals("1")) {
                dtftmp.setStadodet(1);
            } else {
                dtftmp.setStadodet(0);
            }
            jpdfac.create(dtftmp);
            System.out.println(dtftmp);
        }
    }

    public void updateFactura(Date date, BigDecimal efe, Factura f1) throws NumberFormatException, Exception {
        //En caso de ser modificada la factura y agregar un producto el total se calcula mejor al final
        //pkfact = new FacturaPK(Integer.parseInt(txtnfact.getValue().toString()), date);
        factura.setFacturaPK(pkfact);
        factura.setEfectivo(efe);
        Tipofactura seltpf = ((Tipofactura) cbotipofac.getSelectedItem());
        Empresa selemp = (Empresa) cbotienda.getSelectedItem();
        Tiporden seltipo = (Tiporden) cbotipoorden.getSelectedItem();
        clisel = f1.getCliente();
        factura.setCliente(clisel);
        if (efe == null) {
            factura.setEstadofact(new Estadofact(1));
        } else {
            factura.setEstadofact(new Estadofact(2));
        }
        factura.setEmpresa(selemp);
        factura.setTipofact(seltpf);
        factura.setIdorden(seltipo);
        factura.setUsuario(new Usuario("juan"));
        factura.setTotalfac(new BigDecimal(dtf.totalarticulosISV()));
        //factura.setEfectivo(new BigDecimal(Double.parseDouble(txt)));
        System.out.println(factura);
        jpfac.edit(factura);
        //JOptionPane.showMessageDialog(rootPane, "Factura Almacenada! ->" + date);
        for (articulo dtfac : dtf.getLParticulos()) {
            Detallefactura dtftmp = new Detallefactura();
            dtftmp.setDetallefacturaPK(new DetallefacturaPK(dtfac.getId(), pkfact.getIdFactura(), pkfact.getFecha()));
            dtftmp.setProducto(dtfac.getCodigo());
            dtftmp.setNomproducto(dtfac.getNombre());
            dtftmp.setCantidad((int) dtfac.getCantidad());
            dtftmp.setPrecio(dtfac.getPrecio());
            dtftmp.setComentarios(dtfac.getComentarios());
            dtftmp.setDescuento(new BigDecimal(dtfac.getDescuento()));
            dtftmp.setIsv(new BigDecimal(dtfac.getValisv()));
            //Estado controla si la orden ya fue impresa
            if (dtfac.getEstado().equals("1")) {
                dtftmp.setStadodet(1);
            } else {
                dtftmp.setStadodet(0);
            }
            jpdfac.edit(dtftmp);
            System.out.println(dtftmp);
        }
    }

    /**
     * *
     * Funcion para almacenar las facturas como abiertas o pendientes de cobrar
     */
    public void guardarFacturaOpen(BigDecimal efe) throws ParseException, Exception, NumberFormatException, HeadlessException {
        try {
            Date date = formatfac.parse(txtfecha.getText());
            if (btnsavefac.getText().equals("Guardar")) {
                txtnfact.setValue(ultimaFact("factura") + 1);
            }
            pkfact = new FacturaPK(Integer.parseInt(txtnfact.getValue().toString()), date);
            Factura f1 = jpfac.findFactura(pkfact);
            //jpfac.findFactura(new FacturaPK(Integer.parseInt(txtnfact.getValue().toString()), date));
            if (dtf.totalarticulos() == 0) {
                JOptionPane.showMessageDialog(this, "Intente ingresando un producto a la lista!", "La factura no tiene Detalle!", JOptionPane.WARNING_MESSAGE);
            }
            //Valida que La factura se almacene con un numero que no exista y que la factura tenga productos en su detalle.
            //Condicion para evaluar el nivel del usuario
            if (1 == 1) {
                if (btnsavefac.getText().equals("Guardar")) {
                    saveFactura(date, efe);
                    //Si la factura se almaceno correctamente se imprime la factura
                    btnsavefac.setEnabled(false);
                    btnprintfac.setEnabled(false);
                    if (efe != null) {
                        imprimirFactura();//No ingreso efectivo}
                    }
                } else {
                    //Envia los parametros de la factura y los cambios para ser actualizados
                    updateFactura(date, efe, f1);
                    //Si la factura se almaceno correctamente se imprime la factura
                    btnsavefac.setEnabled(false);
                    btnprintfac.setEnabled(false);
                    if (efe != null) {
                        imprimirFactura();//No ingreso efectivo}
                    }
                }
            } else {
                if (btnsavefac.getText().equals("Guardar")) {
                    saveFactura(date, efe);
                    //Si la factura se almaceno correctamente se imprime la factura
                    btnsavefac.setEnabled(false);
                    btnprintfac.setEnabled(false);
                    imprimirFactura();
                } else {
                    JOptionPane.showMessageDialog(this, "La factura con Nro." + txtnfact.getValue().toString() + " ya Existe!, Intente con otro numero!", "La factura ya existe!", JOptionPane.WARNING_MESSAGE);
                }
            }
            imprimirOrden();
        } catch (Exception e) {
            System.out.println("Error->" + e.getMessage());
        }
    }

    private void btnaddetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddetActionPerformed
        // TODO add your handling code here:
        if (estado == 1) {
            if (!txtnombre.getText().isEmpty()) {
                JDialog jdet = new JDialog();
                jdet.setContentPane(new panelDetV2());
                jdet.setLocation(200, 200);
                jdet.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                jdet.pack();
                jdet.setModal(true);
                jdet.setVisible(true);
                if (((panelDetV2) jdet.getContentPane()).addpro == 1) {
                    System.out.println("Producto Agregado");
                    addproTableV2(jdet);
                } else {
                    JOptionPane.showMessageDialog(rootPane, "La factura ya esta cerrada!", "Consulte al Administrador.", JOptionPane.WARNING_MESSAGE);
                
            }}else {
                    JOptionPane.showMessageDialog(rootPane, "Debe ingresar un Cliente", "El Cliente no ha sido Ingresado!", JOptionPane.WARNING_MESSAGE);
                }

        }
    }//GEN-LAST:event_btnaddetActionPerformed

    public void addproTable(JDialog jdet) {
        padd = ((panelDet) jdet.getContentPane()).producto;
        dtf.addPArticulos(padd);
        txttot.setValue(dtf.totalarticulosISV());
        try {
            DefaultTableModel temp = (DefaultTableModel) detfac.getModel();
            Object nuevo[] = {padd.getId(), padd.getCodigo(), padd.getNombre(), padd.getComentarios(), String.format("%,.2f", padd.getCantidad()),
                String.format("%,.2f", padd.getPrecio()), String.format("%,.2f", padd.getDescuento()), String.format("%,.2f", padd.getValisv()), String.format("%,.2f", padd.getSubtotal())};
            temp.addRow(nuevo);
        } catch (Exception e) {
            System.out.println("" + e.getMessage());
        }
    }

    public void addproTableV2(JDialog jdet) {
        padd = ((panelDetV2) jdet.getContentPane()).producto;
        dtf.addPArticulos(padd);
        txttot.setValue(dtf.totalarticulosISV());
        try {
            DefaultTableModel temp = (DefaultTableModel) detfac.getModel();
            Object nuevo[] = {padd.getId(), padd.getCodigo(), padd.getNombre(), padd.getComentarios(), String.format("%,.2f", padd.getCantidad()),
                String.format("%,.2f", padd.getPrecio()), String.format("%,.2f", padd.getDescuento()), String.format("%,.2f", padd.getValisv()), String.format("%,.2f", padd.getSubtotal())};
            temp.addRow(nuevo);
        } catch (Exception e) {
            System.out.println("" + e.getMessage());
        }
    }

    public void addproTable2(List<Detallefactura> ldet) {
        DefaultTableModel temp = (DefaultTableModel) detfac.getModel();
        for (Detallefactura deta : ldet) {
            articulo padd = new articulo();
            padd.setId(deta.getDetallefacturaPK().getNrolinea());
            padd.setCodigo(deta.getProducto());
            padd.setNombre(deta.getNomproducto());
            padd.setPrecio(deta.getPrecio());
            padd.setCantidad(deta.getCantidad());
            padd.setComentarios(deta.getComentarios());
            padd.setDescuento(deta.getDescuento().doubleValue());
            padd.setValisv(deta.getIsv().doubleValue());
            padd.setEstado(deta.getStadodet().toString());
            dtf.addPArticulos(padd);
            try {
                Object nuevo[] = {padd.getId(), padd.getCodigo(), padd.getNombre(), padd.getComentarios(), String.format("%,.2f", padd.getCantidad()),
                    String.format("%,.2f", padd.getPrecio()), String.format("%,.2f", padd.getDescuento()), String.format("%,.2f", padd.getValisv()), String.format("%,.2f", padd.getSubtotal())};
                temp.addRow(nuevo);
            } catch (Exception e) {
                System.out.println("" + e.getMessage());
            }
        }
        txttot.setValue(dtf.totalarticulosISV());
    }

    private void cbotipofacKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbotipofacKeyReleased
        //MostrarDatoscel(txtidcli);
    }//GEN-LAST:event_cbotipofacKeyReleased

    private void txtidcliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtidcliKeyReleased
        // TODO add your handling code here:
        switch (evt.getKeyCode()) {
            case com.sun.glass.events.KeyEvent.VK_F4:
                abrirBuscacli();
                break;
            default:
                System.out.println("");
                ;
        }
    }//GEN-LAST:event_txtidcliKeyReleased

    private void txtidcliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidcliActionPerformed
        // TODO add your handling code here:
        if (txtidcli.getSelectedIndex() > 0) {
            clisel = (Cliente) txtidcli.getSelectedItem();
            txtnombre.setText(clisel.getNombre());
            txtdir.setText(clisel.getDireccion());
        }
    }//GEN-LAST:event_txtidcliActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        try {
            fincli = new ClienteJpaController(emFac);
            ltipfac = new TipofacturaJpaController(emFac);
            ltipor = new TipordenJpaController(emFac);
            lempre = new EmpresaJpaController(emFac);
            jpfac = new FacturaJpaController(emFac);
            jpdfac = new DetallefacturaJpaController(emFac);
            jpusu = new UsuarioJpaController(emFac);

            MostrarDatoscel(txtidcli);
            //Carga los Combobox
            cbotipoorden.setModel(this.ObtenerListaTipoor());
            cbotipofac.setModel(this.ObtenerListaTipofac());
            cbotienda.setModel(this.ObtenerListaEmp());
            if (nuevafac == 0) {
                txtnfact.setValue(ultimaFact("factura") + 1);
            } else {
                txtnfact.setValue(nuevafac);
                txtnfact.setEnabled(false);
                //Carga la factura existente
                factura = jpfac.findFactura(pkfact);
                //Carga el detalle existente
                lsdetfac = jpdfac.findDetallefacturaEntities2(new DetallefacturaPK(pkfact.getIdFactura(), pkfact.getFecha()));
                cbotienda.setSelectedItem(factura.getEmpresa());
                cbotipofac.setSelectedItem(factura.getTipofact());
                cbotipoorden.setSelectedItem(factura.getIdorden());
                txtidcli.setSelectedItem(factura.getCliente());
                txtnombre.setText(factura.getCliente().getNombre());
                txtdir.setText(factura.getCliente().getDireccion());
                addproTable2(lsdetfac);
            }
        } catch (Exception e) {
            System.out.println("Error->" + e.getMessage());
        }
    }//GEN-LAST:event_formInternalFrameOpened

    private Integer ultimaFact(String f) {
        Integer x = 0;
        try {
            Query qsql = fincli.getEntityManager().createNativeQuery("select * from " + f + " c where idfactura=(select max(idfactura) from " + f + ")", Factura.class);//qsql.setParameter("n", txtnom.getText());
            Factura f2 = (Factura) qsql.getSingleResult();
            x = f2.getFacturaPK().getIdFactura();
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return x;
    }


    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        // TODO add your handling code here:
        switch (evt.getKeyCode()) {
            case com.sun.glass.events.KeyEvent.VK_ESCAPE:
                this.dispose();
                break;
            default:
                System.out.println("");
                ;
        }
    }//GEN-LAST:event_formKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {
            JDialog jdet = new JDialog();
            jdet.setContentPane(new panelFindcli());
            jdet.setLocation(200, 200);
            jdet.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jdet.pack();
            jdet.setModal(true);
            jdet.setVisible(true);
            if (((panelFindcli) jdet.getContentPane()).csel != null) {
                clisel = ((panelFindcli) jdet.getContentPane()).csel;
                DefaultComboBoxModel modelo = new DefaultComboBoxModel();
                modelo.addElement(clisel);
                txtidcli.setModel(modelo);
                txtnombre.setText(clisel.getNombre());
                txtdir.setText(clisel.getDireccion());
            }
        } catch (Exception e) {
            System.out.println("Error->" + e.getMessage());
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void detfacKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_detfacKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            if (JOptionPane.showInternalConfirmDialog(this, "Esata seguro quitar la fila", "La fila sera elimnada!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                int f = detfac.getSelectedRow();//Integer.parseInt(detfac.getModel().getValueAt(detfac.getSelectedRow(),0).toString());
                dtf.delPArticulos(f);
                DefaultTableModel temp = (DefaultTableModel) detfac.getModel();
                //Validacion de acceso usuario Administrador
                if (1 == 1) {
                    if (nuevafac > 0) {
                        try {
                            Integer lin = Integer.parseInt(detfac.getModel().getValueAt(f, 0).toString());
                            jpdfac.destroy(new DetallefacturaPK(lin, pkfact.getIdFactura(), pkfact.getFecha()));
                            try {
                                guardarFacturaOpen(null);
                            } catch (Exception ex) {
                                Logger.getLogger(frmFacturav2.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } catch (NonexistentEntityException ex) {
                            Logger.getLogger(frmFacturav2.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

                temp.removeRow(detfac.getSelectedRow());
                txttot.setValue(dtf.totalarticulos());

            }

        }
    }//GEN-LAST:event_detfacKeyReleased

    private void btnprintfacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprintfacActionPerformed
        try {
            Date date = formatfac.parse(txtfecha.getText());
            //JOptionPane.showMessageDialog(rootPane,"Factura->"+date.toString()+"->"+txtnfact.getValue()+"->"+LocalDate.now());
            JDialog jdet = new JDialog();
            Integer nfa = Integer.parseInt(txtnfact.getValue().toString());
            jdet.setContentPane(new panelPagofactura(nfa, date, new BigDecimal(txttot.getValue().toString())));
            jdet.setLocation(200, 200);
            jdet.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jdet.pack();
            jdet.setModal(true);
            jdet.setVisible(true);
            if (((panelPagofactura) jdet.getContentPane()).pago == true) {
                if (guardarFactura(((panelPagofactura) jdet.getContentPane()).efectivo)) {
                    return;
                }
            } else {
                guardarFacturaOpen(((panelPagofactura) jdet.getContentPane()).efectivo);
            }
            this.dispose();
            //imprimirFactura();
        } catch (Exception e) {
            System.out.println("Error->" + e.getMessage());
        }

    }//GEN-LAST:event_btnprintfacActionPerformed

    private void txtnombreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtnombreFocusGained
        // TODO add your handling code here:
        if (((Cliente) txtidcli.getSelectedItem()) != null) {
            Cliente c = fincli.findCliente(((Cliente) txtidcli.getSelectedItem()).getIdCliente().toString());
            txtnombre.setText(c.getNombre());
        }

    }//GEN-LAST:event_txtnombreFocusGained

    public void imprimirFactura() {
        // TODO add your handling code here:
        try {
            Factura f1;
            if (nuevafac == 0) {
                Date date = formatfac.parse(txtfecha.getText());
                f1 = jpfac.findFactura(new FacturaPK(Integer.parseInt(txtnfact.getValue().toString()), date));
            } else {
                f1 = jpfac.findFactura(pkfact);
            }
            if (f1 != null) {
                if (f1.getEfectivo() != null) {
                    Integer id = Integer.parseInt(txtnfact.getValue().toString());
                    if (id > 0) {
                        report.startReportprint("" + id);
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "La factura no ha sido cobrada!, presione \"Guardar\" para Cobrar!", "Facturacion!", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception e) {
            System.out.println("Error->" + e.getMessage());
        }
    }

    public void imprimirOrden()  {
        // TODO add your handling code here:
        Integer id = Integer.parseInt(txtnfact.getValue().toString());
        if (id > 0) {
            if (jpdfac.getDetestadoCount(id) > 0) {
                report.startOrdenCocinaprint("" + id);
                System.out.println("" + jpdfac.actualizarEstadodet(id));
            } else {
                JOptionPane.showMessageDialog(rootPane, "No hay Ordenes de Cocina pendientes!", "Orden de Cocina", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public DefaultComboBoxModel ObtenerListaTipofac() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        try {
            for (Tipofactura it : ltipfac.findTipofacturaEntities()) {
                Tipofactura p = it;
                modelo.addElement(p);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error ");
            System.out.println("Coneccion incorrecta: " + ex);
        }
        return modelo;
    }

    public DefaultComboBoxModel ObtenerListaTipoor() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        try {
            for (Tiporden it : ltipor.findTipordenEntities()) {
                Tiporden p = it;
                modelo.addElement(p);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error ");
            System.out.println("Coneccion incorrecta: " + ex);
        }
        return modelo;
    }

    public DefaultComboBoxModel ObtenerListaEmp() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        try {
            for (Empresa it : lempre.findEmpresaEntities()) {
                Empresa p = it;
                modelo.addElement(p);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error ");
            System.out.println("Coneccion incorrecta: " + ex);
        }
        return modelo;
    }

    public void MostrarDatoscel(final JComboBox combobox) {
        combobox.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                String cadenaEscrita = combobox.getEditor().getItem().toString();
                if (evt.getKeyCode() >= 65 && evt.getKeyCode() <= 90 || evt.getKeyCode() >= 96 && evt.getKeyCode() <= 105 || evt.getKeyCode() == 8) {
                    combobox.setModel(ObtenerListacli(cadenaEscrita, "cel"));
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
                }
            }
        });
        if (txtidcli.getModel().getSize() > 0) {
            if (txtidcli.getSelectedItem().toString() != null) {
                txtnombre.setText(((Cliente) txtidcli.getSelectedItem()).getNombre());
            }
        }
    }

    public void MostrarDatosID(final JComboBox combobox) {
        combobox.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                String cadenaEscrita = combobox.getEditor().getItem().toString();
                if (evt.getKeyCode() >= 65 && evt.getKeyCode() <= 90 || evt.getKeyCode() >= 96 && evt.getKeyCode() <= 105 || evt.getKeyCode() == 8) {
                    combobox.setModel(ObtenerListacli(cadenaEscrita, "id"));
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
                }
            }
        });
        if (txtidcli.getModel().getSize() > 0) {
            if (txtidcli.getSelectedItem().toString() != null) {
                txtnombre.setText(((Cliente) txtidcli.getSelectedItem()).getNombre());
            }
        }
    }

    public DefaultComboBoxModel ObtenerListacli(String cadena, String op) {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        try {
            if (op.equals("cel")) {
                for (Cliente it : Filcelclike(cadena)) {
                    Cliente p = it;
                    modelo.addElement(p);
                }
            } else {
                for (Cliente it : Filcodlike(cadena)) {
                    Cliente p = it;
                    modelo.addElement(p);
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error ");
            System.out.println("Coneccion incorrecta: " + ex);
        }
        return modelo;
    }

    public List<Cliente> Filcodlike(String txtfil) {
        List<Cliente> lcli = new ArrayList<Cliente>();
        try {
            String pt = txtfil.toLowerCase();
            Query qsql = fincli.getEntityManager().createNativeQuery("select * from cliente c where LOWER(c.idcliente) LIKE \'" + pt + "%\'", Cliente.class);//qsql.setParameter("n", txtnom.getText());
            lcli = qsql.getResultList();
            int i = 1;
            for (Iterator<Cliente> iterator = lcli.iterator(); iterator.hasNext();) {
                Cliente p = iterator.next();
                lcli.add(p);
            }
            //lscli.clear();
            lscli = lcli;
        } catch (Exception e) {
        }
        return lcli;
    }

    public List<Cliente> Filcelclike(String cel) {

        List<Cliente> lcli = new ArrayList<Cliente>();
        try {
            String pt = cel.toLowerCase();
            Query qsql = fincli.getEntityManager().createNativeQuery("select * from cliente c where LOWER(c.telefono) LIKE \'" + pt + "%\' or  LOWER(c.celular) LIKE \'" + pt + "%\' or LOWER(c.idcliente) LIKE \'" + pt + "%\'", Cliente.class);//qsql.setParameter("n", txtnom.getText());
            lcli = qsql.getResultList();
            int i = 1;
            for (Iterator<Cliente> iterator = lcli.iterator(); iterator.hasNext();) {
                Cliente p = iterator.next();
                lcli.add(p);
            }
            // lscli.clear();
            lscli = lcli;
        } catch (Exception e) {
        }
        return lcli;
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
            java.util.logging.Logger.getLogger(frmFacturav2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmFacturav2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmFacturav2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmFacturav2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new frmFacturav2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnaddet;
    private javax.swing.JButton btnaddet3;
    public javax.swing.JButton btnprintfac;
    private javax.swing.JButton btnprintorden;
    public javax.swing.JButton btnsavefac;
    private javax.swing.JComboBox<String> cbotienda;
    private javax.swing.JComboBox<String> cbotipofac;
    private javax.swing.JComboBox<String> cbotipoorden;
    private javax.swing.JTable detfac;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField txtatendido;
    private javax.swing.JTextArea txtdir;
    private javax.swing.JFormattedTextField txtfecha;
    private javax.swing.JComboBox<String> txtidcli;
    private javax.swing.JSpinner txtnfact;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JFormattedTextField txttot;
    private javax.swing.JTextField txtusu;
    // End of variables declaration//GEN-END:variables

    private void abrirBuscacli() {
        ((Menuprincipal) this.getParent()).abrirBuscacli();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
