/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siguasystem.facturacionjava;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.siguasystem.config.ConfiguracionJpaController;
import com.siguasystem.modelo.Cliente;
import com.siguasystem.modelo.ClienteJpaController;
import com.siguasystem.modelo.Configuracion;
import com.siguasystem.modelo.Estadofact;
import com.siguasystem.modelo.EstadofactJpaController;
import com.siguasystem.modelo.Factura;
import com.siguasystem.modelo.FacturaJpaController;
import com.siguasystem.modelo.FacturaPK;
import com.siguasystem.modelo.Facturaorm;
import com.siguasystem.modelo.Producto;
import com.siguasystem.modelo.ProductoJpaController;
import com.siguasystem.modelo.Tipofactura;
import com.siguasystem.modelo.TipofacturaJpaController;
import com.siguasystem.modelo.Tiporden;
import com.siguasystem.modelo.TipordenJpaController;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

/**
 *
 * @author juan
 */
public class frmListadoFacturas extends javax.swing.JInternalFrame implements ActionListener {

    /**
     *
     */
    //Establece la conexion y con que SBD se trabajara
    private ConnectionSource connectionSource;//= new JdbcConnectionSource("jdbc:mysql://192.168.0.103:3306/bdrestaurante", "root", "cantones");
    private DefaultComboBoxModel cbomodelti = new DefaultComboBoxModel();
    private TablaListadoFacturas bp = new TablaListadoFacturas();
    EntityManagerFactory emFac = Persistence.createEntityManagerFactory("Restorant");
    private FacturaJpaController finfac;// = new FacturaJpaController(emFac);
    private ClienteJpaController fincli;// = new ClienteJpaController(emFac);
    private TipordenJpaController fintipoo;// = new TipordenJpaController(emFac);
    private TipofacturaJpaController fintipof;// = new TipofacturaJpaController(emFac);
    private EstadofactJpaController finstado ;//= new EstadofactJpaController(emFac);
    EjecutarReporte report;//= new EjecutarReporte();
    public String pselstring;
    public String pselstado;
    public String pseltot;
    Menuprincipal mnp;
    public BigDecimal tothoy;
    public BigDecimal totopen;
    public BigDecimal totclose;
    public BigDecimal totcomer;
    public BigDecimal totllevar;
    public BigDecimal totadomi;
    FileReader fr = null;
    public String[] conexion_data = new String[3];
    String alerta = "";
    String key = "01EE31A79FXXB2A3"; //llave
    String iv = "0123456789ABCDEF"; // vector de inicialización
    DateFormat formatfac = new SimpleDateFormat("yyyy-MM-dd", Locale.US);//);
    String factual = DateFormat.getDateInstance().format(new Date()).toString();
     EntityManagerFactory emConfigLocal = Persistence.createEntityManagerFactory("RestorantLocal");
    ConfiguracionJpaController jpconfig=new ConfiguracionJpaController(emConfigLocal);

    public frmListadoFacturas() {
        try {
            initComponents();
            new Runnable() {
                @Override
                public void run() {
                   report = new EjecutarReporte();
                   finfac = new FacturaJpaController(emFac);
                   fincli = new ClienteJpaController(emFac);
                   fintipoo= new TipordenJpaController(emFac);
                   fintipof= new TipofacturaJpaController(emFac);
                   finstado= new EstadofactJpaController(emFac);
                }
            }.run();

            listado.setModel(bp.crearTabla());
            tamanioCol();
            lblnreg.setText("0");
            f2.setDateToToday();
            Configuracion confi=jpconfig.findConfiguracion(1);
            conexion_data=confi.getConfigval().split(";");
            connectionSource = new JdbcConnectionSource(conexion_data[0], conexion_data[1],  conexion_data[2]);
        } catch (Exception ex) {
            Logger.getLogger(frmListadoFacturas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void tamanioCol() {
        listado.getColumnModel().getColumn(0).setPreferredWidth(10);
        listado.getColumnModel().getColumn(1).setPreferredWidth(50);
        listado.getColumnModel().getColumn(2).setPreferredWidth(250);
        listado.getColumnModel().getColumn(3).setPreferredWidth(80);
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalTextPosition(JTextField.RIGHT);
        listado.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        listado.getColumnModel().getColumn(4).setPreferredWidth(150);
        listado.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabfac = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        listado = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        lblnreg = new javax.swing.JLabel();
        cbotipofac2 = new javax.swing.JComboBox<>();
        txtfiltroopen = new javax.swing.JTextField();
        txtfind = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        listado2 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        lblnreg3 = new javax.swing.JLabel();
        cbotipofac1 = new javax.swing.JComboBox<>();
        txtfiltroopen2 = new javax.swing.JTextField();
        txtfind1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listado3 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        lblnreg2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblnreg4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtcomer = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtllevar = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtadomi = new javax.swing.JLabel();
        txtfiltroopen3 = new javax.swing.JTextField();
        cbotipofac = new javax.swing.JComboBox<>();
        txtfind2 = new javax.swing.JButton();
        f2 = new com.github.lgooddatepicker.components.DatePicker();
        jLabel12 = new javax.swing.JLabel();
        f1 = new com.github.lgooddatepicker.components.DatePicker();
        jLabel13 = new javax.swing.JLabel();
        btnprintorden = new javax.swing.JButton();
        btnprintfac = new javax.swing.JButton();
        btnprevifac = new javax.swing.JButton();
        btnprevorden = new javax.swing.JButton();
        chkhist1 = new javax.swing.JCheckBox();
        Cerrar = new javax.swing.JButton();
        btnnulfac = new javax.swing.JButton();

        setTitle("Busqueda de Facturas");
        setName("Listadofac"); // NOI18N
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

        tabfac.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tabfac.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabfacStateChanged(evt);
            }
        });

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Resultados"));

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
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listadoMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(listado);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel7.setText("Total Registros encontrados: ");

        lblnreg.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblnreg.setText("jLabel4");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 925, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblnreg)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
                .addGap(12, 12, 12)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblnreg)))
        );

        cbotipofac2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbotipofac2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Factura", "Cliente\t" }));
        cbotipofac2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cbotipofac2KeyReleased(evt);
            }
        });

        txtfind.setText("Buscar");
        txtfind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfindActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(cbotipofac2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfiltroopen, javax.swing.GroupLayout.PREFERRED_SIZE, 566, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtfind)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtfiltroopen)
                        .addComponent(txtfind))
                    .addComponent(cbotipofac2))
                .addGap(12, 12, 12)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabfac.addTab("Facturas Abiertas", jPanel4);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Resultados"));

        listado2.setModel(new javax.swing.table.DefaultTableModel(
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
        listado2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listado2MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(listado2);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel6.setText("Total Registros encontrados: ");

        lblnreg3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblnreg3.setText("jLabel4");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 925, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblnreg3)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
                .addGap(12, 12, 12)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblnreg3)))
        );

        cbotipofac1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbotipofac1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Factura", "Cliente\t" }));
        cbotipofac1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cbotipofac1KeyReleased(evt);
            }
        });

        txtfind1.setText("Buscar");
        txtfind1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfind1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(cbotipofac1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtfiltroopen2, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtfind1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbotipofac1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtfiltroopen2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtfind1))
                .addGap(1, 1, 1)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabfac.addTab("facturas Cerradas", jPanel5);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Resultados"));
        jPanel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        listado3.setModel(new javax.swing.table.DefaultTableModel(
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
        listado3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listado3MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(listado3);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel5.setText("Total Registros encontrados: ");

        lblnreg2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblnreg2.setText("jLabel4");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel8.setText("Total Ventas: ");

        lblnreg4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblnreg4.setText("jLabel4");

        jLabel1.setText("Comer:");

        txtcomer.setText("jLabel2");

        jLabel2.setText("Llevar:");

        txtllevar.setText("jLabel3");

        jLabel3.setText("Adomicilio:");

        txtadomi.setText("jLabel4");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblnreg2)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtcomer)
                .addGap(74, 74, 74)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtllevar)
                .addGap(94, 94, 94)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtadomi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(lblnreg4)
                .addGap(40, 40, 40))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblnreg4)
                    .addComponent(jLabel1)
                    .addComponent(txtcomer)
                    .addComponent(jLabel2)
                    .addComponent(txtllevar)
                    .addComponent(jLabel3)
                    .addComponent(txtadomi))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
                .addGap(12, 12, 12)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblnreg2)))
        );

        cbotipofac.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbotipofac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Factura", "Cliente\t" }));
        cbotipofac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cbotipofacKeyReleased(evt);
            }
        });

        txtfind2.setText("Buscar");
        txtfind2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfind2ActionPerformed(evt);
            }
        });

        jLabel12.setText("hasta");
        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel13.setText("Desde:");
        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(27, 27, 27))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(cbotipofac, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfiltroopen3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(f1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(f2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(txtfind2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(f1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(f2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtfiltroopen3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtfind2)))
                    .addComponent(cbotipofac))
                .addGap(6, 6, 6)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabfac.addTab("Facturas de Hoy", jPanel3);

        btnprintorden.setText("Re-Imprimir Orden");
        btnprintorden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprintordenActionPerformed(evt);
            }
        });

        btnprintfac.setText("Re-Imprimir Factura");
        btnprintfac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprintfacActionPerformed(evt);
            }
        });

        btnprevifac.setText("Vista Previa Factura");
        btnprevifac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprevifacActionPerformed(evt);
            }
        });

        btnprevorden.setText("Vista Previa de Orden");
        btnprevorden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprevordenActionPerformed(evt);
            }
        });

        chkhist1.setText("Facturas Historicas");
        chkhist1.setActionCommand("");

        Cerrar.setBackground(new java.awt.Color(255, 0, 51));
        Cerrar.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        Cerrar.setForeground(new java.awt.Color(255, 255, 255));
        Cerrar.setText("Cerrar");
        Cerrar.setAutoscrolls(true);
        Cerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CerrarActionPerformed(evt);
            }
        });

        btnnulfac.setText("Anular Factura");
        btnnulfac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnulfacActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tabfac)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnprintfac, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                    .addComponent(btnprintorden, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnprevifac, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                    .addComponent(btnprevorden, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(chkhist1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Cerrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnnulfac, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabfac)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(chkhist1)
                .addGap(18, 18, 18)
                .addComponent(btnprintorden, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnprevorden, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnprintfac, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnprevifac, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnnulfac, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Cerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void listado3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listado3MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            if (listado3.getRowCount() > 0) {
                obtenerValsel();
                if (pselstring.length() > 0) {
                    verificaEstadofacORM(); // verificaEstadofac5(); //verificaEstadofac3();//verificaEstadofac();
                }
            }
        }
    }//GEN-LAST:event_listado3MouseClicked
   
    private void cbotipofacKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbotipofacKeyReleased
        //MostrarDatoscel(txtfiltro);
    }//GEN-LAST:event_cbotipofacKeyReleased

    private void listado2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listado2MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            if (listado.getRowCount() > 0) {
                obtenerValsel();
                if (pselstring.length() > 0) {
                    //verificaEstadofac5();//verificaEstadofac();
                    verificaEstadofacORM();
                }
            }
        }
    }//GEN-LAST:event_listado2MouseClicked

    private void cbotipofac1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbotipofac1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cbotipofac1KeyReleased

    private void listadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listadoMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            if (listado.getRowCount() > 0) {
                obtenerValsel();
                if (pselstring.length() > 0) {
                    // verificaEstadofac2();//
                    //verificarEstadofac4();
                    //verificaEstadofac();
                    //verificaEstadofac6();
                    verificaEstadofacORM();
                }
            }
    }//GEN-LAST:event_listadoMouseClicked
    }
    private void cbotipofac2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbotipofac2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cbotipofac2KeyReleased

    private void txtfindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfindActionPerformed
        finfacopen2(1);
    }//GEN-LAST:event_txtfindActionPerformed

    public void verificaEstadofacORM() {
        Factura fsel = (Factura) finfac.getEntityManager().createNativeQuery("select * from factura where id=" + pselstring, Factura.class).getSingleResult();
        System.out.println(fsel);
        FacturaPK pkfact=new FacturaPK(fsel.getIdFactura(), fsel.getFecha(),Integer.parseInt(pselstring));
        frmFacturaORM frmfac3 = new frmFacturaORM(pkfact);
        frmfac3.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        if (fsel.getEstadofact().getIdestadofact() == 1) {
            frmfac3.btnaddet.setEnabled(true);
            frmfac3.btnsavefac.setEnabled(false);
            frmfac3.btnprintfac.setEnabled(true);
        } else {
            frmfac3.btnaddet.setEnabled(false);
            frmfac3.btnsavefac.setEnabled(false);
            frmfac3.btnprintfac.setEnabled(false);
        }
        this.getDesktopPane().add(frmfac3);
        frmfac3.nuevafac = fsel.getIdFactura();
        frmfac3.txtcodcli.setText(fsel.getCliente().getIdCliente());
        frmfac3.setVisible(true);
        frmfac3.pack();
        frmfac3.show();
    }

    public void finfacopen() {
        // TODO add your handling code here
        if (txtfiltroopen.getText().length() > 0) {
            facOpenfilfac(txtfiltroopen.getText(), 1);
        } else {
            facOpen();
        }
    }

    public void finfacopen2(Integer a) {
        // TODO add your handling code here:
        if (a == 1) {
            if (txtfiltroopen.getText().length() > 0) {
                facOpenfilfac(txtfiltroopen.getText(), 1);
            } else {
                facOpen();
            }
        }
        if (a == 2) {
            if (txtfiltroopen2.getText().length() > 0) {
                facOpenfilfac(txtfiltroopen2.getText(), 2);
            } else {
                facOpen();
            }
        }
        if (a == 3) {
            if (txtfiltroopen3.getText().length() > 0 || !f1.getDate().format(DateTimeFormatter.ISO_DATE).isEmpty()) {
                facOpenfilfac(txtfiltroopen3.getText(), 3);
            } else {
                facOpen();
            }
        }

    }

    private void tabfacStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabfacStateChanged
        if (this.isShowing()) {
            cargarFacturas();
        }
    }//GEN-LAST:event_tabfacStateChanged

    public void cargarFacturas() throws HeadlessException {
        // TODO add your handling code here:
        if (tabfac.getSelectedIndex() == 0) {
            finfacopen();
        }
        if (tabfac.getSelectedIndex() == 1) {
            if (1 == 1) {
               facClose();
            } else {
                JOptionPane.showMessageDialog(rootPane, "No tiene permisos para acceder a esta opcion!", "Error", JOptionPane.ERROR_MESSAGE);
                tabfac.setSelectedIndex(0);
            }
        }
        if (tabfac.getSelectedIndex() == 2) {
            if (1 == Global.admin) {
                facHoy();
            } else {
                JOptionPane.showMessageDialog(rootPane, "No tiene permisos para acceder a esta opcion!", "Error", JOptionPane.ERROR_MESSAGE);
                tabfac.setSelectedIndex(0);
            }

        }
    }

    private void txtfind1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfind1ActionPerformed
        // TODO add your handling code here:
        finfacopen2(2);
    }//GEN-LAST:event_txtfind1ActionPerformed

    private void txtfind2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfind2ActionPerformed
        // TODO add your handling code here:
        finfacopen2(3);
    }//GEN-LAST:event_txtfind2ActionPerformed

    private void btnprintordenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprintordenActionPerformed
        // TODO add your handling code here:
        imprimirOrden();
    }//GEN-LAST:event_btnprintordenActionPerformed

    private void btnprintfacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprintfacActionPerformed
        // TODO add your handling code here:
        Integer id = Integer.parseInt(obtenerValsel());
        if (id > 0) {
            if (pselstado.trim().toUpperCase().equals("CERRADA")) {
                report.startReportprintL("" + id,pseltot);
            } else {
                JOptionPane.showMessageDialog(rootPane, "La Factura debe cobrarse antes de imprimirse, y no se puede imprimir si esta Abierta.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnprintfacActionPerformed

    private void btnprevifacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprevifacActionPerformed
        // TODO add your handling code here:
        Integer id = Integer.parseInt(obtenerValsel());
        // JOptionPane.showMessageDialog(rootPane,"Factura->"+id);
        if (id > 0) {
            if (pselstado.equals("CERRADA")) {
                report.startReportL("" + id, pseltot);
            } else {
                JOptionPane.showMessageDialog(rootPane, "La Factura debe cobrarse antes de imprimirse, y no se puede imprimir si esta Abierta.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnprevifacActionPerformed

    private void btnprevordenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprevordenActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        Integer id = Integer.parseInt(obtenerValsel());
        // JOptionPane.showMessageDialog(rootPane,"Factura->"+id);
        if (id > 0) {
            report.startOrdenCocinatotpre("" + id);
        }
    }//GEN-LAST:event_btnprevordenActionPerformed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_formKeyReleased

    private void CerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CerrarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_CerrarActionPerformed

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        // TODO add your handling code here:
        cargarFacturas();
    }//GEN-LAST:event_formInternalFrameActivated

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        //cargarFacturas();
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnnulfacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnulfacActionPerformed
        // TODO add your handling code here:
        try {
            obtenerValsel();
            Factura fsel = (Factura) finfac.getEntityManager().createNativeQuery("select * from factura where idfactura=" + pselstring, Factura.class).getSingleResult();
            fsel.setEstadofact(new Estadofact(3));
            fsel.setNotas("Factura Anulada");
            fsel.setTotalfac(BigDecimal.valueOf(0.00));
            finfac.edit(fsel);
            JOptionPane.showMessageDialog(rootPane, "Factura->" + obtenerValsel() + " ha sido ANULADA.");
        } catch (Exception e) {
            System.out.println("Error->" + e.getMessage());
        }
    }//GEN-LAST:event_btnnulfacActionPerformed

    public void imprimirOrden() {
        Integer id = Integer.parseInt(obtenerValsel());
        // JOptionPane.showMessageDialog(rootPane,"Factura->"+id+" Producto->"+obtenerValsel());
        if (id > 0) {
            report.startOrdenCocinaprintTot("" + id);
        }
    }

    public String obtenerValsel() {
        // TODO add your handling code here:
        if (listado3.getRowCount() > 0 || listado.getRowCount() > 0 || listado2.getRowCount() > 0) {
            switch (tabfac.getSelectedIndex()) {
                case 0:
                    pselstring = listado.getModel().getValueAt(listado.getSelectedRow(), 0).toString();
                    pselstado = listado.getModel().getValueAt(listado.getSelectedRow(), 5).toString().trim();
                    pseltot = listado.getModel().getValueAt(listado.getSelectedRow(), 4).toString().trim();
                    break;
                case 1:
                    pselstring = listado2.getModel().getValueAt(listado2.getSelectedRow(), 0).toString();
                    pselstado = listado2.getModel().getValueAt(listado2.getSelectedRow(), 5).toString().trim();
                    pseltot = listado2.getModel().getValueAt(listado2.getSelectedRow(), 4).toString().trim();
                    break;
                case 2:
                    pselstring = listado3.getModel().getValueAt(listado3.getSelectedRow(), 0).toString();
                    pselstado = listado3.getModel().getValueAt(listado3.getSelectedRow(), 5).toString().trim();
                    pseltot = listado3.getModel().getValueAt(listado3.getSelectedRow(), 4).toString().trim();
                    break;
            }
        }
        return pselstring;
    }

    private void facOpenORM() {
        if (this.isShowing()) {
            /// Tabla 1
            try {
                DefaultTableModel df = bp.crearTabla();
                int i = 1;
                double totf = 0;
                List<Facturaorm> lf = new ArrayList<Facturaorm>();
                lf = findFacORM2();
                for (Iterator<Facturaorm> iterator = lf.iterator(); iterator.hasNext();) {
                    Facturaorm p = iterator.next();
                    Cliente c = fincli.findCliente(p.getCliente().getIdCliente());
                    Tiporden orden = fintipoo.findTiporden(p.getIdorden().getIdtiporden());
                    Tipofactura ffact = fintipof.findTipofactura(p.getTipofact().getIdTipoFactura());
                    Estadofact stfac = finstado.findEstadofact(p.getEstadofact().getIdestadofact());
                    if (p.getIdfactura() != null) {
                        df.addRow(new Object[]{i++, p.getIdfactura(), c.getIdCliente() + "-" + c.getNombre(), orden.getDescri() + "/" + ffact.getDescripcion(), p.getTotalfac(), stfac.getDescrip()});
                        //df.addRow(new Object[]{i++, p.getIdfactura(), p.getCliente(),p.getIdorden()+ "/" +p.getTipofact(), p.getTotalfac(), p.getEstadofact()});
                        totf += p.getTotalfac().doubleValue();
                    }
                }
                listado.setModel(df);
                lblnreg.setText("" + listado.getRowCount());
                lblnreg4.setText("" + Math.round(totf));
                tamanioCol();
            } catch (Exception e) {
                System.out.println("Error:" + e.getMessage());
            }
        }
    }

    private void facOpen() {
        if (this.isShowing()) {
            /// Tabla 1
            try {
                DefaultTableModel df = bp.crearTabla();
                List<Factura> lf = new ArrayList<Factura>();
                Query qsql = finfac.getEntityManager().createNativeQuery("select * from factura c where c.fecha=curdate() and c.estadofact=1", Factura.class
                );
                qsql.setHint(QueryHints.REFRESH, HintValues.TRUE);//Actualiza
                lf = qsql.getResultList();
                int i = 1;
                double totf = 0;
                for (Iterator<Factura> iterator = lf.iterator(); iterator.hasNext();) {
                    Factura p = iterator.next();
                    if (p != null) {
                        df.addRow(new Object[]{p.getId(), p.getIdFactura(), p.getCliente().getNombre(), p.getIdorden().getDescri() + "/" + p.getTipofact().getDescripcion(), p.getTotalfac(), p.getEstadofact().getDescrip()});
                        totf += p.getTotalfac().doubleValue();
                    }
                }
                listado.setModel(df);
                lblnreg.setText("" + listado.getRowCount());
                lblnreg4.setText("" + Math.round(totf));
                tamanioCol();
            } catch (Exception e) {
                System.out.println("Error:" + e.getMessage());
            }

            /*
            /// Tabla 2
            try {
                DefaultTableModel df = bp.crearTabla();
                List<Factura> lf = new ArrayList<Factura>();
                // pt = txtnom.getText().toLowerCase();
                Query qsql = finfac.getEntityManager().createNativeQuery("select * from factura c where c.fecha=curdate() and c.estadofact=2", Factura.class
                );//qsql.setParameter("n", txtnom.getText());
                qsql.setHint(QueryHints.REFRESH, HintValues.TRUE);//Actualiza
                lf = qsql.getResultList();
                int i = 1;
                for (Iterator<Factura> iterator = lf.iterator(); iterator.hasNext();) {
                    Factura p = iterator.next();
                    if (p.getFacturaPK() != null) {
                        df.addRow(new Object[]{i++, p.getFacturaPK().getIdFactura(), p.getCliente().getNombre(), p.getIdorden().getDescri() + "/" + p.getTipofact().getDescripcion(), p.getTotalfac(), p.getEstadofact().getDescrip()});
                    }
                }
                listado2.setModel(df);
                lblnreg3.setText("" + listado2.getRowCount());
                tamanioCol();
            } catch (Exception e) {
                System.out.println("Error:" + e.getMessage());
            }

            /// Tabla 3
            try {
                DefaultTableModel df = bp.crearTabla();
                List<Factura> lf = new ArrayList<Factura>();
                //String pt = txtnom.getText().toLowerCase();
                Query qsql = finfac.getEntityManager().createNativeQuery("select * from factura c where c.fecha=curdate() ", Factura.class
                );//qsql.setParameter("n", txtnom.getText());
                qsql.setHint(QueryHints.REFRESH, HintValues.TRUE);//Actualiza
                lf = qsql.getResultList();
                int i = 1; double totf=0;
                for (Iterator<Factura> iterator = lf.iterator(); iterator.hasNext();) {
                    Factura p = iterator.next();
                    if (p.getFacturaPK() != null) {
                        df.addRow(new Object[]{i++, p.getFacturaPK().getIdFactura(), p.getCliente().getNombre(), p.getIdorden().getDescri() + "/" + p.getTipofact().getDescripcion(), p.getTotalfac(), p.getEstadofact().getDescrip()});
                        totf+= p.getTotalfac().doubleValue();
                    }
                }
                listado3.setModel(df);
                lblnreg4.setText(""+Math.round(totf));
                lblnreg2.setText("" + listado3.getRowCount());
                tamanioCol();
            } catch (Exception e) {
                System.out.println("Error:" + e.getMessage());
            }*/
        }

    }

    private List<Facturaorm> findFacORM2() {
        if (connectionSource != null) {
            List<Facturaorm> lp = null;
            try {
                //String pt = txtnompro.getText().toUpperCase();
                Dao<Facturaorm, String> facDao = DaoManager.createDao(connectionSource, Facturaorm.class);
                // find out how many orders account-id #10 has
                lp = facDao.queryBuilder().where().eq("estadofact", "1").query();
                //queryRaw("select * from producto c where UPPER(c.descripcion) LIKE UPPER(\'%" + pt.trim() + "%\')");
                ///Verificar en la tabla correlativos cual es el ultimo numero
                ///Debido a que las facturas se pueden vencer por fecha o por
                /// que llegaron al limite de numeracion.
                //System.out.println("" + lp);
                return lp;
                //valida si el numero de factura inicial es menor al ultimo numero 
                // de factura realizado y asigna el numero de factura de acuerdo a 
                // los nuevo correlativos y rangos.

            } catch (Exception e) {
                System.out.println("Error:" + e.getMessage());
            }
        }
        return null;
    }

    private void facClose() {
        DefaultTableModel df = bp.crearTabla();
        List<Factura> lf = new ArrayList<Factura>();
        try {
            Query qsql = null;
            qsql = finfac.getEntityManager().createNativeQuery("select * from factura c where c.fecha=curdate() and c.estadofact=2", Factura.class);
            qsql.setHint(QueryHints.REFRESH, HintValues.TRUE);//Actualiza
            lf = qsql.getResultList();
            int i = 1;
            for (Iterator<Factura> iterator = lf.iterator(); iterator.hasNext();) {
                Factura p = iterator.next();
                if (p != null) {
                    df.addRow(new Object[]{p.getId(), p.getIdFactura(), p.getCliente().getNombre(), p.getIdorden().getDescri() + "/" + p.getTipofact().getDescripcion(), p.getTotalfac(), p.getEstadofact().getDescrip()});
                }
            }
            listado2.setModel(df);
            lblnreg3.setText("" + listado2.getRowCount());
            tamanioCol();
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    private void facOpenfilfac(String nf, Integer a) {
        ///// listado 1
        DefaultTableModel df = bp.crearTabla();
        List<Factura> lf = new ArrayList<Factura>();
        if (a == 1) {
            if ("Factura".equals(cbotipofac2.getSelectedItem().toString())) {
                try {
                    Query qsql = null;
                    if (chkhist1.isSelected()) {
                        qsql = finfac.getEntityManager().createNativeQuery("select * from factura c where c.idfactura=" + nf, Factura.class);
                    } else {
                        qsql = finfac.getEntityManager().createNativeQuery("select * from factura c where c.fecha=curdate() and c.estadofact=1 and c.idfactura=" + nf, Factura.class);
                    }
                    qsql.setHint(QueryHints.REFRESH, HintValues.TRUE);//Actualiza
                    lf = qsql.getResultList();
                    int i = 1;
                    double totf = 0;
                    for (Iterator<Factura> iterator = lf.iterator(); iterator.hasNext();) {
                        Factura p = iterator.next();
                        if (p!= null) {
                            df.addRow(new Object[]{p.getId(), p.getIdFactura(), p.getCliente().getNombre(), p.getIdorden().getDescri() + "/" + p.getTipofact().getDescripcion(), p.getTotalfac(), p.getEstadofact().getDescrip()});
                            totf += p.getTotalfac().doubleValue();
                        }
                    }
                    listado.setModel(df);
                    lblnreg.setText("" + listado.getRowCount());
                    lblnreg4.setText("" + Math.round(totf));
                    tamanioCol();
                } catch (Exception e) {
                    System.out.println("Error:" + e.getMessage());
                }
            } else {
                try {
                    Query qsql = finfac.getEntityManager().createNativeQuery("select * from factura c inner join Cliente f on c.cliente = f.idCliente where c.fecha=curdate() and c.estadofact=1 and f.nombre LIKE \'" + nf + "%\'", Factura.class
                    );
                    lf = qsql.getResultList();
                    int i = 1;
                    for (Iterator<Factura> iterator = lf.iterator(); iterator.hasNext();) {
                        Factura p = iterator.next();
                        if (p!= null) {
                            df.addRow(new Object[]{p.getId(), p.getIdFactura(), p.getCliente().getNombre(), p.getIdorden().getDescri() + "/" + p.getTipofact().getDescripcion(), p.getTotalfac(), p.getEstadofact().getDescrip()});
                        }
                    }
                    listado.setModel(df);
                    lblnreg.setText("" + listado.getRowCount());
                    tamanioCol();
                } catch (Exception e) {
                    System.out.println("Error:" + e.getMessage());
                }
            }
        }

        ///// listado 2
        if (a == 2) {
            if ("Factura".equals(cbotipofac1.getSelectedItem().toString())) {
                try {
                    Query qsql = null;
                    if (chkhist1.isSelected()) {
                        qsql = finfac.getEntityManager().createNativeQuery("select * from factura c where c.estadofact=2 and c.idfactura=" + nf, Factura.class);
                    } else {
                        qsql = finfac.getEntityManager().createNativeQuery("select * from factura c where c.fecha=curdate() and c.estadofact=2 and c.idfactura=" + nf, Factura.class);
                    }
                    lf = qsql.getResultList();
                    int i = 1;
                    for (Iterator<Factura> iterator = lf.iterator(); iterator.hasNext();) {
                        Factura p = iterator.next();
                        if (p!= null) {
                            df.addRow(new Object[]{p.getId(), p.getIdFactura(), p.getCliente().getNombre(), p.getIdorden().getDescri() + "/" + p.getTipofact().getDescripcion(), p.getTotalfac(), p.getEstadofact().getDescrip()});
                        }
                    }
                    listado2.setModel(df);
                    lblnreg3.setText("" + listado2.getRowCount());
                    tamanioCol();
                } catch (Exception e) {
                    System.out.println("Error:" + e.getMessage());
                }
            } else {
                try {
                    Query qsql = finfac.getEntityManager().createNativeQuery("select * from factura c inner join Cliente f on c.cliente = f.idCliente where c.fecha=curdate() and c.estadofact=2 and f.nombre LIKE \'" + nf + "%\'", Factura.class
                    );
                    lf = qsql.getResultList();
                    int i = 1;
                    for (Iterator<Factura> iterator = lf.iterator(); iterator.hasNext();) {
                        Factura p = iterator.next();
                        if (p != null) {
                            df.addRow(new Object[]{p.getId(), p.getIdFactura(), p.getCliente().getNombre(), p.getIdorden().getDescri() + "/" + p.getTipofact().getDescripcion(), p.getTotalfac(), p.getEstadofact().getDescrip()});
                        }
                    }
                    listado2.setModel(df);
                    lblnreg3.setText("" + listado2.getRowCount());
                    tamanioCol();
                } catch (Exception e) {
                    System.out.println("Error:" + e.getMessage());
                }
            }
        }

        ///// listado 2
        if (a == 3) {
            if ("Factura".equals(cbotipofac.getSelectedItem().toString())) {
                try {
                    Query qsql = null;
                    if (chkhist1.isSelected()) {
                        if (f1.getDate().format(DateTimeFormatter.ISO_DATE).length() > 0) {
                            qsql = finfac.getEntityManager().createNativeQuery("select * from factura c where  c.fecha between ?  and ?", Factura.class)
                                    .setParameter(1, f1.getDate().format(DateTimeFormatter.ISO_DATE))
                                    .setParameter(2, f2.getDate().format(DateTimeFormatter.ISO_DATE));
                        } else {
                            qsql = finfac.getEntityManager().createNativeQuery("select * from factura c where  c.idfactura=" + nf, Factura.class);
                        }
                    } else {
                        qsql = finfac.getEntityManager().createNativeQuery("select * from factura c where c.fecha=curdate() and c.estadofact=1 and c.idfactura=" + nf, Factura.class);
                    }
                    lf = qsql.getResultList();
                    int i = 1;
                    double totf = 0;
                    for (Iterator<Factura> iterator = lf.iterator(); iterator.hasNext();) {
                        Factura p = iterator.next();
                        if (p != null) {
                            df.addRow(new Object[]{p.getId(), p.getIdFactura(), p.getCliente().getNombre(), p.getIdorden().getDescri() + "/" + p.getTipofact().getDescripcion(), p.getTotalfac(), p.getEstadofact().getDescrip()});
                            totf += p.getTotalfac().doubleValue();
                        }
                    }
                    listado3.setModel(df);
                    lblnreg4.setText("" + totf);
                    lblnreg2.setText("" + listado3.getRowCount());
                    tamanioCol();
                } catch (Exception e) {
                    System.out.println("Error:" + e.getMessage());
                }
            } else {
                try {
                    Query qsql = finfac.getEntityManager().createNativeQuery("select * from factura c inner join Cliente f on c.cliente = f.idCliente where c.fecha=curdate() and c.estadofact=1 and f.nombre LIKE \'" + nf + "%\'", Factura.class
                    );
                    lf = qsql.getResultList();
                    int i = 1;
                    for (Iterator<Factura> iterator = lf.iterator(); iterator.hasNext();) {
                        Factura p = iterator.next();
                        if (p!= null) {
                            df.addRow(new Object[]{p.getId(), p.getIdFactura(), p.getCliente().getNombre(), p.getIdorden().getDescri() + "/" + p.getTipofact().getDescripcion(), p.getTotalfac(), p.getEstadofact().getDescrip()});
                        }
                    }
                    listado3.setModel(df);
                    lblnreg2.setText("" + listado3.getRowCount());
                    tamanioCol();
                } catch (Exception e) {
                    System.out.println("Error:" + e.getMessage());
                }
            }
        }

    }

    private void facHoy() {
        Double toth = 0.0;
        Double totco = 0.0;
        Double totlle = 0.0;
        Double totado = 0.0;
        DefaultTableModel df = bp.crearTabla();
        List<Factura> lf = new ArrayList<Factura>();
        try {
            Query qsql = finfac.getEntityManager().createNativeQuery("select * from factura c where c.fecha=curdate()", Factura.class
            );
            qsql.setHint(QueryHints.REFRESH, HintValues.TRUE);
            lf = qsql.getResultList();
            int i = 1;
            for (Iterator<Factura> iterator = lf.iterator(); iterator.hasNext();) {
                Factura p = iterator.next();
                if (p != null) {
                    df.addRow(new Object[]{i++, p.getIdFactura(), p.getCliente().getNombre(), p.getIdorden().getDescri() + "/" + p.getTipofact().getDescripcion(), p.getTotalfac(), p.getEstadofact().getDescrip()});
                    toth += p.getTotalfac().doubleValue();
                    if (p.getIdorden().getIdtiporden() == 1) {
                        totco += p.getTotalfac().doubleValue();
                    } else if (p.getIdorden().getIdtiporden() == 2) {
                        totlle += p.getTotalfac().doubleValue();
                    } else if (p.getIdorden().getIdtiporden() == 3) {
                        totado += p.getTotalfac().doubleValue();
                    }
                }
            }
            listado3.setModel(df);
            lblnreg2.setText("" + listado3.getRowCount());
            tothoy = new BigDecimal(toth).setScale(2);
            totcomer = new BigDecimal(totco).setScale(2);
            txtcomer.setText("" + String.format("%,.2f", totcomer));
            totllevar = new BigDecimal(totlle).setScale(2);
            txtllevar.setText("" + String.format("%,.2f", totllevar));
            totadomi = new BigDecimal(totado).setScale(2);
            txtadomi.setText("" + String.format("%,.2f", totadomi));
            lblnreg4.setText("" + String.format("%,.2f", tothoy));
            tamanioCol();
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    private void ProductoFilcodlike() {
        DefaultTableModel df = bp.crearTabla();
        List<Producto> lpro = new ArrayList<Producto>();
        try {
            String pt = txtfiltroopen.getText().toLowerCase();
            Query qsql = finfac.getEntityManager().createNativeQuery("select * from producto c where LOWER(c.idproducto) LIKE \'" + pt + "%\'", Producto.class
            );//qsql.setParameter("n", txtnom.getText());
            lpro = qsql.getResultList();
            int i = 1;
            for (Iterator<Producto> iterator = lpro.iterator(); iterator.hasNext();) {
                Producto p = iterator.next();
                NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("es", "es_HN"));
                String precio = formatter.format(p.getPrecio());
                df.addRow(new Object[]{i++, p.getIdProducto(), p.getNombre(), p.getPrecio(), p.getCantidadexis(), p.getCategoria()});
            }
            listado.setModel(df);
            tamanioCol();
        } catch (Exception e) {
        }
    }

    private void ProductoFilcod() {
        DefaultTableModel df = bp.crearTabla();
        Producto lpro = new Producto();
        try {

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
            java.util.logging.Logger.getLogger(frmListadoFacturas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmListadoFacturas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmListadoFacturas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmListadoFacturas.class
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmListadoFacturas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Cerrar;
    private javax.swing.JButton btnnulfac;
    private javax.swing.JButton btnprevifac;
    private javax.swing.JButton btnprevorden;
    private javax.swing.JButton btnprintfac;
    private javax.swing.JButton btnprintorden;
    private javax.swing.JComboBox<String> cbotipofac;
    private javax.swing.JComboBox<String> cbotipofac1;
    private javax.swing.JComboBox<String> cbotipofac2;
    private javax.swing.JCheckBox chkhist1;
    private com.github.lgooddatepicker.components.DatePicker f1;
    private com.github.lgooddatepicker.components.DatePicker f2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblnreg;
    private javax.swing.JLabel lblnreg2;
    private javax.swing.JLabel lblnreg3;
    private javax.swing.JLabel lblnreg4;
    private javax.swing.JTable listado;
    private javax.swing.JTable listado2;
    private javax.swing.JTable listado3;
    private javax.swing.JTabbedPane tabfac;
    private javax.swing.JLabel txtadomi;
    private javax.swing.JLabel txtcomer;
    private javax.swing.JTextField txtfiltroopen;
    private javax.swing.JTextField txtfiltroopen2;
    private javax.swing.JTextField txtfiltroopen3;
    private javax.swing.JButton txtfind;
    private javax.swing.JButton txtfind1;
    private javax.swing.JButton txtfind2;
    private javax.swing.JLabel txtllevar;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
