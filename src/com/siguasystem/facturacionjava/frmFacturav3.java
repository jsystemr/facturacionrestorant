/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siguasystem.facturacionjava;

import com.siguasystem.modelo.Correlativossar;
import com.siguasystem.modelo.Cliente;
import com.siguasystem.modelo.ClienteJpaController;
import com.siguasystem.modelo.CorrelativossarJpaController;
import com.siguasystem.modelo.Detallefactura;
import com.siguasystem.modelo.DetallefacturaJpaController;
import com.siguasystem.modelo.DetallefacturaPK;
import com.siguasystem.modelo.Empresa;
import com.siguasystem.modelo.EmpresaJpaController;
import com.siguasystem.modelo.Estadofact;
import com.siguasystem.modelo.Factura;
import com.siguasystem.modelo.FacturaJpaController;
import com.siguasystem.modelo.FacturaPK;
import com.siguasystem.modelo.Historicaproductos;
import com.siguasystem.modelo.HistoricaproductosJpaController;
import com.siguasystem.modelo.HistoricaproductosPK;
import com.siguasystem.modelo.Producto;
import com.siguasystem.modelo.ProductoJpaController;
import com.siguasystem.modelo.Tipofactura;
import com.siguasystem.modelo.TipofacturaJpaController;
import com.siguasystem.modelo.Tiporden;
import com.siguasystem.modelo.TipordenJpaController;
import com.siguasystem.modelo.Usuario;
import com.siguasystem.modelo.UsuarioJpaController;
import com.siguasystem.modelos.exceptions.NonexistentEntityException;
import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import static java.awt.Frame.getFrames;
import java.awt.HeadlessException;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.AbstractList;
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
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameAdapter;
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
public class frmFacturav3 extends javax.swing.JInternalFrame {

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
    frmBusclientes frmbuscacli;//= new frmBusclientes();
    EjecutarReporte report;// = new EjecutarReporte();
    FacturaPK pkfact;//Toma valor si la factura existen
    DetallefacturaPK pkdfact;//Toma valor si el detalle existe
    Integer nuevafac = 0;
    Integer estado = 1;
    Integer buspro = 0;
    public boolean g = false;
    private Double subtot = 0.0;
    public int formStado = 0;
    private Correlativossar lcorre; //= new Correlativossar();
    private List<Cliente> lscli = new ArrayList<Cliente>();
    private List<Detallefactura> lsdetfac = new ArrayList<Detallefactura>();
    public Cliente clisel;
    private Producto prosel;
    private ClienteJpaController fincli; //= new ClienteJpaController(Persistence.createEntityManagerFactory("Restorant"));
    public TipofacturaJpaController ltipfac;//= new TipofacturaJpaController(Persistence.createEntityManagerFactory("Restorant"));
    public TipordenJpaController ltipor;//= new TipordenJpaController(Persistence.createEntityManagerFactory("Restorant"));
    public EmpresaJpaController lempre;//=new EmpresaJpaController(Persistence.createEntityManagerFactory("Restorant"));
    public FacturaJpaController jpfac;
    public DetallefacturaJpaController jpdfac;
    public UsuarioJpaController jpusu;
    private ProductoJpaController finpro; //= new ProductoJpaController(emFac);
    private CorrelativossarJpaController pcorrela;// = new CorrelativossarJpaController(emFac);
    private HistoricaproductosJpaController hpro;//= new HistoricaproductosJpaController(emFac);

    public frmFacturav3() {
        initComponents();
        detfac.setModel(dt.crearTabla());
        // setSize(this.getToolkit().getScreenSize().width, this.getToolkit().getScreenSize().width);
        txtfecha.setText(formatfac.format(new Date()));
        tamanioCol();
        btnfincli.setEnabled(false);
        asginarFocoEnter();
        iniciarFacturacion();
        cbotipofac.requestFocus();
    }

    public void asginarFocoEnter() {
        cambiarFoco(cbotienda);
        cambiarFoco(cbotipofac);
        cambiarFoco(cbotipoorden);
        cambiarFoco(txtcodcli);
        cambiarFoco(txtnombre);
        cambiarFoco(txtdir);
        cambiarFoco(txtidpro);
        cambiarFoco(btnfincli);
        cambiarFoco(txtcan);
        cambiarFoco(txtdesc);
        cambiarFoco(txtcomen);
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

    public void calcSubtotal() throws NumberFormatException {
        //txtsubtot.setValue(Double.parseDouble(txtcan.getValue().toString()) * Double.parseDouble(txtprecio.getValue().toString()));
        subtot = (Double.parseDouble(txtprecio.getValue().toString()) + Double.parseDouble(txtisv.getValue().toString())) * Double.parseDouble(txtcan.getValue().toString());
        txtsubtot.setValue(subtot);
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

    public frmFacturav3(FacturaPK pkf) {
        initComponents();
        //this.setAlwaysOnTop(false);
        detfac.setModel(dt.crearTabla());
        // setSize(this.getToolkit().getScreenSize().width, this.getToolkit().getScreenSize().width);
        txtfecha.setText(formatfac.format(pkf.getFecha()));
        txtnfact.setValue(pkf.getIdFactura());
        pkfact = pkf;
        btnsavefac.setText("Actualizar");
        asginarFocoEnter();
        iniciarFacturacion();
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
        btnfincli = new javax.swing.JButton();
        txtidcli = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtdir = new javax.swing.JTextArea();
        txtcodcli = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtatendido = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
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
        Cerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setTitle("Facturación");
        setFocusTraversalPolicyProvider(true);
        setName("Factura"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1024, 770));
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        setVisible(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameActivated(evt);
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

        btnfincli.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnfincli.setText("Buscar Cliente");
        btnfincli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfincliActionPerformed(evt);
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
                        .addComponent(txtcodcli)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtidcli, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnfincli, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(btnfincli)
                    .addComponent(txtcodcli)
                    .addComponent(jLabel6)
                    .addComponent(txtidcli, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setText("Atendido por:");

        txtatendido.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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
        btnprintfac.setName("Factura"); // NOI18N
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
                .addComponent(btnprintorden)
                .addGap(0, 0, 0)
                .addComponent(btnaddet3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txttot, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
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
                    .addComponent(btnprintorden, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                .addContainerGap())
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
        txtprecio.setModel(new javax.swing.SpinnerNumberModel(0.0f, 0.0f, null, 1.0f));
        txtprecio.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtprecio.setEnabled(false);
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
        txtdesc.setModel(new javax.swing.SpinnerNumberModel(0.0f, 0.0f, null, 0.01f));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setText("Isv:");

        txtisv.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtisv.setEnabled(false);

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel18.setText("Subtotal:");

        txtsubtot.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtsubtot.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 0.0d, 1.0d));
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

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(31, 31, 31)
                        .addComponent(txtprecio, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtdesc, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtisv, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                        .addGap(135, 135, 135)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtsubtot, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtidpro, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addGap(3, 3, 3)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtsubtot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(txtisv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(txtdesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(txtprecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
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
                                .addComponent(cbotipoorden, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(Cerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Cerrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void btnprintordenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprintordenActionPerformed
        // TODO add your handling code here:
        imprimirOrden();
    }//GEN-LAST:event_btnprintordenActionPerformed

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
            //boolean
            g = false;
            Date date = formatfac.parse(txtfecha.getText());
            Factura f1 = jpfac.findFactura(new FacturaPK(Integer.parseInt(txtnfact.getValue().toString()), date));
            if (btnsavefac.getText().equals("Guardar")) {
                txtnfact.setValue(ultimaFact() + 1);
            }
            if (dtf.totalarticulos() == 0) {
                JOptionPane.showMessageDialog(null, "Intente ingresando un producto a la lista!", "La factura no tiene Detalle!", JOptionPane.WARNING_MESSAGE);
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
                    JOptionPane.showMessageDialog(null, "La factura con Nro." + txtnfact.getValue().toString() + " ya Existe!, Intente con otro numero!", "La factura ya existe!", JOptionPane.WARNING_MESSAGE);
                }
            }
            imprimirOrden();
            if (g) {
                imprimirFactura();//Descomentar si quiere imprimir Factura.
            }
            //cerrarForm();
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
        limpiarCamposyTabla();
        this.dispose();
        // this.hide();
        //this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    public void cerrarForm2() {
        for (Frame frame : getFrames()) {//Ciclo recorre todos los Formularios Abiertos
            if (frame.getName().equals("Menu")) {
                Menuprincipal mnp = (Menuprincipal) frame;//
                for (JInternalFrame jifrm : mnp.desktopPane.getAllFrames()) {//Recorre todos los Formularios internos abiertos
                    if (jifrm != null) {
                        if (jifrm.getName().equals("Listadofac")) {
                            frmListadoFacturas lfact = (frmListadoFacturas) jifrm;
                            lfact.cargarFacturas();
                        }
                    }
                }
            }
        }
        this.dispose();
        //this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
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
        if (txtusu.getText().isEmpty()) {
            factura.setUsuario(new Usuario("juan"));
        } else {
            factura.setUsuario(new Usuario(txtusu.getText()));
        }
        factura.setTotalfac(new BigDecimal(dtf.totalarticulos()));
        if (nuevafac == 0) {
            System.out.println(factura);
            jpfac.saveFactura(factura);
            //jpfac.create(factura);
        }
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
                //actualizarExistencia(dtfac, dtftmp);
            } else {
                dtftmp.setStadodet(0);
                //Busca el producto en la tabla
                //Obtiene la existencia actual y rebaja de la existencia
                //Asigna el la nueva existencia y se actualiza.
                //Almacena los cambios
                ///actualizarExistencia(dtfac, dtftmp);
                //Registra el movimiento del prodcuto en un historico para realizar un control
                ////regHistopro("salida", dtftmp.getCantidad(), dtftmp.getProducto());
            }
            //jpdfac.create(dtftmp);
            jpdfac.saveDFactura(dtftmp);
            System.out.println(dtftmp);
        }
        if (nuevafac > 0) {
            System.out.println(factura);
            jpfac.edit(factura);
        }
    }

    public void regHistopro(String t, int ex, String pro) throws NumberFormatException, Exception {
        Historicaproductos hp = new Historicaproductos();
        hp.setFecha(new Date());
        hp.setProducto(pro);
        Integer nex = 0;
        if (t.equals("salida")) {
            nex = -ex;
        } else if (t.equals("ingreso")) {
            nex = ex;
        }
        hp.setTipo(t);
        hp.setEstado((short) 1);
        hp.setCantidad(nex.toString());
        hpro.create(hp);
    }

    public void actualizarExistencia(articulo dtfac, Detallefactura dtftmp) throws Exception {
        //Busca el producto en la tabla
        Producto pupd = finpro.findProducto(dtfac.getCodigo());
        //Obtiene la existencia actual y rebaja de la existencia
        int cantmp = pupd.getCantidadexis();
        //Asigna el la nueva existencia y se actualiza.
        pupd.setCantidadexis(cantmp - dtftmp.getCantidad());
        //Almacena los cambios
        finpro.edit(pupd);
    }

    public void updateFactura(Date date, BigDecimal efe, Factura f1) throws NumberFormatException, Exception {
        //En caso de ser modificada la factura y agregar un producto el total se calcula mejor al final
        //pkfact = new FacturaPK(Integer.parseInt(txtnfact.getValue().toString()), date);
        factura.setFacturaPK(pkfact);
        factura.setEfectivo(efe);
        Tipofactura seltpf = ((Tipofactura) cbotipofac.getSelectedItem());
        Empresa selemp = (Empresa) cbotienda.getSelectedItem();
        Tiporden seltipo = (Tiporden) cbotipoorden.getSelectedItem();
        //clisel = f1.getCliente();
        setClientefac();
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
                //Busca el producto en la tabla
                actualizarExistencia(dtfac, dtftmp);
                //Obtiene la existencia actual y rebaja de la existencia
                //Asigna el la nueva existencia y se actualiza.
                //Almacena los cambios
            }
            jpdfac.edit(dtftmp);
            System.out.println(dtftmp);
        }
        //Una vez elimnada la fila, calcula nuevamente el total
        factura.setTotalfac(new BigDecimal(dtf.totalarticulosISV()));
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
            Date date = formatfac.parse(txtfecha.getText());
            if (btnsavefac.getText().equals("Guardar")) {
                txtnfact.setValue(ultimaFact() + 1);
            }
            pkfact = new FacturaPK(Integer.parseInt(txtnfact.getValue().toString()), date);
            Factura f1 = jpfac.findFactura(pkfact);
            //jpfac.findFactura(new FacturaPK(Integer.parseInt(txtnfact.getValue().toString()), date));
            if (dtf.totalarticulos() == 0) {
                JOptionPane.showMessageDialog(null, "Intente ingresando un producto a la lista!", "La factura no tiene Detalle!", JOptionPane.WARNING_MESSAGE);
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
                    JOptionPane.showMessageDialog(null, "La factura con Nro." + txtnfact.getValue().toString() + " ya Existe!, Intente con otro numero!", "La factura ya existe!", JOptionPane.WARNING_MESSAGE);
                }
            }
            if (corden == 0) {
                imprimirOrden();
            }
        } catch (Exception e) {
            System.out.println("Error->" + e.getMessage());
        }
    }

    private void btnaddetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddetActionPerformed
        addprolista();
    }//GEN-LAST:event_btnaddetActionPerformed

    public void addprolista() {
        // TODO add your handling code here:
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
                    padd.setPrecio(BigDecimal.valueOf(prosel.getPrecio().doubleValue() - Double.parseDouble(txtisv.getValue().toString())));
                    calcSubtotal();
                    padd.setSubtotal(Double.parseDouble(txtsubtot.getValue().toString()));
                    padd.setEstado("0");
                    padd.setComentarios(txtcomen.getText());
                    addproTableV3(padd);
                    subtot = 0.0;
                    ///guardaroupdateFactura();
                    txtidpro.requestFocus();
                    this.btnaddet.setEnabled(true);
                }

            }
        } catch (Exception e) {
            System.out.println("Error->" + e.getMessage());
        }
    }

    public void buscarProducto() {
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
                    JOptionPane.showMessageDialog(null, "La factura ya esta cerrada!", "Consulte al Administrador.", JOptionPane.WARNING_MESSAGE);

                }
            } else {
                JOptionPane.showMessageDialog(null, "Debe ingresar un Cliente", "El Cliente no ha sido Ingresado!", JOptionPane.WARNING_MESSAGE);
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
                    txtnompro.setText(prosel.getNombre());
                    txtprecio.setValue(prosel.getPrecio() / (1 + prosel.getImpuesto().doubleValue()));
                    txtisv.setValue((prosel.getPrecio() / (1 + prosel.getImpuesto().doubleValue())) * prosel.getImpuesto().doubleValue());
                    subtot = ((Double.parseDouble(txtprecio.getValue().toString()) * Double.parseDouble(txtcan.getValue().toString())) + Double.parseDouble(txtisv.getValue().toString()));
                    txtsubtot.setValue(subtot);
                } else {
                    JOptionPane.showMessageDialog(null, "La factura ya esta cerrada!", "Consulte al Administrador.", JOptionPane.WARNING_MESSAGE);

                }
                //((panelDetV3) jdet.getContentPane()).emFac.close();
            } else {
                JOptionPane.showMessageDialog(null, "Debe ingresar un Cliente", "El Cliente no ha sido Ingresado!", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

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

    public void addproTableV3(articulo art) {
        padd = art;
        if (padd.getCodigo().length() > 0) {
            /* padd.setCodigo(txtidpro.getText());
            padd.setNombre(txtnompro.getText());
            padd.setCantidad(Double.parseDouble(txtcan.getValue().toString()));
            padd.setDescuento(Double.parseDouble(txtdesc.getValue().toString()));
            padd.setIsv((art.isIsv()) ? true : false);
            padd.setPrecio(Double.parseDouble(txtprecio.getValue().toString()));
            padd.setValisv((art.isIsv()) ? Double.parseDouble(txtisv.getValue().toString()) : 0);
            padd.setEstado("0");
            padd.setComentarios(txtcomen.getText());*/
            dtf.addPArticulos(padd);
            txttot.setValue(dtf.totalarticulosISV());
            txttot.setText("" + dtf.totalarticulosISV());
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
            padd.setDescuento(deta.getDescuento().doubleValue());
            padd.setValisv(deta.getIsv().doubleValue());
            subtot = (deta.getIsv().doubleValue() + deta.getPrecio().doubleValue()) * deta.getCantidad();
            padd.setSubtotal(subtot);
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
        //txttot.setValue(dtf.totalarticulosISV());
    }

    private void cbotipofacKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbotipofacKeyReleased
        //MostrarDatoscel(txtidcli);
    }//GEN-LAST:event_cbotipofacKeyReleased

    private void txtidcliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtidcliKeyReleased
        // TODO add your handling code here:
        switch (evt.getKeyCode()) {
            case com.sun.glass.events.KeyEvent.VK_F4:
                abrirBuscacli();
                // dialogBuscaCli();
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
        // iniciarFacturacion();
    }//GEN-LAST:event_formInternalFrameOpened

    public void iniciarFacturacion() {
        // TODO add your handling code here:
        try {
            //emFac = Persistence.createEntityManagerFactory("Restorant");
            fincli = new ClienteJpaController(emFac);
            ltipfac = new TipofacturaJpaController(emFac);
            ltipor = new TipordenJpaController(emFac);
            lempre = new EmpresaJpaController(emFac);
            jpfac = new FacturaJpaController(emFac);
            jpdfac = new DetallefacturaJpaController(emFac);
            jpusu = new UsuarioJpaController(emFac);
            finpro = new ProductoJpaController(emFac);
            pcorrela = new CorrelativossarJpaController(emFac);
            hpro = new HistoricaproductosJpaController(emFac);
            lcorre = new Correlativossar();
            frmbuscacli = new frmBusclientes();
            report = new EjecutarReporte();

            //MostrarDatoscel(txtidcli);
            //Carga los Combobox
            cbotipoorden.setModel(this.ObtenerListaTipoor());
            cbotienda.setModel(this.ObtenerListaEmp());
            cbotipofac.setModel(this.ObtenerListaTipofac());
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
                cbotipoorden.setSelectedItem(factura.getIdorden());
                txtidcli.setSelectedItem(factura.getCliente());
                txtnombre.setText(factura.getCliente().getNombre());
                txtdir.setText(factura.getCliente().getDireccion());
                txttot.setText("" + factura.getTotalfac());
                addproTable2(lsdetfac);
            }

            lcorre = pcorrela.ultimoCorrela();
            if ((Integer.parseInt(txtnfact.getValue().toString())) > (lcorre.getRangofin() - 201)) {
                JOptionPane.showMessageDialog(null, "Los correlativos estan por agotarse actualmente tiene 200 dispobiles", "Advertencia!", JOptionPane.WARNING_MESSAGE);
            }
            if ((Integer.parseInt(txtnfact.getValue().toString())) == (lcorre.getRangofin())) {
                JOptionPane.showMessageDialog(null, "Los correlativos se agotaron no tiene dispobiles, facture manualmente!, \n Llame al administrador del sistema.", "Advertencia!", JOptionPane.WARNING_MESSAGE);
                cerrarForm();
            }
            Date factual = new Date();
            // if ( (factual-lcorre.getFechafin())==15)) {
            //      JOptionPane.showMessageDialog(this, "La fecha de caducidad de los correlativos esta prixima.","Advertencia!", JOptionPane.WARNING_MESSAGE);
            // }
            //  cbotipofac.requestFocus();
        } catch (Exception e) {
            System.out.println("Error->" + e.getMessage());
        }
    }

    private Integer ultimaFact() {
        Integer x = 0;
        try {
            Query qsql = fincli.getEntityManager().createNativeQuery("select * from factura c where idfactura=(select max(idfactura) from factura)", Factura.class);//qsql.setParameter("n", txtnom.getText());
            qsql.setHint(QueryHints.REFRESH, HintValues.TRUE);//Actualiza
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
                cerrarForm();
                break;
            default:
                System.out.println("");
                ;
        }
    }//GEN-LAST:event_formKeyReleased

    private void btnfincliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfincliActionPerformed
        //dialogBuscaCli();
        //abrirBuscacli();
    }//GEN-LAST:event_btnfincliActionPerformed

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
            ((panelFindcli) jdet.getContentPane()).emFac.close();
        } catch (Exception e) {
            System.out.println("Error->" + e.getMessage());
        }
    }

    public void dialogBuscaCliv2() {
        buscarClientesv2 frmfCli2 = new buscarClientesv2();
        //frmlistf.setVisible(true);
        this.getDesktopPane().add(frmfCli2);
        frmfCli2.setLocation(this.getDesktopPane().getWidth() / 4, 0);
        frmfCli2.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frmfCli2.setClosable(true);
        frmfCli2.setEnabled(true);
        frmfCli2.setVisible(true);
        frmfCli2.pack();
        frmfCli2.show();

    }


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
                                temp.removeRow(detfac.getSelectedRow());
                                txttot.setValue(dtf.totalarticulos());
                                guardarFacturaOpen(null, 1);
                            } catch (Exception ex) {
                                Logger.getLogger(frmFacturav3.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } catch (NonexistentEntityException ex) {
                            Logger.getLogger(frmFacturav3.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No tiene permisos para quitar filas, consulte al administrador!", "Error!", JOptionPane.WARNING_MESSAGE);
                }
            }

        }
    }//GEN-LAST:event_detfacKeyReleased

    private void btnprintfacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprintfacActionPerformed
        try {
            Date date = formatfac.parse(txtfecha.getText());
            //JOptionPane.showMessageDialog(rootPane,"Factura->"+date.toString()+"->"+txtnfact.getValue()+"->"+LocalDate.now());
            JDialog jdet = new JDialog();
            Integer nfa = Integer.parseInt(txtnfact.getValue().toString());
            BigDecimal totsen = new BigDecimal(txttot.getText());
            jdet.setContentPane(new panelPagofactura(nfa, date, totsen));
            jdet.setLocation(200, 200);
            jdet.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jdet.pack();
            jdet.setFocusableWindowState(true);
            jdet.setModal(true);
            ((panelPagofactura) jdet.getContentPane()).txtefectivo.requestFocus();
            jdet.setVisible(true);
            ((panelPagofactura) jdet.getContentPane()).txtefectivo.requestFocus();
            if (((panelPagofactura) jdet.getContentPane()).pago == true) {
                if (guardarFactura(((panelPagofactura) jdet.getContentPane()).efectivo)) {
                    return;
                }
            } else {
                guardarFacturaOpen(((panelPagofactura) jdet.getContentPane()).efectivo, 0);
            }
            cerrarForm();
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

    public void limpiarCampos() {
        txtcan.setValue(BigDecimal.valueOf(1.00));
        txtprecio.setValue(BigDecimal.valueOf(0.00));
        txtsubtot.setValue(BigDecimal.valueOf(0.00));
        txtdesc.setValue(BigDecimal.valueOf(0.00));
        txtisv.setValue(BigDecimal.valueOf(0.00));
        txtcomen.setText("");
        txtnompro.setText("");
    }

    public void limpiarCamposyTabla() {
        this.updateUI();
        if (this.emFac.isOpen()) {
            this.emFac.close();
        }

    }

    public Integer contarPagoIndi(String txtfil) {
        Integer x = 0;
        try {
            String pt = txtfil;
            Query qsql = jpdfac.getEntityManager().createNativeQuery("select count(*) from detallefactura c where pedindi is null and c.idfactura=" + pt);
            x = Integer.parseInt(qsql.getResultList().get(0).toString());
        } catch (Exception e) {
            System.out.println("Error->" + e.getMessage());
        }
        return x;
    }

    private void ProductoFilcod() {
        limpiarCampos();
        try {
            String pt = txtidpro.getText();
            Query qsql = finpro.getEntityManager().createNativeQuery("select * from producto c where upper(c.idproducto)='" + pt.toUpperCase().trim() + "'", Producto.class);//qsql.setParameter("n", txtnom.getText());
            prosel = (Producto) qsql.getSingleResult();
            if (prosel != null) {
                txtnompro.setText(prosel.getNombre());
                //txtprecio.setValue(prosel.getPrecio() - (prosel.getPrecio() * prosel.getImpuesto().doubleValue()));
                //txtisv.setValue(prosel.getPrecio() * prosel.getImpuesto().doubleValue());
                //subtot = ((Double.parseDouble(txtprecio.getValue().toString()) * Double.parseDouble(txtcan.getValue().toString())) + Double.parseDouble(txtisv.getValue().toString()));
                //txtsubtot.setValue(subtot);
                txtprecio.setValue(prosel.getPrecio() / (1 + prosel.getImpuesto().doubleValue()));
                txtisv.setValue((prosel.getPrecio() / (1 + prosel.getImpuesto().doubleValue())) * prosel.getImpuesto().doubleValue());
                subtot = ((Double.parseDouble(txtprecio.getValue().toString()) * Double.parseDouble(txtcan.getValue().toString())) + Double.parseDouble(txtisv.getValue().toString()));
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
        setClientefac();
    }//GEN-LAST:event_txtcodcliFocusLost

    public void setClientefac() throws HeadlessException {
        // TODO add your handling code here:
        txtcodcli.setBackground(Color.WHITE);
        if (!txtcodcli.getText().isEmpty()) {
            clisel = Filcodcli(txtcodcli.getText());
            if (clisel.getIdCliente() != null) {
                txtnombre.setText(clisel.getNombre());
                txtdir.setText(clisel.getDireccion());
            } else {
                JOptionPane.showMessageDialog(null, "El Cliente no existe, pruebe otro codigo", "Error!", JOptionPane.WARNING_MESSAGE);
                txtcodcli.select(0, txtcodcli.getText().length());
                txtcodcli.requestFocus();
            }
        }
    }

    private void txtcodcliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcodcliKeyReleased
        cerrarconESC(evt);
        if (evt.getKeyCode() == KeyEvent.VK_F4) {
            buspro = 1;
            dialogBuscaCli();
            //dialogBuscaCliv2();
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
        // TODO add your handling code here:
        if (prosel != null) {
            txtcan.requestFocus();
            txtidpro.setBackground(Color.WHITE);
        } else {
            if (buspro == 0) {
                JOptionPane.showMessageDialog(null, "El producto no existe, pruebe otro codigo", "Error!", JOptionPane.WARNING_MESSAGE);
                txtidpro.select(0, txtcodcli.getText().length());
                txtidpro.requestFocus();
            } else {
                buspro = 1;
            }
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
        //cerrarForm();
        if (JOptionPane.showInternalConfirmDialog(getRootPane(), "Esta seguro de salir, sino a Guarda o Actualiza Perdera la Informacion?", "Saliendo de la Factura!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            cerrarForm();
        } else {
            //this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_CerrarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        //iniciarFacturacion();

    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        //if (JOptionPane.showInternalConfirmDialog(null, "Esta seguro de salir, sino a Guarda o Actualiza Perdera la Informacion?", "Saliendo de la Factura!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
        cerrarForm();
        //}
    }//GEN-LAST:event_formWindowClosing

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        // iniciarFacturacion();
    }//GEN-LAST:event_formWindowActivated

    private void txtidproActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidproActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidproActionPerformed

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        // TODO add your handling code here:
        iniciarFacturacion();
    }//GEN-LAST:event_formInternalFrameActivated

    public void imprimirFactura() {
        // TODO add your handling code here:
        try {
            Integer id = Integer.parseInt(txtnfact.getValue().toString());
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

    public void imprimirOrden() {
        // TODO add your handling code here:
        try {
            Integer id = Integer.parseInt(txtnfact.getValue().toString());
            if (id > 0) {
                if (jpdfac.getDetestadoCount(id) > 0) {
                    report.startOrdenCocinaprint("" + id);
                    System.out.println("" + jpdfac.actualizarEstadodet(id));
                } else {
                    JOptionPane.showMessageDialog(null, "No hay Ordenes de Cocina pendientes!", "Orden de Cocina", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception e) {
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
            java.util.logging.Logger.getLogger(frmFacturav3.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmFacturav3.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmFacturav3.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmFacturav3.class
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmFacturav3().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Cerrar;
    public javax.swing.JButton btnaddet;
    private javax.swing.JButton btnaddet3;
    private javax.swing.JButton btnfincli;
    public javax.swing.JButton btnprintfac;
    private javax.swing.JButton btnprintorden;
    public javax.swing.JButton btnsavefac;
    private javax.swing.JComboBox<String> cbotienda;
    private javax.swing.JComboBox<String> cbotipofac;
    private javax.swing.JComboBox<String> cbotipoorden;
    private javax.swing.JTable detfac;
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
    private javax.swing.JLabel jLabel5;
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
    private javax.swing.JTextField txtatendido;
    private javax.swing.JSpinner txtcan;
    public javax.swing.JTextField txtcodcli;
    private javax.swing.JTextField txtcomen;
    private javax.swing.JSpinner txtdesc;
    private javax.swing.JTextArea txtdir;
    private javax.swing.JFormattedTextField txtfecha;
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

    private void abrirBuscacli() {
        ((Menuprincipal) this.getParent()).abrirBuscacli();
    }


}
