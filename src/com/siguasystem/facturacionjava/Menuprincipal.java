/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siguasystem.facturacionjava;

import com.siguasystem.config.ConfiguracionJpaController;
import com.siguasystem.modelo.Configuracion;
import com.siguasystem.modelo.DetallefacturaJpaController;
import com.siguasystem.modelo.Factura;
import com.siguasystem.modelo.Tipofactura;
import com.siguasystem.modelo.TipofacturaJpaController;
import com.siguasystem.modelo.Tiporden;
import com.siguasystem.modelo.TipordenJpaController;
import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.SAXException;

/**
 *
 * @author juan
 */
public class Menuprincipal extends javax.swing.JFrame {

    /**
     * Creates new form Menuprincipal
     */
    EntityManagerFactory emFac = Persistence.createEntityManagerFactory("Restorant");
    EntityManagerFactory emConfigLocal = Persistence.createEntityManagerFactory("RestorantLocal");
  
    frmListadoFacturas frmlistf = null; //= new frmListadoFacturas();
    //Formularios de Busqueda
    frmFacturaORM frmfaO = null;
    frmBuscarproductos frmbuscapro = null;// = new frmBuscarproductos();
    frmBusclientes frmbuscacli = null;// = new frmBusclientes();
    frmReportes frmrpor = null;//=new frmReportes();
    EjecutarReporte report = null;//= new EjecutarReporte();
    //public DetallefacturaJpaController jpdfac = new DetallefacturaJpaController(emFac);
    Menuaccion act = new Menuaccion();
    public Integer ordenp = 0;
    FileReader fr = null;
    public String[] conexion_data = new String[3];
    File persistenciaXML;
    String rutaxml="src/META-INF/persistence.xml";
    ConfiguracionJpaController jpconfig;
    String sSistemaOperativo ="";
    public Menuprincipal() throws FileNotFoundException, IOException, SAXException, DOMException, ParserConfigurationException {
        initComponents();
        try {
            /**
             * *****Inicia Ejecucion de Comando par Obtener Num Serie HD***********
             */
            
            final Process p = Runtime.getRuntime().exec("cmd.exe /c vol c:");
            new Thread(new Runnable() {
                public void run() {
                    BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line = null;
                    try {
                        while ((line = input.readLine()) != null) {
                            String r = "";
                            if (line.contains(":")) {
                                String r2[] = line.split(":");
                                r = r2[1];
                            } else {
                                r = line;
                            }
                            System.out.println(r);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            try {
                p.waitFor();
            } catch (InterruptedException ex) {
                Logger.getLogger(Menuprincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
            leerpersistenciaXML();

            setSize(this.getToolkit().getScreenSize().width, this.getToolkit().getScreenSize().height);
            pnlmsg.setSize(this.getToolkit().getScreenSize().width, 80);
            pnlmsg.setLocation(0, this.getToolkit().getScreenSize().height - 200);
            setBackgroundImage();
            jMenuItem3.setVisible(false);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Menuprincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Menuprincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Menuprincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Menuprincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
/***
 *Permite modificar la ip de conexion al servidor
 **/
    public void leerpersistenciaXML() throws DOMException, SAXException, IOException, ParserConfigurationException {
       jpconfig=new ConfiguracionJpaController(emConfigLocal);
       sSistemaOperativo = System.getProperty("os.name");
            System.out.println(sSistemaOperativo);
            //configurando almacenamiento de sistema operativo
            Configuracion cf=new Configuracion();
           cf.setConfigid(null);cf.setConfigfreg(new Date());
            cf.setConfigdesc("Sistema operativo utilizado");
            cf.setConfigval(sSistemaOperativo);cf.setConfigstado(1);
            List<Configuracion> selSO=jpconfig.findConfiguracionSO(sSistemaOperativo);
            if (selSO!=null) {
                if (selSO.size()==0) {
             jpconfig.create(cf);
                }
              }  
            /***********************************************/
       Configuracion confi=jpconfig.findConfiguracion(1);
        try {
            persistenciaXML = new File("src/META-INF/persistence.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder loader = factory.newDocumentBuilder();
            Document document = (Document) loader.parse("src/META-INF/persistence.xml");
            DocumentTraversal trav = (DocumentTraversal) document;
            NodeIterator it = trav.createNodeIterator(document.getDocumentElement(),
                    NodeFilter.SHOW_ELEMENT, null, true);
            int c = 1;
            int nmy=0;
            for (Node node = it.nextNode(); node != null; node = it.nextNode()) {
                String name = node.getNodeName(); 
                //System.out.printf("%d %s%n", c, name);
                c++;
                if (node.getNodeName().equals("property")) {
                    //System.out.println("Nodo: "+node.getAttributes().item(0).getNodeValue());
                    // update staff attribute
                    NamedNodeMap attr = node.getAttributes();
                    // Node nodeAttr = attr.getNamedItem("value");
                    if(attr.item(0).getNodeValue().toString().equals("javax.persistence.jdbc.url"))
                    {
                        System.out.println("Valor URL JDBC:"+attr.item(1).getNodeValue());
                        if (attr.item(1).getNodeValue().contains("mysql") ) {
                            if ( nmy==0) {//"jdbc:mysql://localhost:3306/bdrestaurante?zeroDateTimeBehavior=convertToNull"
                                conexion_data=confi.getConfigval().split(";");
                                attr.item(1).setTextContent(conexion_data[0]);    
                            }
                             nmy++;
                        }
                    }
                }
            }
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(rutaxml));
            transformer.transform(source, result); 
            System.out.println("Actualizacion ip servidor exitosa!, hay "+nmy+" conexiones mysql");
        } catch (TransformerException ex) {
            Logger.getLogger(Menuprincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void setBackgroundImage() {
        try {
            //URL ubicacion = this.getClass().getClassLoader().getResource("Reportes/LogoCantones.jpg");
            File f = new File(Global.rutaActual() + "/Reportes/SistemaControlFacturas.jpg");// File("C:/javafacturacion/Reportes/LogoCantones.jpg");
            ImageIcon icon;
            icon = new ImageIcon(f.getPath());
            JLabel label = new JLabel(icon);
            label.setBounds(this.getToolkit().getScreenSize().width / 4, 100, icon.getIconWidth(), icon.getIconHeight());
            this.desktopPane.add(label, new Integer(Integer.MIN_VALUE));
        } catch (Exception e) {
            System.out.println("Error->" + e.getMessage());
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

        desktopPane = new javax.swing.JDesktopPane();
        pnlmsg = new javax.swing.JPanel();
        lblalerta = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        menuBar = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        itfactura = new javax.swing.JMenuItem();
        itprod = new javax.swing.JMenuItem();
        itfindcli = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        itcrearproduc = new javax.swing.JMenuItem();
        itcorrela = new javax.swing.JMenuItem();
        itclientes = new javax.swing.JMenuItem();
        itempresa = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        itimpuestos = new javax.swing.JMenuItem();
        itimport = new javax.swing.JMenuItem();
        itconfig = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        btnreport = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        contentMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Administracion Ventas");
        setName("Menu"); // NOI18N
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        desktopPane.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                desktopPaneKeyReleased(evt);
            }
        });

        lblalerta.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblalerta.setForeground(new java.awt.Color(255, 51, 51));
        lblalerta.setText("Alertas del Sistema, por el Momento no hay \nNinguna Alerta.");
        lblalerta.setAutoscrolls(true);

        javax.swing.GroupLayout pnlmsgLayout = new javax.swing.GroupLayout(pnlmsg);
        pnlmsg.setLayout(pnlmsgLayout);
        pnlmsgLayout.setHorizontalGroup(
            pnlmsgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlmsgLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblalerta, javax.swing.GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlmsgLayout.setVerticalGroup(
            pnlmsgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlmsgLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblalerta, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                .addContainerGap())
        );

        lblalerta.getAccessibleContext().setAccessibleName("");

        desktopPane.add(pnlmsg);
        pnlmsg.setBounds(0, 0, 850, 90);

        jToolBar1.setRollover(true);

        jMenu3.setText("Ventas");

        itfactura.setText("Nueva Facturacion");
        itfactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itfacturaActionPerformed(evt);
            }
        });
        jMenu3.add(itfactura);

        itprod.setText("Buscar Productos");
        itprod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itprodActionPerformed(evt);
            }
        });
        jMenu3.add(itprod);

        itfindcli.setText("Buscar Clientes");
        itfindcli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itfindcliActionPerformed(evt);
            }
        });
        jMenu3.add(itfindcli);

        jMenuItem4.setText("Buscar Bodegas");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuItem2.setText("Listado de Facturas");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem2);

        jMenuItem5.setText("Listado de Pedidos\nOnline");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);

        menuBar.add(jMenu3);

        jMenu1.setText("Administracion");

        jMenuItem1.setText("Usuarios");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        itcrearproduc.setText("Productos");
        itcrearproduc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itcrearproducActionPerformed(evt);
            }
        });
        jMenu1.add(itcrearproduc);

        itcorrela.setText("Correlativos");
        itcorrela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itcorrelaActionPerformed(evt);
            }
        });
        jMenu1.add(itcorrela);

        itclientes.setText("Clientes");
        itclientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itclientesActionPerformed(evt);
            }
        });
        jMenu1.add(itclientes);

        itempresa.setText("Empresas");
        itempresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itempresaActionPerformed(evt);
            }
        });
        jMenu1.add(itempresa);
        jMenu1.add(jSeparator1);

        jMenu4.setText("Codigos Configuracion");

        jMenuItem8.setText("Tipo Ordenes");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem8);

        jMenuItem10.setText("Tipo de Pagos");
        jMenu4.add(jMenuItem10);

        jMenuItem11.setText("Estados");
        jMenu4.add(jMenuItem11);

        itimpuestos.setText("Impuestos");
        jMenu4.add(itimpuestos);

        jMenu1.add(jMenu4);

        itimport.setText("Importar");
        itimport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itimportActionPerformed(evt);
            }
        });
        jMenu1.add(itimport);

        itconfig.setText("Configuracion Sistema");
        itconfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itconfigActionPerformed(evt);
            }
        });
        jMenu1.add(itconfig);

        menuBar.add(jMenu1);

        jMenu2.setText("Reportes");

        btnreport.setMnemonic('s');
        btnreport.setText("Reportes Gerenciales");
        btnreport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnreportActionPerformed(evt);
            }
        });
        jMenu2.add(btnreport);

        jMenuItem3.setText("Administracion");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        menuBar.add(jMenu2);

        helpMenu.setMnemonic('h');
        helpMenu.setText("Ayuda");

        contentMenuItem.setMnemonic('c');
        contentMenuItem.setText("Manual");
        helpMenu.add(contentMenuItem);

        aboutMenuItem.setMnemonic('a');
        aboutMenuItem.setText("Acerca del Sistema");
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 847, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnreportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnreportActionPerformed
        // TODO add your handling code here:
            if (1 == Global.admin) {
            frmrpor = new frmReportes();
            this.desktopPane.add(frmrpor);
            frmrpor.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            frmrpor.setLocation(this.desktopPane.getWidth() / 4, 0);
            frmrpor.setVisible(true);
            frmrpor.pack();
            frmrpor.show();
            } else {
                JOptionPane.showMessageDialog(rootPane, "No tiene permisos para acceder a esta opcion!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        
        
    }//GEN-LAST:event_btnreportActionPerformed

    private void itfacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itfacturaActionPerformed
        abrirFacturaOrm();
       //abrirFacturav61();
    }//GEN-LAST:event_itfacturaActionPerformed
 
      public void abrirFacturaOrm() {
        frmfaO = new frmFacturaORM();
        //frmFacturav6 frmfac6 = new frmFacturav6();
        frmfaO.setClosable(true);
        frmfaO.setLocation(0, 0);
        frmfaO.pack();
        this.desktopPane.add(frmfaO);
        frmfaO.setVisible(true);
    }
      
    public void imprimirOrden(String nf) {
        // TODO add your handling code here:
        try {
            Integer id = Integer.parseInt(nf);
            if (id > 0) {
                /*   if (jpdfac.getDetestadoCount(id) > 0) {
                    report.startOrdenCocinaprint("" + id);
                    System.out.println("" + jpdfac.actualizarEstadodet(id));
                } else {
                    JOptionPane.showMessageDialog(this, "No hay Ordenes de Cocina pendientes!", "Orden de Cocina", JOptionPane.INFORMATION_MESSAGE);
                }*/
            }
        } catch (Exception e) {
        }

    }

    public void imprimirFactura(String nf) {
        // TODO add your handling code here:
        try {
            Integer id = Integer.parseInt(nf);
            if (id != null) {
                if (id > 0) {
                    report.startReportprint("" + id);
                }
            } else {
                JOptionPane.showMessageDialog(this, "La factura no ha sido cobrada!, presione \"Guardar\" para Cobrar!", "Facturacion!", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println("Error->" + e.getMessage());
        }
    }

  
    private void itprodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itprodActionPerformed
        JDialog jdet = new JDialog();
        jdet.setContentPane(new panelDetV3());
        jdet.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jdet.pack();
        jdet.setVisible(true);
    }//GEN-LAST:event_itprodActionPerformed

    private void itcrearproducActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itcrearproducActionPerformed
        buscarProductov3();
    }//GEN-LAST:event_itcrearproducActionPerformed

    public void buscarProductov3() {
        JDialog jdet = new JDialog();
        jdet.setContentPane(new panelDetV3());
        //jdet.setLocation(10,100);
        jdet.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jdet.pack();
        jdet.setModal(true);
        jdet.setVisible(true);
        if (((panelDetV3) jdet.getContentPane()).addpro == 1) {
            System.out.println("Producto Consultado");
        } else {
            //JOptionPane.showMessageDialog(rootPane, "La factura ya esta cerrada!", "Consulte al Administrador.", JOptionPane.WARNING_MESSAGE);

        }
    }

    private void itempresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itempresaActionPerformed
        // TODO add your handling code here:
        frmEmpresa empresa = new frmEmpresa();
        empresa.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.desktopPane.add(empresa);
        empresa.setVisible(true);
        this.desktopPane.selectFrame(true);
        empresa.setFocusable(true);
        empresa.setClosable(true);
        empresa.setLocation(this.desktopPane.getWidth() / 4, 0);
        empresa.pack();
        empresa.show();
    }//GEN-LAST:event_itempresaActionPerformed

    private void itclientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itclientesActionPerformed

    }//GEN-LAST:event_itclientesActionPerformed

    private void itcorrelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itcorrelaActionPerformed
        frmCorrelativos correlativo = new frmCorrelativos();
        correlativo.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.desktopPane.add(correlativo);
        correlativo.setVisible(true);
        this.desktopPane.selectFrame(true);
        correlativo.setFocusable(true);
        correlativo.setClosable(true);
        correlativo.setLocation(this.desktopPane.getWidth() / 4, 0);
        correlativo.pack();
        correlativo.show();
    }//GEN-LAST:event_itcorrelaActionPerformed

    private void itfindcliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itfindcliActionPerformed
        abrirBuscacli();
    }//GEN-LAST:event_itfindcliActionPerformed

    public void abrirBuscacli() {
        JInternalFrame jfindcli = new JInternalFrame("Buscar Clientes!");
        jfindcli.add(new panelFindcli());
        this.desktopPane.add(jfindcli);
        jfindcli.setVisible(true);
        jfindcli.setClosable(true);
        jfindcli.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //jfindcli.setLocation(50,100);
        jfindcli.pack();
        //jfindcli.show();
    }

    private void desktopPaneKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_desktopPaneKeyReleased
        EventosForm(evt);
    }//GEN-LAST:event_desktopPaneKeyReleased

    public void EventosForm(java.awt.event.KeyEvent evt) throws AssertionError {
        // TODO add your handling code here:
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_F3:
               abrirFacturaOrm();
                break;
            case KeyEvent.VK_ESCAPE:
                this.dispose();
                break;
            case KeyEvent.VK_F12:
                abrirAdminfac();
                break;
            default:
                throw new AssertionError();
        }
    }

    public void abrirAdminfac() {
        AdministracionX frmadmin = new AdministracionX();
        this.desktopPane.add(frmadmin);
        frmadmin.setVisible(true);
        frmadmin.show();
    }

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        // TODO add your handling code here:
        EventosForm(evt);
    }//GEN-LAST:event_formKeyReleased

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        listadoFacturas();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    public void listadoFacturas() {
        frmListadoFacturas frmlistf = new frmListadoFacturas();
        //frmlistf.setVisible(true);
        this.desktopPane.add(frmlistf);
        frmlistf.setLocation(10, 0);
        frmlistf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frmlistf.setClosable(true);
        frmlistf.setVisible(true);
        frmlistf.pack();
        frmlistf.show();
    }
    
    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        abrirAdminfac();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void itimportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itimportActionPerformed
        // TODO add your handling code here:
        frmImport frmIm = new frmImport();
        this.desktopPane.add(frmIm);
        frmIm.setVisible(true);
        frmIm.show();
    }//GEN-LAST:event_itimportActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        frmPedidosonline frmIm = new frmPedidosonline();
        this.desktopPane.add(frmIm);
        frmIm.setVisible(true);
        frmIm.show();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void itconfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itconfigActionPerformed
        // TODO add your handling code here:        
            if (1 == 1) {
                frmConfiguracion frmconfig=new frmConfiguracion();
                this.desktopPane.add(frmconfig);
               frmconfig.setVisible(true);
               frmconfig.show();
            } else {
                JOptionPane.showMessageDialog(rootPane, "No tiene permisos para acceder a esta opcion!", "Error", JOptionPane.ERROR_MESSAGE);
                
            }
        
    }//GEN-LAST:event_itconfigActionPerformed

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
            java.util.logging.Logger.getLogger(Menuprincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menuprincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menuprincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menuprincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    try {
                        new Menuprincipal().setVisible(true);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Menuprincipal.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SAXException ex) {
                        Logger.getLogger(Menuprincipal.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (DOMException ex) {
                        Logger.getLogger(Menuprincipal.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ParserConfigurationException ex) {
                        Logger.getLogger(Menuprincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Menuprincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public boolean buscarFrm() {
        boolean a = false;
        try {
            //JInternalFrame ob = this.desktopPane.getSelectedFrame();
            for (JInternalFrame ob : this.desktopPane.getAllFrames()) {
                if (ob.isShowing()) {
                    System.out.println("" + ob.getClass().toString());
                    a = true;
                }
            }
        } catch (Exception e) {
            System.out.println("" + e.getMessage());
        }
        return a;
    }

    public boolean buscarFrm2(JInternalFrame act) {
        boolean a = false;
        try {
            //JInternalFrame ob = this.desktopPane.getSelectedFrame();
            for (JInternalFrame ob : this.desktopPane.getAllFrames()) {
                if (ob != null) {
                    if (ob.isShowing() == act.isShowing()) {
                        System.out.println("" + ob.getClass().toString());
                        a = true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("" + e.getMessage());
        }
        return a;
    }

    public void cargarBarra() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Simulate doing something useful.
                /* for (int i = 0; i <= jProgressBar1.getMaximum(); i++) {
                    Thread.sleep(200);
                    jLabel1.setText(Integer.toString(i));
                    updateBar(i);
                    System.out.println("Running " + i);
                }*/
                return null;
            }
        };

        worker.execute();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem btnreport;
    private javax.swing.JMenuItem contentMenuItem;
    public javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem itclientes;
    private javax.swing.JMenuItem itconfig;
    private javax.swing.JMenuItem itcorrela;
    private javax.swing.JMenuItem itcrearproduc;
    private javax.swing.JMenuItem itempresa;
    private javax.swing.JMenuItem itfactura;
    private javax.swing.JMenuItem itfindcli;
    private javax.swing.JMenuItem itimport;
    private javax.swing.JMenuItem itimpuestos;
    private javax.swing.JMenuItem itprod;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    public javax.swing.JLabel lblalerta;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JPanel pnlmsg;
    // End of variables declaration//GEN-END:variables

}
