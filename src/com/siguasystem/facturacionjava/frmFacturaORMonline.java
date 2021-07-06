/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siguasystem.facturacionjava;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.siguasystem.modelo.Correlativossar;
import com.siguasystem.modelo.Cliente;
import com.siguasystem.modelo.ClienteJpaController;
import com.siguasystem.modelo.Correlativossar2;
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
import com.siguasystem.modelo.Producto;
import com.siguasystem.modelo.ProductoJpaController;
import com.siguasystem.modelo.Tipofactura;
import com.siguasystem.modelo.TipofacturaJpaController;
import com.siguasystem.modelo.Tiporden;
import com.siguasystem.modelo.TipordenJpaController;
import com.siguasystem.modelo.Usuario;
import com.siguasystem.modelo.UsuarioJpaController;
import com.siguasystem.modelo.pedidos.PedidosJpaController;
import com.siguasystem.modelos.exceptions.NonexistentEntityException;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

/**
 *
 * @author juan
 */
public class frmFacturaORMonline extends javax.swing.JInternalFrame implements ActionListener {

    /**
     * Creates new form frmFactura
     */
    //Establece la conexion y con que SBD se trabajara
    private ConnectionSource connectionSource;//= new JdbcConnectionSource("jdbc:mysql://192.168.0.103:3306/bdrestaurante", "root", "cantones");
    //Conexion JPA
    EntityManagerFactory emPed = Persistence.createEntityManagerFactory("pedidosOnline");
    EntityManagerFactory emFac = Persistence.createEntityManagerFactory("Restorant");
    EntityManagerFactory emFaclocal = Persistence.createEntityManagerFactory("Restorant");
    PedidosJpaController jpa = new PedidosJpaController(emPed);
    Tabladetfac dt = new Tabladetfac();
    frmDetalle frmdt;
    String factual = DateFormat.getDateInstance().format(new Date()).toString();
    DateFormat formatfac = new SimpleDateFormat("dd/MM/yyyy", Locale.US);//);
    detallefactura dtf = new detallefactura();
    articulo padd;
    Factura factura = new Factura();
    frmBusclientes frmbuscacli = null;//new frmBusclientes();
    EjecutarReporte report = new EjecutarReporte();
    FacturaPK pkfact;//Toma valor si la factura existen
    DetallefacturaPK pkdfact;//Toma valor si el detalle existe
    Integer nuevafac = 0;
    Integer estado = 1;
    Integer buspro = 0;
    private Double subtot = 0.0;
    private int idcorrelativo=0;
    private int ped=0;
    private String idpedido="";

    private Correlativossar lcorre = new Correlativossar();
    private Correlativossar2 lcorre2 = new Correlativossar2();
    private List<Cliente> lscli = new ArrayList<Cliente>();
    private List<Detallefactura> lsdetfac = new ArrayList<Detallefactura>();
    private List<Usuario> lsusuario;
    private Cliente clisel;
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
    FileReader fr = null;
    public String[] conexion_data = new String[3];
    String alerta = "";
    String key = "01EE31A79FXXB2A3"; //llave
    String iv = "0123456789ABCDEF"; // vector de inicialización
    Thread h1;//=new Thread();
    boolean ejecuta = true;
    String coordenadascli="";
    private Integer facturaid=0;

    public frmFacturaORMonline() {
        try {
            initComponents();
            try {
                fr = new FileReader("conexion.dat");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(frmFacturaORMonline.class.getName()).log(Level.SEVERE, null, ex);
            }
            BufferedReader br = new BufferedReader(fr);
            String linea = "";
            int number = 0;
            try {
                while ((linea = br.readLine()) != null) {
                    conexion_data[number] = linea.substring(linea.indexOf("=") + 1, linea.length());
                    number++;
                    if (number > 2) {
                        break;
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(frmFacturaORMonline.class.getName()).log(Level.SEVERE, null, ex);
            }
            String clv = "";
            try {
                clv = StringEncrypt.decrypt(key, iv, StringEncrypt.encrypt(key, iv, conexion_data[2]));
            } catch (Exception ex) {
                Logger.getLogger(frmFacturaORMonline.class.getName()).log(Level.SEVERE, null, ex);
            }
            connectionSource = new JdbcConnectionSource(conexion_data[0], conexion_data[1], clv);
//new JdbcConnectionSource("jdbc:mysql://192.168.0.103:3306/bdrestaurante", "root", "cantones");     
            detfac.setModel(dt.crearTabla());
            // setSize(this.getToolkit().getScreenSize().width, this.getToolkit().getScreenSize().width);
            txtfecha.setText(formatfac.format(new Date()));
            cambiarFoco(this);
            tamanioCol();
            ejecutarHilo();
            txtcan.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    if (Double.parseDouble(txtcan.getValue().toString()) > 0) {
                        calcSubtotal();
                    }
                }
            });
            jButton1.setEnabled(false);
        } catch (SQLException ex) {
            Logger.getLogger(frmFacturaORMonline.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public frmFacturaORMonline(detallefactura lpedido,Menuprincipal mn,int ped,String cli) {
        try {
            initComponents();
            try {
                fr = new FileReader("conexion.dat");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(frmFacturaORMonline.class.getName()).log(Level.SEVERE, null, ex);
            };
            connectionSource = new JdbcConnectionSource(mn.conexion_data[0], mn.conexion_data[1],  mn.conexion_data[2]);
//new JdbcConnectionSource("jdbc:mysql://192.168.0.103:3306/bdrestaurante", "root", "cantones");     
            detfac.setModel(dt.crearTabla());
            // setSize(this.getToolkit().getScreenSize().width, this.getToolkit().getScreenSize().width);
            txtfecha.setText(formatfac.format(new Date()));
            cambiarFoco(this);
            tamanioCol();
            ejecutarHilo();
            txtcan.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    if (Double.parseDouble(txtcan.getValue().toString()) > 0) {
                        calcSubtotal();
                    }
                }
            });
            jButton1.setEnabled(false);
            this.ped=ped;
            this.dtf=lpedido;
            txtcodcli.setText(cli);
        } catch (SQLException ex) {
            Logger.getLogger(frmFacturaORMonline.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
     public frmFacturaORMonline(detallefactura lpedido,MenuprincipalPedidosOnline mn,int ped,String cli,String nomcli,String coordenadas,String idpedido) {
        try {
            initComponents();
            this.idpedido=idpedido;
            try {
                fr = new FileReader("conexion.dat");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(frmFacturaORMonline.class.getName()).log(Level.SEVERE, null, ex);
            };
            connectionSource = new JdbcConnectionSource(mn.conexion_data[0], mn.conexion_data[1],  mn.conexion_data[2]);
//new JdbcConnectionSource("jdbc:mysql://192.168.0.103:3306/bdrestaurante", "root", "cantones");     
            detfac.setModel(dt.crearTabla());
            // setSize(this.getToolkit().getScreenSize().width, this.getToolkit().getScreenSize().width);
            txtfecha.setText(formatfac.format(new Date()));
            cambiarFoco(this);
            tamanioCol();
            ejecutarHilo();
            txtcan.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    if (Double.parseDouble(txtcan.getValue().toString()) > 0) {
                        calcSubtotal();
                    }
                }
            });
            jButton1.setEnabled(false);
            this.ped=ped;
            this.dtf=lpedido;
            txtcodcli.setText(cli);
            txtnombre.setText(nomcli);
            txtdir.setText(coordenadas);
            coordenadascli=coordenadas;
        } catch (SQLException ex) {
            Logger.getLogger(frmFacturaORMonline.class.getName()).log(Level.SEVERE, null, ex);
        }

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
        subtot = (Double.parseDouble(txtprecio.getValue().toString()) + Double.parseDouble(txtisv.getValue().toString())) * Double.parseDouble(txtcan.getValue().toString());
        txtsubtot.setValue(subtot);
    }
    
    public void calcSubtotal(double can,double pre,double isv) throws NumberFormatException {
        //txtsubtot.setValue(Double.parseDouble(txtcan.getValue().toString()) * Double.parseDouble(txtprecio.getValue().toString()));
        subtot = (pre + isv) * can;
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

    public frmFacturaORMonline(FacturaPK pkf) {
        try {
            initComponents();
            try {
                fr = new FileReader("conexion.dat");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(frmFacturaORMonline.class.getName()).log(Level.SEVERE, null, ex);
            }
            BufferedReader br = new BufferedReader(fr);
            String linea = "";
            int number = 0;
            try {
                while ((linea = br.readLine()) != null) {
                    conexion_data[number] = linea.substring(linea.indexOf("=") + 1, linea.length());
                    number++;
                    if (number > 2) {
                        break;
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(frmFacturaORMonline.class.getName()).log(Level.SEVERE, null, ex);
            }
            connectionSource = new JdbcConnectionSource(conexion_data[0], conexion_data[1], conexion_data[2]);
            //new JdbcConnectionSource("jdbc:mysql://localhost:3306/bdrestaurante", "root", "linuxdeb");
            detfac.setModel(dt.crearTabla());
            txtfecha.setText(formatfac.format(pkf.getFecha()));
            txtnfact.setValue(pkf.getIdFactura());
            pkfact = pkf;
            btnsavefac.setText("Actualizar");
            cambiarFoco(this);
            tamanioCol();
            ejecutarHilo();
        } catch (SQLException ex) {
            Logger.getLogger(frmFacturaORMonline.class.getName()).log(Level.SEVERE, null, ex);
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
        txtcodcli = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
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
        txtatendido = new javax.swing.JComboBox<>();

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
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(137, 137, 137)
                                .addComponent(txtidcli, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(txtcodcli)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(txtidcli, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(txtcodcli)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
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

        txtatendido.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        txtatendido.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                txtatendidoItemStateChanged(evt);
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
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtusu)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtatendido, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField7)
                                .addGap(13, 13, 13))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(381, 381, 381)
                                .addComponent(Cerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))))
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
                    .addComponent(jLabel16)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtatendido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Cerrar, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))
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
        boolean g = false;
        try {
           
            Date date = formatfac.parse(txtfecha.getText());
            Factura f1 = jpfac.findFactura(facturaid);
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
            imprimirOrden();
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
        return g;
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
        //Menuprincipal mp = (Menuprincipal) getTopLevelAncestor();
        //mp.lblalerta.setText(alerta);
        //ejecuta = false;
        this.dispose();
        //}
    }

    public void saveFactura(Date date, BigDecimal efe) throws NumberFormatException, Exception {
        //pkfact = new FacturaPK(Integer.parseInt(txtnfact.getValue().toString()), date);
        pkfact = new FacturaPK();
        //factura.setFacturaPK(pkfact);
        factura.setEfectivo(efe);
        Tipofactura seltpf = ((Tipofactura) cbotipofac.getSelectedItem());
        Empresa selemp = (Empresa) cbotienda.getSelectedItem();
        Tiporden seltipo = (Tiporden) cbotipoorden.getSelectedItem();
        if (clisel==null) {
             getCliente();
        }
         factura.setCliente(clisel);     
         factura.setIdcorrelativo(idcorrelativo);
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
            factura.setIdFactura(Integer.parseInt(txtnfact.getValue().toString()));
            factura.setFecha(date);
            System.out.println(factura);
            Factura f1 = jpfac.findFactura(facturaid);
            if (f1 == null) {
                //Cuando es una factura Nueva
                jpfac.create(factura);
            } else {
                txtnfact.setValue(ultimaFact() + 1);
                factura.setIdFactura(factura.getIdFactura());
                factura.setFecha(date);
                System.out.println(factura);
                jpfac.create(factura);
            }

        }
        //JOptionPane.showMessageDialog(rootPane, "Factura Almacenada! ->" + date);
        for (articulo dtfac : dtf.getLParticulos()) {
            Detallefactura dtftmp = new Detallefactura();
            dtftmp.setDetallefacturaPK(new DetallefacturaPK(dtfac.getId(), factura.getIdFactura(), factura.getFecha()));
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
             //Actualiza el pedido seleccionado, se debe dar
             //doble clic al pedido para procesarlo
             actualizarPedidoonline();
            System.out.println(dtftmp);
        }
        if (nuevafac > 0) {
            System.out.println(factura);
            jpfac.edit(factura);
        }
    }
    
    public void actualizarPedidoonline() throws Exception, NumberFormatException {
        com.siguasystem.modelo.pedidos.Pedidos ped = jpa.findPedidos(Long.parseLong(idpedido));
        ped.setEstado((short) 1);
        jpa.edit(ped);
    }

    public void updateFactura(Date date, BigDecimal efe, Factura f1) throws NumberFormatException, Exception {
        //En caso de ser modificada la factura y agregar un producto el total se calcula mejor al final
        //pkfact = new FacturaPK(Integer.parseInt(txtnfact.getValue().toString()), date);
        //factura.setFacturaPK(pkfact);
        factura.setIdFactura(pkfact.getIdFactura());
        factura.setFecha(pkfact.getFecha());
        factura.setEfectivo(efe);
        Tipofactura seltpf = ((Tipofactura) cbotipofac.getSelectedItem());
        Empresa selemp = (Empresa) cbotienda.getSelectedItem();
        Tiporden seltipo = (Tiporden) cbotipoorden.getSelectedItem();
        if (clisel == null) {
            clisel = f1.getCliente();
        }
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
                 jpdfac.edit(dtftmp);
            } else {
                dtftmp.setStadodet(0);
                 jpdfac.create(dtftmp);
            }
            //jpdfac.edit(dtftmp);
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
            Factura f1 = null;
            if (btnsavefac.getText().equals("Guardar")) {
                txtnfact.setValue(ultimaFact() + 1);
            } else {
                pkfact = new FacturaPK(Integer.parseInt(txtnfact.getValue().toString()), date,0);
                f1 = jpfac.findFactura(facturaid);
            }
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
            /*else {
                if (btnsavefac.getText().equals("Guardar")) {
                    saveFactura(date, efe);
                    //Si la factura se almaceno correctamente se imprime la factura
                    btnsavefac.setEnabled(false);
                    btnprintfac.setEnabled(false);
                    imprimirFactura();
                } else {
                    JOptionPane.showMessageDialog(this, "La factura con Nro." + txtnfact.getValue().toString() + " ya Existe!, Intente con otro numero!", "La factura ya existe!", JOptionPane.WARNING_MESSAGE);
                }
            }*/
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
    
    public void addproPedTableV3(detallefactura dtfac) {
        for (articulo art: dtfac.getLParticulos()) {
            padd = art;
        if (padd.getCodigo().length() > 0) {
            try {
                DefaultTableModel temp = (DefaultTableModel) detfac.getModel();//padd.setIsv(false);
                Object nuevo[] = {padd.getId(), padd.getCodigo(), padd.getNombre(), padd.getComentarios(), String.format("%,.2f", padd.getCantidad()),
                    String.format("%,.2f", padd.getPrecio()), String.format("%,.2f", padd.getDescuento()), String.format("%,.2f", padd.getValisv()), String.format("%,.2f", padd.getSubtotal())};
                temp.addRow(nuevo);
            } catch (Exception e) {
                System.out.println("" + e.getMessage());
            }
        }
        }
         txttot.setValue(dtf.totalarticulosISV());
         txttot.setText("" + dtf.totalarticulosISV());
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
            subtot = (deta.getPrecio().doubleValue()) * deta.getCantidad();
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
        iniciarFactura();
    }//GEN-LAST:event_formInternalFrameOpened

    public void iniciarFactura() {
        // TODO add your handling code here:
        try {
            fincli = new ClienteJpaController(emFac);
            //ltipfac = new TipofacturaJpaController(emFac);
            //ltipor = new TipordenJpaController(emFac);
            //lempre = new EmpresaJpaController(emFac);
            jpfac = new FacturaJpaController(emFaclocal);
            jpdfac = new DetallefacturaJpaController(emFaclocal);
            jpusu = new UsuarioJpaController(emFac);
            finpro = new ProductoJpaController(emFac);
            pcorrela = new CorrelativossarJpaController(emFaclocal);
            //MostrarDatoscel(txtidcli);
            //Carga los Combobox
            cbotipoorden.setModel(this.ObtenerListaTipoorv2());
            cbotienda.setModel(this.ObtenerListaEmpORM());
            cbotipofac.setModel(this.ObtenerListaTipofacORM());
            txtatendido.setModel(this.ObtenerListausuORM());
            //Obtiene el ultimo correlativo activo valido
             lcorre = pcorrela.ultimoCorrela();
            if (nuevafac == 0) {
                txtnfact.setValue(ultimaFact() + 1);
                 //Ayuda a controlar formato de correlativo a
                //imprimir con su fecha de vencimiento.
                idcorrelativo=lcorre.getId();
            } else {
                txtnfact.setValue(nuevafac);
                txtnfact.setEnabled(false);
                //Carga la factura existente
                factura = jpfac.findFactura(facturaid);
                //Carga el detalle existente
                lsdetfac = jpdfac.findDetallefacturaEntities2(new DetallefacturaPK(pkfact.getIdFactura(), pkfact.getFecha()));
                cbotienda.setSelectedItem(factura.getEmpresa());
                cbotipofac.setSelectedItem(factura.getTipofact());
                cbotipoorden.setSelectedItem(factura.getIdorden());
                txtidcli.setSelectedItem(factura.getCliente());
                txtnombre.setText(factura.getCliente().getNombre());
                txtdir.setText(factura.getCliente().getDireccion());
                txttot.setText("" + factura.getTotalfac());
                //Ayuda a controlar formato de correlativo a
                //imprimir con su fecha de vencimiento.
                idcorrelativo=factura.getIdcorrelativo();
                addproTable2(lsdetfac);
            }
            cbotipofac.requestFocus();
            Integer uf = ultimaFact();
                     
            if ((lcorre.getRangofin() - lcorre.getAlernfac()) < uf) {
                JOptionPane.showMessageDialog(this, "Los correlativos estan por agotarse actualmente tiene " + (lcorre.getRangofin() - uf) + " dispobiles", "Advertencia!", JOptionPane.WARNING_MESSAGE);
                alerta = "Los correlativos estan por agotarse actualmente tiene " + (lcorre.getRangofin() - uf) + " dispobiles.";
            }
            if (uf == (lcorre.getRangofin())) {
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
            int diffecha = diferenciaEnDias2(lcorre.getFechafin(), factual, Integer.parseInt(fe1[2].substring(0, 4)));
            /*d1 >= d2 && (factual.getYear() <= lcorre.getFechafin().getYear()
                    && factual.getMonth() <= lcorre.getFechafin().getMonth())*/
            if (diffecha > 0 && diffecha <= 10) {
                JOptionPane.showMessageDialog(this, "La fecha de caducidad de los correlativos es el " + lcorre.getFechafin() + ".\nDebe actualizar los correlativos muy pronto.", "Advertencia!", JOptionPane.WARNING_MESSAGE);
                alerta = "La fecha de caducidad de los correlativos es el " + lcorre.getFechafin() + ".\nDebe actualizar los correlativos muy pronto.";
            }
            if (diffecha <= 0) {
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
            MenuprincipalPedidosOnline mp = (MenuprincipalPedidosOnline ) getTopLevelAncestor();
            mp.lblalerta.setText(alerta);
           //Identifica que los productos son tomados desde el pedido
           //online
            if (ped==1) {
                addproPedTableV3(dtf);
                calcSubtotal();
            }
        } catch (Exception e) {
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
            Correlativossar cor = (Correlativossar) qsql2.getResultList().get(0);
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

    private Integer ultimaFactorm() {
        Factura x = null;
        Integer r = 0;
        try {
            Dao<Factura, String> facDao = DaoManager.createDao(connectionSource, Factura.class);
            // find out how many orders account-id #10 has
            GenericRawResults<String[]> rawResults
                    = facDao.queryRaw("select * from factura c where idfactura=(select max(idfactura) from factura)");
            ///Verificar en la tabla correlativos cual es el ultimo numero
            ///Debido a que las facturas se pueden vencer por fecha o por
            /// que llegaron al limite de numeracion.
            GenericRawResults<String[]> rawResultsCorre
                    = facDao.queryRaw("SELECT * FROM correlativossar c where c.estado=1 order by fechacreacion DESC");
            List<String[]> results = rawResults.getResults();
            List<String[]> results2 = rawResultsCorre.getResults();
            String[] resultArray = results.get(0);
            String[] resultArray2 = results2.get(0);
            //valida si el numero de factura inicial es menor al ultimo numero 
            // de factura realizado y asigna el numero de factura de acuerdo a 
            // los nuevo correlativos y rangos.
            if (Integer.parseInt(resultArray[0]) <= Integer.parseInt(resultArray2[4])) {
                r = Integer.parseInt(resultArray2[4]);
            } else {
                r = Integer.parseInt(resultArray[0]);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return r;
    }
    
    private Integer ultimaFact2() {
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
            if (f2.getIdFactura() <= cor.getRangoini()) {
                x = cor.getRangoini();
            } else {
                x = f2.getIdFactura();
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return x;
    }

    //select * from correlativossar c where id=(select max(id))
    private Correlativossar2 ultimaCorrelaorm() {
        Correlativossar2 x = null;
        Integer r = 0;
        try {
            Dao<Correlativossar2, String> facDao = DaoManager.createDao(connectionSource, Correlativossar2.class);
            // find out how many orders account-id #10 has
            /* GenericRawResults<Correlativossar2> rawResults
                    = facDao.queryRaw("select * from correlativossar c where id=(select max(id))",
                             new RawRowMapper<Correlativossar2>() {
                        public Correlativossar2 mapRow(String[] columnNames,
                                String[] resultColumns) {
                                return new Correlativossar2(Integer.parseInt(resultColumns[0]),(resultColumns[1]),Integer.parseInt(resultColumns[4]),Integer.parseInt(resultColumns[5]),
                                    resultColumns[6],resultColumns[7],Integer.parseInt(resultColumns[9]));
                        }
                    });*/
            /// Verificar en la tabla correlativos cual es el ultimo numero
            /// Debido a que las facturas se pueden vencer por fecha o por
            /// que llegaron al limite de numeracion.
            GenericRawResults<Correlativossar2> rawResultsCorre
                    = facDao.queryRaw("SELECT * FROM correlativossar c where c.estado=1 order by fechacreacion DESC",
                            new RawRowMapper<Correlativossar2>() {
                        public Correlativossar2 mapRow(String[] columnNames,
                                String[] resultColumns) {
                            return new Correlativossar2(Integer.parseInt(resultColumns[0]), resultColumns[1], Integer.parseInt(resultColumns[4]), Integer.parseInt(resultColumns[5]),
                                    resultColumns[6], resultColumns[7], Integer.parseInt(resultColumns[9]));
                        }
                    });
            /// Verif
            //List<Correlativossar2> results = rawResults.getResults();
            List<Correlativossar2> results2 = rawResultsCorre.getResults();
            //Correlativossar2 resultArray = results.get(0);
            Correlativossar2 resultArray2 = results2.get(0);
            // valida si el numero de factura inicial es menor al ultimo numero 
            // de factura realizado y asigna el numero de factura de acuerdo a 
            // los nuevo correlativos y rangos.
            // if ( resultArray.getRangoini()<= resultArray.getRangofin()) {
            x = resultArray2;
            //} else {
            //    x=resultArray;
            //}
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
                dtf.delPArticulos(f);
                DefaultTableModel temp = (DefaultTableModel) detfac.getModel();
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
                                    Logger.getLogger(frmFacturaORMonline.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        } catch (NonexistentEntityException ex) {
                            Logger.getLogger(frmFacturaORMonline.class.getName()).log(Level.SEVERE, null, ex);
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
        /* ejecuta = true;
        if (ejecuta) {
            h1.start();//
        }*/
        ////Se ejecutara hasta que el correlativo funcione correctamente!
        if (this.ped==1) {
             if (!txtcodcli.getText().isEmpty()) {
            clisel = Filcodcli(txtcodcli.getText());
            if(clisel==null){
                  if (JOptionPane.showConfirmDialog(null, "El Cliente no existe!", "Desea Crear el Cliente",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
                    try {
                        Cliente ncli=new Cliente();
                        ncli.setIdCliente(txtcodcli.getText());
                        ncli.setNombre(txtnombre.getText());
                        ncli.setDireccion(coordenadascli);
                        fincli.create(ncli);
                        getCliente();
                    } catch (Exception ex) {
                        Logger.getLogger(frmFacturaORMonline.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    }
                }
             }
        }
        Integer resul = 0;
        //crearFactura();
         jpfac=new FacturaJpaController(emFaclocal);
         jpdfac=new DetallefacturaJpaController(emFaclocal);
        resul=crearFactura();
        while (resul==0) {            
           resul=crearFactura();
           System.out.println("" + resul);
        }
    }//GEN-LAST:event_btnprintfacActionPerformed

    public Integer crearFactura() {
        Integer r = 0;
        try {
            Date date = formatfac.parse(txtfecha.getText());
            //guardaroupdateFactura();
            //JOptionPane.showMessageDialog(rootPane,"Factura->"+date.toString()+"->"+txtnfact.getValue()+"->"+LocalDate.now());
            JDialog jdet = new JDialog();
            Integer nfa = Integer.parseInt(txtnfact.getValue().toString());
            BigDecimal totsen = new BigDecimal(txttot.getText());
            jdet.setContentPane(new panelPagofacturaOnline(nfa, date, totsen));
            jdet.setLocation(200, 200);
            jdet.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jdet.pack();
            jdet.setModal(true);
            ((panelPagofacturaOnline) jdet.getContentPane()).txtefectivo.requestFocus();
            jdet.setVisible(true);
            if (((panelPagofacturaOnline) jdet.getContentPane()).pago == true) {
                if (guardarFactura(((panelPagofacturaOnline) jdet.getContentPane()).efectivo)) {
                    r = 1;
                }
            } else {
                guardarFacturaOpen(((panelPagofacturaOnline) jdet.getContentPane()).efectivo, 0);
                r = 1;
            }
            cerrarForm();
        } catch (Exception e) {
            r = 0;
            System.out.println("Error->" + e.getMessage());
        }
        return r;
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
        getCliente();
    }//GEN-LAST:event_txtcodcliFocusLost

    public void getCliente() throws HeadlessException {
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
    }

    private void txtcodcliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcodcliKeyReleased
        cerrarconESC(evt);
        if (evt.getKeyCode() == KeyEvent.VK_F4) {
            buspro = 1;
            dialogBuscaCli();
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

    public void imprimirFactura() {
        // TODO add your handling code here:
        try {
            Factura f1 = jpfac.findFactura(facturaid);
            if (f1 != null) {
                if (f1.getEfectivo() != null) {
                    Integer id = Integer.parseInt(txtnfact.getValue().toString());
                    if (id > 0) {
                        report.startReportprint("" + id,0);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "La factura no ha sido cobrada!, presione \"Guardar\" para Cobrar!", "Facturacion!", JOptionPane.INFORMATION_MESSAGE);
                }
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
                    JOptionPane.showMessageDialog(rootPane, "No hay Ordenes de Cocina pendientes!", "Orden de Cocina", JOptionPane.INFORMATION_MESSAGE);
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

    public DefaultComboBoxModel ObtenerListaTipofacORM() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        try {
            //Establece la conexion y con que SBD se trabajara
            Dao<Tipofactura, String> tipofDao = DaoManager.createDao(connectionSource, Tipofactura.class);
            for (Tipofactura it : tipofDao.queryForAll()) {
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
            for (Usuario it : jpusu.findUsuarioEntities()) {
                Usuario p = it;
                modelo.addElement(p);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error ");
            System.out.println("Coneccion incorrecta: " + ex);
        }
        return modelo;
    }

    public DefaultComboBoxModel ObtenerListausuORM() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        try {
            Dao<Usuario, String> usuDao = DaoManager.createDao(connectionSource, Usuario.class);
            for (Usuario it : usuDao.queryForAll()) {
                Usuario p = it;
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

    public DefaultComboBoxModel ObtenerListaTipoorv2() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        try {
            //Establece la conexion y con que SBD se trabajara
            Dao<Tiporden, String> tipofDao = DaoManager.createDao(connectionSource, Tiporden.class);
            for (Iterator<Tiporden> it = tipofDao.queryForAll().iterator(); it.hasNext();) {
                Tiporden t = it.next();
                modelo.addElement(t);
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

    public DefaultComboBoxModel ObtenerListaEmpORM() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        try {
            //Establece la conexion y con que SBD se trabajara
            Dao<Empresa, String> empDao = DaoManager.createDao(connectionSource, Empresa.class);
            for (Empresa it : empDao.queryForAll()) {
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
             return lcli;
        } catch (Exception e) {
            System.out.println("Error->" + e.getMessage());
            return null;
        }
       
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

    //////////////////////////////////
    public void ejecutarHilo() {
        h1 = new Thread() {
            @Override
            public void run() {
                try {
                    while (ejecuta) {
                        System.out.println("Hola Hilo1..." + new Date());
                        Thread.sleep(1000 * 2);
                        crearFactura();
                    }
                } catch (Exception e) {
                }

            }
        };
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
            java.util.logging.Logger.getLogger(frmFacturaORMonline.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmFacturaORMonline.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmFacturaORMonline.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmFacturaORMonline.class
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmFacturaORMonline().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Cerrar;
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
    private javax.swing.JComboBox<String> txtatendido;
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
        ((MenuprincipalPedidosOnline) this.getParent()).abrirBuscacli();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
