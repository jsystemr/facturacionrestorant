/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siguasystem.facturacionjava;

import com.siguasystem.modelo.*;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.HeadlessException;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

/**
 *
 * @author juan
 */
public class frmFacturav61 extends javax.swing.JInternalFrame implements ActionListener {

    /**
     * Creates new form frmFactura
     */
    //Conexion JPA
    EntityManagerFactory emFac = Persistence.createEntityManagerFactory("Restorant");
    Tabladetfac dt = new Tabladetfac();
    frmDetalle frmdt;
    String factual = DateFormat.getDateInstance().format(new Date()).toString();
    DateFormat formatfac = new SimpleDateFormat("dd/MM/yyyy", Locale.US);//);
    DateFormat formatfac2 = new SimpleDateFormat("dd-MM-yyyy", Locale.US);//);
    detallefactura dtf = new detallefactura();
    articulo padd;
    Factura factura = new Factura();
    //frmBusclientes frmbuscacli=null; //= new frmBusclientes();
    EjecutarReporte report = new EjecutarReporte();
    FacturaPK pkfact;//Toma valor si la factura existen
    DetallefacturaPK pkdfact;//Toma valor si el detalle existe
    Integer nuevafac = 0;
    Integer estado = 1;
    Integer buspro = 0;
    private Double subtot = 0.0;
    private Double costopro = 0.0;
    String alerta = "";
    //Almacena el anio de la identidad para saber si el cliente es
    //Adulto mayor y aplicar el descuento automaticamente.
    String anionacimientocli = "0";
    //Adulto MAyor descuento de 25%
    Integer admayor = 0;
    private Correlativossar lcorre = null; //= new Correlativossar();
    private List<Cliente> lscli = new ArrayList<Cliente>();
    private List<Detallefactura> lsdetfac = new ArrayList<Detallefactura>();
    private List<Usuario> lsusuario;
    private Cliente clisel;
    private Producto prosel;
    private ClienteJpaController fincli; //= new ClienteJpaController(Persistence.createEntityManagerFactory("Restorant"));
    public TipofacturaJpaController ltipfac;//= new TipofacturaJpaController(Persistence.createEntityManagerFactory("Restorant"));
    public EmpresaJpaController lempre;//=new EmpresaJpaController(Persistence.createEntityManagerFactory("Restorant"));
    public FacturaJpaController jpfac;
    public DetallefacturaJpaController jpdfac;
    public UsuarioJpaController jpusu;
    private ProductoJpaController finpro; //= new ProductoJpaController(emFac);
    private CorrelativossarJpaController pcorrela;// = new CorrelativossarJpaController(emFac);
    private Integer nkardex = 0;
    Menuprincipal menup;
    Double totAlq = 0.00;
    Double valAlq = 0.00;
      private int ped=0;
    
    public frmFacturav61() {
        initComponents();
        detfac.setModel(dt.crearTabla());
        // setSize(this.getToolkit().getScreenSize().width, this.getToolkit().getScreenSize().width);
        txtfecha.setDate(new Date());
        //fechafac.setDateToToday();
        cambiarFoco(this);
        tamanioCol();
        txtcan.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (Double.parseDouble(txtcan.getValue().toString()) > 0) {
                    calcSubtotal();
                }
            }
        });
        jButton1.setEnabled(false);
    }
    
    public void cambiarFoco(JInternalFrame f) {
        Set forwardKeys = f.getFocusTraversalKeys(
                KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS);
        Set newForwardKeys = new HashSet(forwardKeys);
        newForwardKeys.add(KeyStroke.getKeyStroke(com.sun.glass.events.KeyEvent.VK_ENTER, 0));
        f.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
                newForwardKeys);
    }
    
    public void calcSubtotal() throws NumberFormatException {
        //txtsubtot.setValue(Double.parseDouble(txtcan.getValue().toString()) * Double.parseDouble(txtprecio.getValue().toString()));
        subtot = (Double.parseDouble(txtprecio.getValue().toString()) + Double.parseDouble(txtisv.getValue().toString())) * Double.parseDouble(txtcan.getValue().toString()) - Double.parseDouble(txtdesc.getValue().toString());
        BigDecimal stexacto=new BigDecimal(subtot).round(MathContext.DECIMAL32);
        txtsubtot.setValue(stexacto.doubleValue());
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
    
    public frmFacturav61(FacturaPK pkf) {
        initComponents();
        detfac.setModel(dt.crearTabla());
        System.out.println("Fecha sin formato->" + pkf.getFecha());
        System.out.println("Fecha Local->" + pkf.getFecha().toLocaleString().substring(0, 10));
        System.out.println("Fecha GMT->" + pkf.getFecha().toLocaleString());
        txtfecha.setDate(pkf.getFecha());
        String fres[] = pkf.getFecha().toLocaleString().substring(0, 10).split("-");
        LocalDate date = LocalDate.parse(fres[2] + "-" + fres[0] + "-" + fres[1]);
        //fechafac.setDate(date);
        txtnfact.setValue(pkf.getIdFactura());
        pkfact = pkf;
        System.out.println("");
        btnsavefac.setText("Actualizar");
        cambiarFoco(this);
        tamanioCol();
        txtcan.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (Double.parseDouble(txtcan.getValue().toString()) > 0) {
                    calcSubtotal();
                }
            }
        });
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
        jLabel3 = new javax.swing.JLabel();
        cbotienda = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        cbotipofac = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtnombre = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txtidcli = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtdir = new javax.swing.JTextArea();
        txtcodcli = new javax.swing.JTextField();
        txteditnom = new javax.swing.JCheckBox();
        jLabel15 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnsavefac = new javax.swing.JButton();
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
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtidpro = new javax.swing.JTextField();
        txtnompro = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtcan = new javax.swing.JSpinner();
        jLabel12 = new javax.swing.JLabel();
        txtprecio = new javax.swing.JSpinner();
        jLabel13 = new javax.swing.JLabel();
        txtdesc = new javax.swing.JSpinner();
        jLabel14 = new javax.swing.JLabel();
        txtisv = new javax.swing.JSpinner();
        jLabel18 = new javax.swing.JLabel();
        txtsubtot = new javax.swing.JSpinner();
        txtcomen = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        btnaddet = new javax.swing.JButton();
        txtdesmax = new javax.swing.JLabel();
        Cerrar = new javax.swing.JButton();
        txtatendido = new javax.swing.JComboBox<>();
        txtfecha = new com.toedter.calendar.JDateChooser();

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
                formInternalFrameClosing(evt);
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
        jPanel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jPanel1KeyReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Nro. Factura:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Fecha:");

        txtnfact.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtnfact.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        txtnfact.setEnabled(false);

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

        txtcodcli.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtcodcli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtcodcliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtcodcliFocusLost(evt);
            }
        });
        txtcodcli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcodcliKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcodcliKeyTyped(evt);
            }
        });

        txteditnom.setText("editar");
        txteditnom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txteditnomActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(137, 137, 137)
                                .addComponent(txtidcli, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(txtcodcli)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txteditnom))
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtnombre)
                    .addComponent(jLabel7)
                    .addComponent(txtidcli, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(txtcodcli)
                    .addComponent(jLabel6)
                    .addComponent(txteditnom))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setText("Atendido por:");

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnsavefac.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnsavefac.setText("Guardar");
        btnsavefac.setEnabled(false);
        btnsavefac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsavefacActionPerformed(evt);
            }
        });

        btnaddet3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnaddet3.setText("Anular");
        btnaddet3.setEnabled(false);
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
                .addContainerGap()
                .addComponent(btnsavefac, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnprintfac, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnaddet3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txttot, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txttot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnaddet3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnsavefac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnprintfac, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(7, 7, 7))
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
        );

        txtusu.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Usuario:");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setText("Hora:");

        jTextField7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Productos"));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Codigo del Producto:");

        txtidpro.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txtidpro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtidproFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtidproFocusLost(evt);
            }
        });
        txtidpro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidproActionPerformed(evt);
            }
        });
        txtidpro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtidproKeyReleased(evt);
            }
        });

        txtnompro.setEditable(false);
        txtnompro.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("Cantidad:");

        txtcan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtcan.setModel(new javax.swing.SpinnerNumberModel(1, 0, null, 1));
        txtcan.setEditor(new javax.swing.JSpinner.NumberEditor(txtcan, ""));
        txtcan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtcanFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtcanFocusLost(evt);
            }
        });
        txtcan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcanKeyReleased(evt);
            }
        });
        txtcan.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                txtcanVetoableChange(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setText("Precio:");

        txtprecio.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtprecio.setModel(new javax.swing.SpinnerNumberModel(0.0f, 0.0f, null, 0.01f));
        txtprecio.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtprecio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtprecioFocusLost(evt);
            }
        });
        txtprecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtprecioKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtprecioKeyReleased(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setText("Descuento:");

        txtdesc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtdesc.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(0.01f)));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setText("Isv:");

        txtisv.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtisv.setModel(new javax.swing.SpinnerNumberModel(0.0f, null, null, 0.001f));
        txtisv.setEnabled(false);

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel18.setText("Subtotal:");

        txtsubtot.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtsubtot.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(0.001f)));
        txtsubtot.setEnabled(false);

        txtcomen.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtcomen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtcomenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtcomenFocusLost(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel19.setText("Comentarios:");

        btnaddet.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnaddet.setText("+");
        btnaddet.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnaddetFocusGained(evt);
            }
        });
        btnaddet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddetActionPerformed(evt);
            }
        });

        txtdesmax.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        txtdesmax.setForeground(new java.awt.Color(255, 102, 0));
        txtdesmax.setText("Descuento Maximo:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(31, 31, 31)
                        .addComponent(txtprecio, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtdesc, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtisv, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtdesmax)
                        .addGap(44, 44, 44)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtsubtot, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtidpro, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtnompro, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcan, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtcomen, javax.swing.GroupLayout.PREFERRED_SIZE, 704, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnaddet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtnompro)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtidpro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtcan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtsubtot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18)
                            .addComponent(txtisv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)
                            .addComponent(txtdesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)
                            .addComponent(txtprecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtdesmax)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnaddet)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtcomen, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel19)))
                .addGap(2, 2, 2))
        );

        Cerrar.setBackground(new java.awt.Color(255, 0, 51));
        Cerrar.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        Cerrar.setForeground(new java.awt.Color(255, 255, 255));
        Cerrar.setText("Cerrar");
        Cerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CerrarActionPerformed(evt);
            }
        });

        txtatendido.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        txtatendido.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                txtatendidoItemStateChanged(evt);
            }
        });

        txtfecha.setDateFormatString("dd-MM-yyyy");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbotipofac, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtnfact, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtfecha, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)))
                        .addGap(28, 28, 28)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbotienda, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(205, 205, 205)
                                .addComponent(jLabel15))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtusu, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtatendido, 0, 227, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Cerrar, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtfecha, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2)
                        .addComponent(txtnfact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(cbotienda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbotipofac, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(17, 17, 17)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Cerrar)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(txtusu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15)
                        .addComponent(txtatendido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 734, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnaddet3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddet3ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_btnaddet3ActionPerformed

    private void btnsavefacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsavefacActionPerformed
        guardaroupdateFactura();
    }//GEN-LAST:event_btnsavefacActionPerformed
    
    public void guardaroupdateFactura() {
        // TODO add your handling code here:
        try {
            btnsavefac.setEnabled(false);
            // if (guardarFactura(null)) {
            //     return;
            // }
            guardarFacturaOpen(null, 0);
            cerrarForm();
        } catch (Exception e) {
            System.out.println("Error-" + e.getMessage());
        }
    }
    
    public boolean guardarFactura(BigDecimal efe) throws ParseException, Exception, NumberFormatException, HeadlessException {
        try {
            boolean g = false;
            Date date = txtfecha.getDate();
            Factura f1 = jpfac.findFactura(new FacturaPK(Integer.parseInt(txtnfact.getValue().toString()), date));
            if (btnsavefac.getText().equals("Guardar") && f1 != null) {
                txtnfact.setValue(ultimaFact() + 1);
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
            if (g) {
                imprimirFactura();
            }
            cerrarForm();
        } catch (Exception e) {
            System.out.println("Error->" + e.getMessage());
        }

        //Desea crear una nueva factura
        /*if (JOptionPane.showInternalConfirmDialog(this, "Desea crear una nueva factura?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
        System.out.println("Factura Nueva Creada!");
        }*/
        return false;
    }
    
    public void cerrarForm() {
        //Desea crear una nueva factura
        /* if (JOptionPane.showInternalConfirmDialog(this, "Desea crear una nueva factura [si] / [no] Sale de la Factura!", "Facturacion Rapida!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            limpiarCampos();
            detfac.setModel(dt.crearTabla());
            prosel=null;clisel=null;btnprintfac.setEnabled(true);
            nuevafac=0;btnsavefac.setText("Guardar");
            dtf=new detallefactura();
            txttot.setText("0.00");
            txtcodcli.setText("");
            txtnombre.setText("");
            txtdir.setText("");
            iniciarFactura();
        } else {*/
        this.dispose();
        //}
    }
    
     public static class resultado{
         public static Integer r=0;
         public static Factura factg;
         public static Detallefactura detfag;
         public static Integer nk=0;
         public static Integer cant=0;
    }
    
    public void saveFactura(Date date, BigDecimal efe) throws NumberFormatException, Exception {
    Thread t1=new Thread(new Runnable() {
        @Override
        public void run() {
        pkfact = new FacturaPK();
        factura.setEfectivo(efe);
        Tipofactura seltpf = ((Tipofactura) cbotipofac.getSelectedItem());
        Empresa selemp = (Empresa) cbotienda.getSelectedItem();
        Global.ulogin=(Usuario)txtatendido.getSelectedItem();
        //Tiporden seltipo = (Tiporden) cbotipoorden.getSelectedItem();
        if (clisel == null) {
            try {
                clisel = new Cliente();
                clisel.setIdCliente("0000-0000-00000");
                clisel.setNombre("Consumidor Final");
                fincli.create(clisel);
            } catch (Exception e) {
                System.out.println("Error->" + e.getMessage());
            }
        }
        //factura.setNombrefac(txtnombre.getText());//Nombre a quien saldra la factura
        factura.setCliente(clisel);
        if (efe == null) {
            factura.setEstadofact(new Estadofact(1));
        } else {
            factura.setEstadofact(new Estadofact(2));
        }
        factura.setEmpresa(selemp);
        factura.setTipofact(seltpf);
        //factura.setIdorden(seltipo);
        if (txtusu.getText().isEmpty()) {
            factura.setUsuario(Global.ulogin);
        } else {
            factura.setUsuario(new Usuario(txtusu.getText()));
        }
        factura.setTotalfac(new BigDecimal(dtf.totalarticulos()));
        if (nuevafac == 0) {
            //txtnfact.setValue(""+ultimaFact()+1);
            factura.setFacturaPK(new FacturaPK(Integer.parseInt(txtnfact.getValue().toString()), date));
            System.out.println(factura);
            resultado.factg=factura;
            try {
                jpfac.create(resultado.factg);
            } catch (Exception ex) {
                Logger.getLogger(frmFacturav61.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //JOptionPane.showMessageDialog(rootPane, "Factura Almacenada! ->" + date);
        for (articulo dtfac : dtf.getLParticulos()) {
            Detallefactura dtftmp = new Detallefactura();
            dtftmp.setDetallefacturaPK(new DetallefacturaPK(dtfac.getId(), factura.getFacturaPK().getIdFactura(), factura.getFacturaPK().getFecha()));
            dtftmp.setProducto(dtfac.getCodigo());
            dtftmp.setNomproducto(dtfac.getNombre());
            dtftmp.setCantidad((int) dtfac.getCantidad());
            if (dtfac.getCodigo().toLowerCase().equals("alq") && totAlq > 0) {
                dtftmp.setPrecio(BigDecimal.valueOf(valAlq));
                dtftmp.setDescuento(new BigDecimal(0.00));
            } else {
                dtftmp.setPrecio(dtfac.getPrecio());
                dtftmp.setDescuento(new BigDecimal(dtfac.getDescuento()));
            }
            dtftmp.setComentarios(dtfac.getComentarios());
            
            Double operaIsv = dtfac.getValisv();
            dtftmp.setIsv(new BigDecimal(operaIsv));
            //Estado controla si la orden ya fue impresa
            if (dtfac.getEstado().equals("1")) {
                dtftmp.setStadodet(1);
            } else {
                dtftmp.setStadodet(0);
            }
            try {
                resultado.detfag=dtftmp;
                jpdfac.create(resultado.detfag);
                System.out.println(dtftmp);
            } catch (Exception e) {
                System.out.println("Error->" + e.getMessage());
            }
        }
        if (nuevafac > 0) {
            System.out.println(factura);
            resultado.factg=factura;
            try {
                jpfac.edit(resultado.factg);
            } catch (Exception ex) {
                Logger.getLogger(frmFacturav61.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        }
    });
    t1.start();
    t1.join();
    }
    
   
     
    public void updateFactura(Date date, BigDecimal efe, Factura f1) throws NumberFormatException, Exception {
        //En caso de ser modificada la factura y agregar un producto el total se calcula mejor al final
        //pkfact = new FacturaPK(Integer.parseInt(txtnfact.getValue().toString()), date);
        factura.setFacturaPK(pkfact);
        factura.setEfectivo(efe);
        Tipofactura seltpf = ((Tipofactura) cbotipofac.getSelectedItem());
        Empresa selemp = (Empresa) cbotienda.getSelectedItem();
        ///Tiporden seltipo = (Tiporden) cbotipoorden.getSelectedItem();
        //factura.setNombrefac(txtnombre.getText());//Nombre a quien saldra la factura
        clisel = fincli.findCliente(txtcodcli.getText());
        factura.setCliente(clisel);
        if (efe == null) {
            factura.setEstadofact(new Estadofact(1));
        } else {
            factura.setEstadofact(new Estadofact(2));
        }
        factura.setEmpresa(selemp);
        factura.setTipofact(seltpf);
        ///factura.setIdorden(seltipo);
        factura.setUsuario(Global.ulogin);
        //El calculo del total se envio al final para mantener actualizado el
        //total al momento de eliminar un renglon
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
        //Una vez elimnada la fila, calcula nuevamente el total
        factura.setTotalfac(new BigDecimal(dtf.totalarticulos()));
        //factura.setEfectivo(new BigDecimal(Double.parseDouble(txt)));
        System.out.println(factura);
        jpfac.edit(factura);
    }

    /**
     * *
     * Funcion para almacenar las facturas como abiertas o pendientes de cobrar
     */
    public void guardarFacturaOpen(BigDecimal efe, Integer corden) throws ParseException, Exception, NumberFormatException, HeadlessException {
        try {
            Date date = txtfecha.getDate();
            if (btnsavefac.getText().equals("Guardar")) {
                txtnfact.setValue(ultimaFact() + 1);
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
            }
        } catch (Exception e) {
            System.out.println("Error->" + e.getMessage());
        }
    }

    private void btnaddetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddetActionPerformed
        addprolista();
    }//GEN-LAST:event_btnaddetActionPerformed
    
    public void addprolista() {
        this.btnaddet.setEnabled(false);
        try {
            if (prosel != null) {
                if (prosel.getIdProducto().length() > 0) {
                    articulo padd = new articulo();
                    padd.setCodigo(prosel.getIdProducto());
                    padd.setNombre(prosel.getNombre());
                    padd.setCantidad(Double.parseDouble(txtcan.getValue().toString()));
                    padd.setDescuento(Double.parseDouble(txtdesc.getValue().toString()));
                    padd.setIsv((prosel.getImpuesto().doubleValue() > 0) ? true : false);
                    padd.setValisv(Double.parseDouble(txtisv.getValue().toString()));
                    padd.setPrecio(BigDecimal.valueOf(prosel.getPrecio()));
                    calcSubtotal();
                    padd.setSubtotal(Double.parseDouble(txtsubtot.getValue().toString()));
                    padd.setEstado("0");
                    padd.setComentarios(txtcomen.getText());
                    addproTableV3(padd);
                    subtot = 0.0;
                    ///guardaroupdateFactura();
                    txtidpro.requestFocus();
                    this.btnaddet.setEnabled(true);
                    prosel = null;
                }

            } else {
                txtidpro.requestFocus();
                this.btnaddet.setEnabled(true);
            }
        } catch (Exception e) {
            System.out.println("Error->" + e.getMessage());
        }
    }
    
    public void buscarProducto() {
        if (estado == 1) {
            if (!txtnombre.getText().isEmpty()) {
                JDialog jdet = new JDialog();
                jdet.setContentPane(new panelDet());
                jdet.setLocation(200, 200);
                jdet.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                jdet.pack();
                jdet.setModal(true);
                jdet.setVisible(true);
                if (((panelDet) jdet.getContentPane()).addpro == 1) {
                    System.out.println("Producto Agregado");
                    addproTableV2(jdet);
                } else {
                    JOptionPane.showMessageDialog(rootPane, "La factura ya esta cerrada!", "Consulte al Administrador.", JOptionPane.WARNING_MESSAGE);
                    
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Debe ingresar un Cliente", "El Cliente no ha sido Ingresado!", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    public void buscarProductov3() {
        if (estado == 1) {
            if (!txtnombre.getText().isEmpty()) {
                JDialog jdet = new JDialog();
                jdet.setContentPane(new panelDetV3());
                jdet.setLocation(200, 200);
                jdet.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                jdet.pack();
                jdet.setModal(true);
                jdet.setVisible(true);
                if (((panelDetV3) jdet.getContentPane()).addpro == 1) {
                    System.out.println("Producto Agregado");
                    prosel = ((panelDetV3) jdet.getContentPane()).pselec;
                    txtidpro.setText(prosel.getIdProducto());
                   /* txtnompro.setText(prosel.getNombre());
                    txtprecio.setValue(prosel.getPrecio() - (prosel.getPrecio() * prosel.getImpuesto().doubleValue()));
                    txtisv.setValue(prosel.getPrecio() * prosel.getImpuesto().doubleValue());
                    subtot = ((Double.parseDouble(txtprecio.getValue().toString()) * Double.parseDouble(txtcan.getValue().toString())) + Double.parseDouble(txtisv.getValue().toString()));
                    txtsubtot.setValue(subtot);*/
                     txtnompro.setText(prosel.getNombre());
                    txtprecio.setValue(prosel.getPrecio() / (1 + prosel.getImpuesto().doubleValue()));
                    //Calculo del impuesto multiplicado                
                    //txtisv.setValue(prosel.getPrecio() / prosel.getImpuesto().doubleValue());
                    txtisv.setValue((prosel.getPrecio() / (1 + prosel.getImpuesto().doubleValue())) * prosel.getImpuesto().doubleValue());
                    //Calculo del impuesto multiplicado
                    //subtot = ((Double.parseDouble(txtprecio.getValue().toString()) * Double.parseDouble(txtcan.getValue().toString())) + Double.parseDouble(txtisv.getValue().toString()));
                    subtot = (((Double.parseDouble(txtprecio.getValue().toString()) + Double.parseDouble(txtisv.getValue().toString())) * Double.parseDouble(txtcan.getValue().toString())));
                    txtsubtot.setValue(subtot);
                } else {
                    JOptionPane.showMessageDialog(rootPane, "La factura ya esta cerrada!", "Consulte al Administrador.", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Debe ingresar un Cliente", "El Cliente no ha sido Ingresado!", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    public void addproTable(JDialog jdet) {
        padd = ((panelDet) jdet.getContentPane()).producto;
        dtf.addPArticulos(padd);
        txttot.setValue(dtf.totalarticulos());
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
        padd = ((panelDet) jdet.getContentPane()).producto;
        dtf.addPArticulos(padd);
        txttot.setValue(dtf.totalarticulos());
        try {
            DefaultTableModel temp = (DefaultTableModel) detfac.getModel();
            Object nuevo[] = {padd.getId(), padd.getCodigo(), padd.getNombre(), padd.getComentarios(), String.format("%,.2f", padd.getCantidad()),
                String.format("%,.2f", padd.getPrecio()), String.format("%,.2f", padd.getDescuento()), String.format("%,.2f", padd.getValisv()), String.format("%,.2f", padd.getSubtotal())};
            temp.addRow(nuevo);
        } catch (Exception e) {
            System.out.println("" + e.getMessage());
        }
    }
    
    public void addproTableV3(articulo art) {
         padd = art;
        if (padd.getCodigo().length() > 0) {
            dtf.addPArticulos(padd);
            txttot.setValue(dtf.totalarticulosISV());
            txttot.setText("" + BigDecimal.valueOf(dtf.totalarticulosISV()).setScale(2, BigDecimal.ROUND_HALF_EVEN));
            try {
                DefaultTableModel temp = (DefaultTableModel) detfac.getModel();
                Object nuevo[] = {padd.getId(), padd.getCodigo(), padd.getNombre(), padd.getComentarios(), String.format("%,.2f", padd.getCantidad()),
                    String.format("%,.2f", padd.getPrecio()), String.format("%,.2f", padd.getDescuento()), String.format("%,.2f", padd.getValisv()), String.format("%,.2f", padd.getSubtotal())};
                temp.addRow(nuevo);
            } catch (Exception e) {
                System.out.println("" + e.getMessage());
            }
        }
        limpiarCampos();
        txtidpro.setText("");
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
            //boolean x=(deta.getIsv().doubleValue()>0)?true:false;
            padd.setIsv((deta.getIsv().doubleValue() > 0) ? true : false);
            anionacimientocli = txtcodcli.getText().toString().substring(5, 9);
            //if (Integer.parseInt(anionacimientocli) > 0) {
            //    padd.setDescuento(deta.getPrecio().doubleValue() * 0.25);
            //} else {
            padd.setDescuento(deta.getDescuento().doubleValue());
            // }
            padd.setValisv(deta.getIsv().doubleValue());
            subtot = (deta.getIsv().doubleValue() + deta.getPrecio().doubleValue()) * deta.getCantidad();
            //padd.setSubtotal(subtot);
            padd.setEstado(deta.getStadodet().toString());
            dtf.addPArticulos(padd);
            //addproTableV3(padd);
            try {
                Object nuevo[] = {deta.getDetallefacturaPK().getNrolinea(), deta.getProducto(), deta.getNomproducto(),
                    deta.getCantidad(), String.format("%,.3f", (deta.getPrecio().doubleValue())),
                    String.format("%,.3f", padd.getDescuento().doubleValue()), String.format("%,.3f", deta.getIsv().doubleValue()),
                    String.format("%,.3f", padd.getSubtotal())};
                temp.addRow(nuevo);
            } catch (Exception e) {
                System.out.println("" + e.getMessage());
            }
        }
        //txttot.setValue(dtf.totalarticulosISV());
    }

    private void cbotipofacKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbotipofacKeyReleased
        //MostrarDatoscel(txtidcli);
    }//GEN-LAST:event_cbotipofacKeyReleased

    private void txtidcliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtidcliKeyReleased
        // TODO add your handling code here:
        switch (evt.getKeyCode()) {
            case com.sun.glass.events.KeyEvent.VK_F4:
                //abrirBuscacli();
                dialogBuscaCli();
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
        iniciarFactura();
    }//GEN-LAST:event_formInternalFrameOpened
    
    public void iniciarFactura() {
        // TODO add your handling code here:
        try {
            cargarCombobox();
            //txtusu.setText(Global.ulogin.getLogin());
            menup = (Menuprincipal) getTopLevelAncestor();
            ///MostrarDatoscel(txtidcli);
            //Carga los Combobox
            ///cbotipoorden.setModel(this.ObtenerListaTipoor());
            cbotienda.setModel(this.ObtenerListaEmp());
            cbotipofac.setModel(this.ObtenerListaTipofac());
            txtatendido.setModel(this.ObtenerListausu());
            lcorre = pcorrela.ultimoCorrela();
            if (nuevafac == 0) {
                txtnfact.setValue(ultimaFact() + 1);
            } else {
                txtnfact.setValue(nuevafac);
                txtnfact.setEnabled(false);
                //Carga la factura existente
                factura = jpfac.findFactura(pkfact);
                //Carga el detalle existente
                lsdetfac = jpdfac.findDetallefacturaEntities2(new DetallefacturaPK(pkfact.getIdFactura(), pkfact.getFecha()));
                cbotienda.setSelectedItem(factura.getEmpresa());
                cbotipofac.setSelectedItem(factura.getTipofact());
                ///cbotipoorden.setSelectedItem(factura.getIdorden());
                txtidcli.setSelectedItem(factura.getCliente());
                txtnombre.setText(factura.getCliente().getNombre());
                txtdir.setText(factura.getCliente().getDireccion());
                txttot.setText("" + factura.getTotalfac());
                addproTable2(lsdetfac);
            }
            cbotipofac.requestFocus();
             Integer uf = ultimaFact();
                     
            if ((lcorre.getRangofin() - lcorre.getAlernfac()) < uf) {
                JOptionPane.showMessageDialog(this, "Los correlativos estan por agotarse actualmente tiene " + (lcorre.getRangofin() - uf) + " dispobiles", "Advertencia!", JOptionPane.WARNING_MESSAGE);
                alerta = "Los correlativos estan por agotarse actualmente tiene " + (lcorre.getRangofin() - uf) + " dispobiles.";
            }
            if (ultimaFact() == (lcorre.getRangofin())) {
                JOptionPane.showMessageDialog(this, "Los correlativos se agotaron no tiene dispobiles, facture manualmente!, \n Llame al administrador del sistema.", "Advertencia!", JOptionPane.WARNING_MESSAGE);
                alerta = "Los correlativos se agotaron no tiene dispobiles, facture manualmente!, \n Llame al administrador del sistema.";
                cerrarForm();
            }
            Date factual = new Date();
            ///Advierte que la fecha de vencimiento esta proxima
            //factual.getDate() == lcorre.getFechafin().getDate()
            String fe1[] = lcorre.getFechafin().toLocaleString().toString().substring(0, 10).split("-");
            String fe2[] = factual.toLocaleString().split("-");
            int d1 = factual.getDate(), d2 = Integer.parseInt(fe1[1]);
            int diffecha =numeroDiasEntreDosFechas(new Date(), lcorre.getFechafin());// diferenciaEnDias2(lcorre.getFechafin(), factual, Integer.parseInt(fe1[2].substring(0, 4)));
            /*d1 >= d2 && (factual.getYear() <= lcorre.getFechafin().getYear()
                    && factual.getMonth() <= lcorre.getFechafin().getMonth())*/
            if (diffecha > 0 && diffecha <= 10) {
                JOptionPane.showMessageDialog(this, "La fecha de caducidad de los correlativos es el " + lcorre.getFechafin() + ".\nDebe actualizar los correlativos!", "Advertencia!", JOptionPane.WARNING_MESSAGE);
                alerta = "El  correlativos es el " + lcorre.getRangofin() + ".\nDebe actualizar los correlativos muy pronto.";
                
            }
             if (diffecha < 0) {
                JOptionPane.showMessageDialog(this, "La fecha de caducidad de los correlativos es el " + lcorre.getFechafin() + ".\nDebe actualizar los correlativos muy pronto.", "Advertencia!", JOptionPane.WARNING_MESSAGE);
                alerta = "La fecha de caducidad de los correlativos es el " + lcorre.getFechafin() + ".\nDebe actualizar los correlativos muy pronto.";
                cerrarForm();
            }
            if (lcorre.getFechafin().toLocaleString().equals(lcorre.getFechaini().toLocaleString())) {
                JOptionPane.showMessageDialog(this, "Hoy es la fecha de caducidad de los correlativos " + lcorre.getFechafin().toLocaleString() + ".\nDebe actualizar los correlativos.", "Advertencia!", JOptionPane.WARNING_MESSAGE);
                alerta = "La fecha de caducidad de los correlativos es el " + lcorre.getFechafin().toLocaleString() + ".\nDebe actualizar los correlativos muy pronto.";
                cerrarForm();
                //lcorre.setes
                //pcorrela.edit(lcorre);
            }
            ///Activa la alerta de fecha de numeracion agotada
            if ((nuevafac + lcorre.getAlernfac()) >= (lcorre.getRangofin())) {
                JOptionPane.showMessageDialog(this, "Los correlativos estan por llegar a su numeracion Maxima.\nDebe actualizar los correlativos para continuar facturando.", "Advertencia!", JOptionPane.WARNING_MESSAGE);
                alerta = "Los correlativos estan por llegar a su numeracion Maxima.\nDebe actualizar los correlativos para continuar facturando.";
                //cerrarForm();Solo Alerta ya que hoy vencen es el ultimo dia
            }
            /* if (lcorre.getAlernfac()==lcorre.getRangofin()) {
                JOptionPane.showMessageDialog(this, "Los correlativos estan por llegar a su numeracion Maxima.\nDebe actualizar los correlativos para continuar facturando.", "Advertencia!", JOptionPane.WARNING_MESSAGE);
                alerta = "Los correlativos estan por llegar a su numeracion Maxima.\nDebe actualizar los correlativos para continuar facturando.";
                cerrarForm();//Solo Alerta ya que hoy vencen es el ultimo dia
            }*/
            ///Activa la alerta de fecha de numeracion agotada
            /*if (d1 > d2 && Integer.parseInt(fe2[0]) >= Integer.parseInt(fe1[0]) && Integer.parseInt(fe2[2].substring(0, 4)) >= Integer.parseInt(fe1[2])) {
                JOptionPane.showMessageDialog(this, "La fecha de caducidad de los correlativos llego a su fecha limite.\nDebe actualizar los correlativos para continuar facturando.", "Advertencia!", JOptionPane.WARNING_MESSAGE);
                alerta = "La fecha de caducidad de los correlativos llego a su fecha limite.\nDebe actualizar los correlativos para continuar facturando.";
                cerrarForm();
            }*/
            Menuprincipal mp = (Menuprincipal) getTopLevelAncestor();
            mp.lblalerta.setText(alerta);
           //Identifica que los productos son tomados desde el pedido
           //online
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error->" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error->" + e.getMessage());
        }
    }
    
    public int diferenciaEnDias2(Date fechaMayor, Date fechaMenor, int anio2) {
        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día 
        java.util.Date hoy = new Date(); //Fecha de hoy 
        int anio = anio2;//fechaMayor.getYear();
        int mes = fechaMayor.getMonth();
        int dia = fechaMayor.getDate(); //Fecha anterior 
        Calendar calendar = new GregorianCalendar(anio, mes - 1, dia);
        java.sql.Date fecha = new java.sql.Date(calendar.getTimeInMillis());
        long diferencia = (fecha.getTime() - hoy.getTime()) / MILLSECS_PER_DAY;
        return (int) diferencia;
    }
    
    public void cargarCombobox() {
        this.setMaximizable(true);
        if (cbotienda.getModel().getSize() == 0) {
            fincli = new ClienteJpaController(emFac);
            ltipfac = new TipofacturaJpaController(emFac);
            lempre = new EmpresaJpaController(emFac);
            jpfac = new FacturaJpaController(emFac);
            jpdfac = new DetallefacturaJpaController(emFac);
            jpusu = new UsuarioJpaController(emFac);
            finpro = new ProductoJpaController(emFac);
            pcorrela = new CorrelativossarJpaController(emFac);
        }
    }
    
 public int numeroDiasEntreDosFechas(Date fecha1, Date fecha2){
     long startTime = fecha1.getTime();
     long endTime = fecha2.getTime();
     long diffTime = endTime - startTime;
     return (int)TimeUnit.DAYS.convert(diffTime, TimeUnit.MILLISECONDS);
}
    
    private Integer ultimaFact() {
        Integer x = 0;
        try {
            //Actualizado para mejorar rendimiento tenia *
            Query qsql = fincli.getEntityManager().createNativeQuery("select idfactura,fecha,cliente,tienda,usuario,estado,totalfac from factura c where idfactura=(select max(idfactura) from factura)", Factura.class);//qsql.setParameter("n", txtnom.getText());
            qsql.setHint(QueryHints.REFRESH, HintValues.TRUE);//Actualiza
            Factura f2 = (Factura) qsql.getResultList().get(0);
            ///Verificar en la tabla correlativos cual es el ultimo numero
            ///Debido a que las facturas se pueden vencer por fecha o por
            /// que llegaron al limite de numeracion.
            Query qsql2 = fincli.getEntityManager().createNativeQuery("SELECT * FROM correlativossar c where c.estado=1 order by fechacreacion", Correlativossar.class);
            Correlativossar cor = (Correlativossar) qsql2.getSingleResult();
            if (f2.getFacturaPK().getIdFactura() <= cor.getRangoini()) {
                x = cor.getRangoini();
            } else {
                x = f2.getFacturaPK().getIdFactura();
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return x;
    }
    

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        // TODO add your handling code here:
        switch (evt.getKeyCode()) {
            case com.sun.glass.events.KeyEvent.VK_ESCAPE:
                cerrarForm();
                break;
            default:
                System.out.println("");
                ;
        }
    }//GEN-LAST:event_formKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //dialogBuscaCli();
        //abrirBuscacli();
    }//GEN-LAST:event_jButton1ActionPerformed
    
    public void dialogBuscaCli() {
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
                txtcodcli.setText(clisel.getIdCliente());
                txtidcli.setModel(modelo);
                txtnombre.setText(clisel.getNombre());
                txtdir.setText(clisel.getDireccion());
            }
        } catch (Exception e) {
            System.out.println("Error->" + e.getMessage());
        }
    }

    private void detfacKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_detfacKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            if (JOptionPane.showInternalConfirmDialog(this, "Esata seguro quitar la fila", "La fila sera elimnada!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                int f = detfac.getSelectedRow();//Integer.parseInt(detfac.getModel().getValueAt(detfac.getSelectedRow(),0).toString());
                System.out.println("Fila seleccionada-->"+detfac.getModel().getValueAt(detfac.getSelectedRow(), 0));
                DefaultTableModel temp = (DefaultTableModel) detfac.getModel();
                System.out.println("Objeto seleccioonado"+temp.getDataVector().elementAt(f).toString());
               //Quita elemento de la lista de articulos
                dtf.delPArticulos(f);
        
                //Validacion de acceso usuario Administrador
                if (1 == 1) {
                    if (nuevafac > 0) {
                        try {
                            if (btnprintfac.isEnabled()) {
                                Integer lin = Integer.parseInt(detfac.getModel().getValueAt(f, 0).toString());
                                jpdfac.destroy(new DetallefacturaPK(lin, pkfact.getIdFactura(), pkfact.getFecha()));
                                try {
                                    temp.removeRow(detfac.getSelectedRow());
                                    txttot.setValue(dtf.totalarticulos());
                                    guardarFacturaOpen(null, 1);
                                } catch (Exception ex) {
                                    Logger.getLogger(frmFacturav61.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        } catch (Exception ex) {
                            Logger.getLogger(frmFacturav61.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        temp.removeRow(detfac.getSelectedRow());
                        txttot.setValue(dtf.totalarticulos());
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "No tiene permisos para quitar filas, consulte al administrador!", "Error!", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_detfacKeyReleased

    private void btnprintfacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprintfacActionPerformed
        guardarFactura();
    }//GEN-LAST:event_btnprintfacActionPerformed
    
    public void guardarFactura() {
        try {
            Date date = txtfecha.getDate();
            //guardaroupdateFactura();
            //JOptionPane.showMessageDialog(rootPane,"Factura->"+date.toString()+"->"+txtnfact.getValue()+"->"+LocalDate.now());
            JDialog jdet = new JDialog();
            Integer nfa = Integer.parseInt(txtnfact.getValue().toString());
            BigDecimal totsen = new BigDecimal(txttot.getText().replaceAll(",", ""));
            jdet.setContentPane(new panelPagofactura(nfa, date, totsen));
            jdet.setLocation(200, 200);
            jdet.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jdet.pack();
            jdet.setModal(true);
            ((panelPagofactura) jdet.getContentPane()).txtefectivo.requestFocus();
            jdet.setVisible(true);
            if (((panelPagofactura) jdet.getContentPane()).pago == true) {
                if (guardarFactura(((panelPagofactura) jdet.getContentPane()).efectivo)) {
                    return;
                }
            } else {
                guardarFacturaOpen(((panelPagofactura) jdet.getContentPane()).efectivo, 0);
            }
            cerrarForm();
            //imprimirFactura();
        } catch (Exception e) {
            System.out.println("Error->" + e.getMessage());
        }
    }

    private void txtnombreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtnombreFocusGained
        // TODO add your handling code here:
        if (((Cliente) txtidcli.getSelectedItem()) != null) {
            Cliente c = fincli.findCliente(((Cliente) txtidcli.getSelectedItem()).getIdCliente().toString());
            txtnombre.setText(c.getNombre());
        }

    }//GEN-LAST:event_txtnombreFocusGained
    
    public void limpiarCampos() {
        txtcan.setValue(BigDecimal.valueOf(1.00));
        txtprecio.setValue(BigDecimal.valueOf(0.00));
        txtsubtot.setValue(BigDecimal.valueOf(0.00));
        txtdesc.setValue(BigDecimal.valueOf(0.00));
        txtisv.setValue(BigDecimal.valueOf(0.00));
        txtcomen.setText("");
        txtnompro.setText("");
    }
    
    private void ProductoFilcod() {
         limpiarCampos();
        try {
            String pt = txtidpro.getText().toString().toLowerCase();
            Query qsql = finpro.getEntityManager().createNativeQuery("select * from producto c where Lower(c.idproducto)='" + pt.toLowerCase().trim() + "' limit 0,1", Producto.class);//qsql.setParameter("n", txtnom.getText());
            prosel = (Producto) qsql.getSingleResult();
            if (prosel != null) {
                txtnompro.setText(prosel.getNombre());
                txtprecio.setValue(prosel.getPrecio() / (1 + prosel.getImpuesto().doubleValue()));
                //Calculo del impuesto multiplicado                
                //txtisv.setValue(prosel.getPrecio() / prosel.getImpuesto().doubleValue());
                txtisv.setValue((prosel.getPrecio() / (1 + prosel.getImpuesto().doubleValue())) * prosel.getImpuesto().doubleValue());
                //Calculo del impuesto multiplicado
                //subtot = ((Double.parseDouble(txtprecio.getValue().toString()) * Double.parseDouble(txtcan.getValue().toString())) + Double.parseDouble(txtisv.getValue().toString()));
                subtot = (((Double.parseDouble(txtprecio.getValue().toString()) + Double.parseDouble(txtisv.getValue().toString())) * Double.parseDouble(txtcan.getValue().toString())));
                txtsubtot.setValue(subtot);
            }
        } catch (Exception e) {
            System.out.println("Error->" + e.getMessage());
        }
    }
    
   

    private void txtcanFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcanFocusLost
        // TODO add your handling code here:
        txtcan.setBackground(Color.WHITE);
        if (Double.parseDouble(txtprecio.getValue().toString()) > 0) {
            calcSubtotal();
        }
    }//GEN-LAST:event_txtcanFocusLost

    private void txtcanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcanKeyReleased
        // TODO add your handling code here:
        if (Double.parseDouble(txtcan.getValue().toString()) > 0) {
            calcSubtotal();
    }//GEN-LAST:event_txtcanKeyReleased
    }

    private void txtprecioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprecioKeyPressed
        // TODO add your handling code here:
        if (Double.parseDouble(txtcan.getValue().toString()) > 0) {
            calcSubtotal();
        }
    }//GEN-LAST:event_txtprecioKeyPressed

    private void txtprecioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprecioKeyReleased
        //JDialog jd = (JDialog) this.getRootPane().getParent();
        //Global.cambiarFoco(jd);
        if (Double.parseDouble(txtcan.getValue().toString()) > 0) {
            calcSubtotal();
        }
    }//GEN-LAST:event_txtprecioKeyReleased

    private void txtcodcliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcodcliFocusGained
        // TODO add your handling code here:
        txtcodcli.setBackground(Color.RED);
    }//GEN-LAST:event_txtcodcliFocusGained

    private void txtcodcliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcodcliFocusLost
        // TODO add your handling code here:
       txtcodcli.setBackground(Color.WHITE);
        if (!txtcodcli.getText().isEmpty()) {
            clisel = Filcodcli(txtcodcli.getText());
            if (clisel.getIdCliente() != null) {
                txtnombre.setText(clisel.getNombre());
                txtdir.setText(clisel.getDireccion());
            } else {
                JOptionPane.showMessageDialog(rootPane, "El Cliente no existe, pruebe otro codigo", "Error!", JOptionPane.WARNING_MESSAGE);
                txtcodcli.select(0, txtcodcli.getText().length());
                txtcodcli.requestFocus();
            }
        }
    }//GEN-LAST:event_txtcodcliFocusLost

    private Integer ultimoExpediente() {
        Integer x = 0;
        try {
            Query qsql = fincli.getEntityManager().createNativeQuery("select * from cliente c where nroexpediente=(select max(nroexpediente) from cliente)", Cliente.class);//qsql.setParameter("n", txtnom.getText());
            qsql.setHint(QueryHints.REFRESH, HintValues.TRUE);//Actualiza
            Cliente c2 = (Cliente) qsql.getSingleResult();
           /* if (c2.getNroexpediente() == 0) {
                x = 1;
            } else {
                x = c2.getNroexpediente() + 1;
            }*/
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return x;
    }
    
    private void txtcodcliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcodcliKeyReleased
        cerrarconESC(evt);
        if (evt.getKeyCode() == KeyEvent.VK_F4) {
            buspro = 1;
            dialogBuscaCli();
            // dialogBuscaDoctor();
        } else {
            buspro = 0;
        }
    }//GEN-LAST:event_txtcodcliKeyReleased
    
      
    public void cerrarconESC(KeyEvent evt) {
        // TODO add your handling code here:
        if (evt.getKeyChar() == KeyEvent.VK_ESCAPE) {
            if (JOptionPane.showInternalConfirmDialog(this, "Esta seguro de salir, sino a Guardado Perdera la Informacion?", "Saliendo de la Factura!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                cerrarForm();
            }
        }
    }

    private void txtidproKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtidproKeyReleased
        // TODO add your handling code here:
       // TODO add your handling code here:
        cerrarconESC(evt);
        if (evt.getKeyCode() == KeyEvent.VK_F4) {
            buspro = 1;
            buscarProductov3();
        } else {
            if (txtidpro.getText().length() > 0) {
                ProductoFilcod();
            }
        }
    }//GEN-LAST:event_txtidproKeyReleased
    

    private void txtcanVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_txtcanVetoableChange
        // TODO add your handling code here:
        if (Double.parseDouble(txtcan.getValue().toString()) > 0) {
            calcSubtotal();
        }
    }//GEN-LAST:event_txtcanVetoableChange

    private void txtidproFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtidproFocusLost
        if (txtidpro.getText().length() > 0) {
            ProductoFilcod();
            if (prosel != null) {
                txtcan.requestFocus();
                txtidpro.setBackground(Color.WHITE);
            }
        } else {
            prosel = null;
        }
    }//GEN-LAST:event_txtidproFocusLost

    private void txtidproFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtidproFocusGained
        // TODO add your handling code here:
        txtidpro.setBackground(Color.red);
    }//GEN-LAST:event_txtidproFocusGained

    private void txtcanFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcanFocusGained
        // TODO add your handling code here:
        txtcan.setBackground(Color.red);
    }//GEN-LAST:event_txtcanFocusGained

    private void txtcomenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcomenFocusGained
        // TODO add your handling code here:
        txtcomen.setBackground(Color.red);
    }//GEN-LAST:event_txtcomenFocusGained

    private void txtcomenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcomenFocusLost
        // TODO add your handling code here:
        txtcomen.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtcomenFocusLost

    private void jPanel1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel1KeyReleased
        // TODO add your handling code here:
        cerrarconESC(evt);
    }//GEN-LAST:event_jPanel1KeyReleased

    private void btnaddetFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnaddetFocusGained
        // TODO add your handling code here:
        addprolista();
    }//GEN-LAST:event_btnaddetFocusGained

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameClosing

    private void CerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CerrarActionPerformed
        // TODO add your handling code here:
        if (JOptionPane.showInternalConfirmDialog(this, "Esta seguro de salir, sino Guarda o Actualiza Perdera la Informacion?", "Saliendo de la Factura!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            cerrarForm();
        }
    }//GEN-LAST:event_CerrarActionPerformed

    private void txtatendidoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_txtatendidoItemStateChanged
        // TODO add your handling code here:
        txtusu.setText(((Usuario) txtatendido.getSelectedItem()).getLogin());
    }//GEN-LAST:event_txtatendidoItemStateChanged

    private void txtidproActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidproActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidproActionPerformed

    private void txtcodcliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcodcliKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcodcliKeyTyped

    private void txteditnomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txteditnomActionPerformed
        // TODO add your handling code here:
        if (txteditnom.isSelected()) {
            txtnombre.setEditable(true);
            txtnombre.requestFocus();
        } else {
            txtnombre.setEditable(false);
        }
    }//GEN-LAST:event_txteditnomActionPerformed

    private void txtprecioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtprecioFocusLost
        // TODO add your handling code here:
        /*if (Double.parseDouble(txtprecio.getValue().toString()) > 0) {
            calcSubtotal();
        }*/
    }//GEN-LAST:event_txtprecioFocusLost
    
    public void imprimirFactura() {
        // TODO add your handling code here:
        try {
            Factura f1;
            if (nuevafac == 0) {
                Date date = txtfecha.getDate();
                f1 = jpfac.findFactura(new FacturaPK(Integer.parseInt(txtnfact.getValue().toString()), date));
            } else {
                f1 = jpfac.findFactura(pkfact);
            }
            if (f1 != null) {
                if (f1.getEfectivo() != null) {
                    Integer id = Integer.parseInt(txtnfact.getValue().toString());
                    if (id > 0) {
                        // report.startReportprint("" + id);
                        report.startReportprintL("" + id,f1.getTotalfac().toString());
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "La factura no ha sido cobrada!, presione \"Guardar\" para Cobrar!", "Facturacion!", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception e) {
            System.out.println("Error->" + e.getMessage());
        }
    }
    
    public DefaultComboBoxModel ObtenerListaTipofac() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        try {
            List<Tipofactura> ltpf = ltipfac.findTipofacturaEntities();
            for (Tipofactura it : ltpf) {
                Tipofactura p = it;
                modelo.addElement(p);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error ");
            System.out.println("Coneccion incorrecta: " + ex);
        }
        return modelo;
    }
    
    public DefaultComboBoxModel ObtenerListausu() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        try {
            List<Usuario> lu = jpusu.findUsuarioEntities();
            for (Usuario it : lu) {
                Usuario p = it;
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
            List<Empresa> lemp = lempre.findEmpresaEntities();
            for (Empresa it : lemp) {
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
            String pt = txtfil;
            Query qsql = fincli.getEntityManager().createNativeQuery("select * from cliente c where c.idcliente LIKE \'" + pt + "%\'", Cliente.class
            );//qsql.setParameter("n", txtnom.getText());
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
    
    public Cliente Filcodcli(String txtfil) {
        Cliente lcli = new Cliente();
        try {
            String pt = txtfil;
            Query qsql = fincli.getEntityManager().createNativeQuery("select * from cliente c where c.idcliente='" + pt + "'", Cliente.class
            );//qsql.setParameter("n", txtnom.getText());
            lcli = (Cliente) qsql.getSingleResult();
        } catch (Exception e) {
            System.out.println("Error->" + e.getMessage());
        }
        
        return lcli;
    }
    
    public Cliente FilnroExpecli(String txtfil) {
        Cliente lcli = new Cliente();
        try {
            String pt = txtfil;
            Query qsql = fincli.getEntityManager().createNativeQuery("select * from cliente c where c.nroexpediente=" + pt, Cliente.class
            );//qsql.setParameter("n", txtnom.getText());
            lcli = (Cliente) qsql.getSingleResult();
        } catch (Exception e) {
            System.out.println("Error->" + e.getMessage());
        }
        
        return lcli;
    }
    
    public List<Cliente> Filcelclike(String cel) {
        
        List<Cliente> lcli = new ArrayList<Cliente>();
        try {
            String pt = cel;
            Query qsql = fincli.getEntityManager().createNativeQuery("select * from cliente c where c.telefono LIKE \'" + pt + "%\' or  c.celular LIKE \'" + pt + "%\' or c.idcliente LIKE \'" + pt + "%\'", Cliente.class
            );//qsql.setParameter("n", txtnom.getText());
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
            java.util.logging.Logger.getLogger(frmFacturav61.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmFacturav61.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmFacturav61.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmFacturav61.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new frmFacturav61().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Cerrar;
    public javax.swing.JButton btnaddet;
    private javax.swing.JButton btnaddet3;
    public javax.swing.JButton btnprintfac;
    public javax.swing.JButton btnsavefac;
    private javax.swing.JComboBox<String> cbotienda;
    private javax.swing.JComboBox<String> cbotipofac;
    private javax.swing.JTable detfac;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JComboBox<String> txtatendido;
    private javax.swing.JSpinner txtcan;
    public javax.swing.JTextField txtcodcli;
    private javax.swing.JTextField txtcomen;
    private javax.swing.JSpinner txtdesc;
    private javax.swing.JLabel txtdesmax;
    private javax.swing.JTextArea txtdir;
    private javax.swing.JCheckBox txteditnom;
    private com.toedter.calendar.JDateChooser txtfecha;
    private javax.swing.JComboBox<String> txtidcli;
    private javax.swing.JTextField txtidpro;
    private javax.swing.JSpinner txtisv;
    private javax.swing.JSpinner txtnfact;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txtnompro;
    private javax.swing.JSpinner txtprecio;
    private javax.swing.JSpinner txtsubtot;
    private javax.swing.JFormattedTextField txttot;
    private javax.swing.JTextField txtusu;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
}
