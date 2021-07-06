package com.siguasystem.facturacionjava;

/**
 *
 * @author Javier Dom{inguez Geniz http://ajdgeniz.wordpress.com Modificado por
 * Juan Carlos Ramos http://siguasystem.com jsystemr@gmail.com
 */
import com.siguasystem.config.ConfiguracionJpaController;
import com.siguasystem.modelo.Configuracion;
import java.awt.Dialog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;
import javax.swing.WindowConstants;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.*;

public class EjecutarReporte {

    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String RUTA = "jdbc:mysql://localhost:3306/bdrestaurante";
    ///"jdbc:mysql://192.168.0.103:3306/bdrestaurante";
    public String USER = "root";
    public String PASSWORD = "";// linuxdeb//cantones
    public static Connection CONEXION;
    public String jdbcruta = "";
    public File ruta;
    EntityManagerFactory em = Persistence.createEntityManagerFactory("Restorant");
     EntityManagerFactory emlocal = Persistence.createEntityManagerFactory("RestorantLocal");
    FileReader fr = null;
    public String[] conexion_data = new String[3];
    EntityManagerFactory emConfigLocal = Persistence.createEntityManagerFactory("RestorantLocal");
    String rutaxml="src/META-INF/persistence.xml";
   ConfiguracionJpaController jpconfig=new ConfiguracionJpaController(emConfigLocal);
    public EjecutarReporte() {
        try {
            Configuracion confi=jpconfig.findConfiguracion(1);
            Class.forName(DRIVER);
                conexion_data=confi.getConfigval().split(";");
                CONEXION = DriverManager.getConnection(conexion_data[0], conexion_data[1], conexion_data[2]);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EjecutarReporte.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EjecutarReporte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarConexion(String rutajdbc, String user, String pass) {
        try {
            Class.forName(DRIVER);
            try {
                CONEXION = DriverManager.getConnection(rutajdbc, user, pass);
            } catch (SQLException ex) {
                Logger.getLogger(EjecutarReporte.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EjecutarReporte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void startReport(int id) {
        /* try {

            //javax.swing.JOptionPane.showMessageDialog(null,"Conexion establecida");
            URL ubicacion = this.getClass().getResource("Catalogo.jasper");
            String template = "Catalogo.jasper";
            JasperReport reporte = null;
            reporte = (JasperReport) JRLoader.loadObject(ubicacion);

            Map param = new HashMap();
            param.put("id", id);
            param.put(JRJpaQueryExecuterFactory.PARAMETER_JPA_ENTITY_MANAGER, em);
            em.getTransaction().begin();
            JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param);
            JasperViewer visor = new JasperViewer(jasperprint, false);
            visor.setTitle("Geniz Reportes - GSF");
            visor.setVisible(true);

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, e);
        }*/
    }

    public void startReport(String id) {
        try {
            // javax.swing.JOptionPane.showMessageDialog(null,"Conexion establecida");
            ruta = new File(Global.rutaActual() + "/Reportes/factura.jasper");
            URL ubicacion = ruta.toURI().toURL();
            // URL detalle = new URL(Global.rutaActual() + "/Reportes/factura_detalle.jasper");
            JasperReport reporte = null;
            reporte = (JasperReport) JRLoader.loadObject(ubicacion);
            String sql = reporte.getQuery().getText();
            String ruta = ubicacion.toURI().toString();
            Map param = new HashMap();
            param.put("id", id);
            JpaAtributos();
            actualizarConexion(jdbcruta, USER, PASSWORD);
            //param.put("REPORT_CONNECTION", cons);
            // param.put("SUBREPORT_DIR",detalle.toURI().toString());
            //JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param);
            JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param, CONEXION);

            JasperViewer visor = new JasperViewer(jasperprint, false);
            visor.setTitle("Impresion de Factura");
            visor.setVisible(true);
            //CONEXION.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, e);
        }
    }

    public void startReportL(String id,String tot) {
        try {
            // javax.swing.JOptionPane.showMessageDialog(null,"Conexion establecida");
            ruta = new File(Global.rutaActual() + "/Reportes/factura.jasper");
            URL ubicacion = ruta.toURI().toURL();
            // URL detalle = new URL(Global.rutaActual() + "/Reportes/factura_detalle.jasper");
            JasperReport reporte = null;
            reporte = (JasperReport) JRLoader.loadObject(ubicacion);
            String sql = reporte.getQuery().getText();
            String ruta = ubicacion.toURI().toString();
            Map param = new HashMap();
            param.put("id", id);
            param.put("tot", NumtoLetras.cantidadConLetra(tot));
            JpaAtributos();
            actualizarConexion(jdbcruta, USER, PASSWORD);
            //param.put("REPORT_CONNECTION", cons);
            // param.put("SUBREPORT_DIR",detalle.toURI().toString());
            //JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param);
            JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param, CONEXION);

            JasperViewer visor = new JasperViewer(jasperprint, false);
            visor.setTitle("Impresion de Factura");
            visor.setVisible(true);
            //CONEXION.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, e);
        }
    }

    
    public void JpaAtributos() {
        em.createEntityManager().getTransaction().begin();
        Map<String, Object> propertiesMap = propertiesMap = em.getProperties();
        for (Map.Entry<String, Object> e : propertiesMap.entrySet()) {
            if (e.getKey().equals("javax.persistence.jdbc.url")) {
                jdbcruta = e.getValue().toString();
                // System.out.println("key=" + e.getKey() + " value = " + e.getValue().toString());
                //break;
            }
            if (e.getKey().equals("javax.persistence.jdbc.user")) {
                USER = e.getValue().toString();
                // System.out.println("key=" + e.getKey() + " value = " + e.getValue().toString());
                //break;
            }
            if (e.getKey().equals("javax.persistence.jdbc.password")) {
                PASSWORD = e.getValue().toString();
                // System.out.println("key=" + e.getKey() + " value = " + e.getValue().toString());
                //break;
            }
            //System.out.println("key=" + e.getKey() + " value = " + e.getValue().toString());
        }

    }
    
     public void JpaAtributos2() {
        emlocal.createEntityManager().getTransaction().begin();
        Map<String, Object> propertiesMap = propertiesMap = emlocal.getProperties();
        for (Map.Entry<String, Object> e : propertiesMap.entrySet()) {
            if (e.getKey().equals("javax.persistence.jdbc.url")) {
                jdbcruta = e.getValue().toString();
                // System.out.println("key=" + e.getKey() + " value = " + e.getValue().toString());
                //break;
            }
            if (e.getKey().equals("javax.persistence.jdbc.user")) {
                USER = e.getValue().toString();
                // System.out.println("key=" + e.getKey() + " value = " + e.getValue().toString());
                //break;
            }
            if (e.getKey().equals("javax.persistence.jdbc.password")) {
                PASSWORD = e.getValue().toString();
                // System.out.println("key=" + e.getKey() + " value = " + e.getValue().toString());
                //break;
            }
            //System.out.println("key=" + e.getKey() + " value = " + e.getValue().toString());
        }

    }

    public void startReportprint(String id) {
        try {
            ruta = new File(Global.rutaActual() + "/Reportes/factura.jasper");
            URL ubicacion = ruta.toURI().toURL();
            ruta = new File(Global.rutaActual() + "/Reportes/factura_detalle.jasper");
            URL detalle = ruta.toURI().toURL(); //new URL(Global.rutarpt + "factura_detalle.jasper");
            JasperReport reporte = null;
            reporte = (JasperReport) JRLoader.loadObject(ubicacion);
            String sql = reporte.getQuery().getText();
            String ruta = ubicacion.toURI().toString();
            Map param = new HashMap();
            param.put("id", id);
           
            JpaAtributos();
                        
            actualizarConexion(jdbcruta, USER, PASSWORD);
            JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param, CONEXION);
            //JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param);
            if (reporte != null) {
                boolean printSucceed = JasperPrintManager.printReport(jasperprint, true);//Imprime Directamente o Muestra el dialogo de impresoras
                jasperprint = null;
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, e);
        } finally {
           
        }
    }
    
    public void startReportprintL(String id,String total) {
        try {
            ruta = new File(Global.rutaActual() + "/Reportes/factura.jasper");
            URL ubicacion = ruta.toURI().toURL();
            ruta = new File(Global.rutaActual() + "/Reportes/factura_detalle.jasper");
            URL detalle = ruta.toURI().toURL(); //new URL(Global.rutarpt + "factura_detalle.jasper");
            JasperReport reporte = null;
            reporte = (JasperReport) JRLoader.loadObject(ubicacion);
            String sql = reporte.getQuery().getText();
            String ruta = ubicacion.toURI().toString();
            Map param = new HashMap();
            param.put("id", id);
            param.put("tot", NumtoLetras.cantidadConLetra(total));
            JpaAtributos();
                        
            actualizarConexion(jdbcruta, USER, PASSWORD);
            JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param, CONEXION);
            //JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param);
            if (reporte != null) {
                boolean printSucceed = JasperPrintManager.printReport(jasperprint, true);//Imprime Directamente o Muestra el dialogo de impresoras
                jasperprint = null;
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, e);
        } finally {
           
        }
    }
    
    public void startReportprintLMarcala(String id,String total) {
        try {
            ruta = new File(Global.rutaActual() + "/Reportes/facturamarcala.jasper");
            URL ubicacion = ruta.toURI().toURL();
            ruta = new File(Global.rutaActual() + "/Reportes/factura_detallemarcala.jasper");
            URL detalle = ruta.toURI().toURL(); //new URL(Global.rutarpt + "factura_detalle.jasper");
            JasperReport reporte = null;
            reporte = (JasperReport) JRLoader.loadObject(ubicacion);
            String sql = reporte.getQuery().getText();
            String ruta = ubicacion.toURI().toString();
            Map param = new HashMap();
            param.put("id", id);
            param.put("tot", NumtoLetras.cantidadConLetra(total));
            JpaAtributos();
                        
            actualizarConexion(jdbcruta, USER, PASSWORD);
            JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param, CONEXION);
            //JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param);
            if (reporte != null) {
                boolean printSucceed = JasperPrintManager.printReport(jasperprint, true);//Imprime Directamente o Muestra el dialogo de impresoras
                jasperprint = null;
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, e);
        } finally {
           
        }
    }
    
    public void startReportprint(String id,int local) {
        try {
            ruta = new File(Global.rutaActual() + "/Reportes/factura.jasper");
            URL ubicacion = ruta.toURI().toURL();
            ruta = new File(Global.rutaActual() + "/Reportes/factura_detalle.jasper");
            URL detalle = ruta.toURI().toURL(); //new URL(Global.rutarpt + "factura_detalle.jasper");
            JasperReport reporte = null;
            reporte = (JasperReport) JRLoader.loadObject(ubicacion);
            String sql = reporte.getQuery().getText();
            String ruta = ubicacion.toURI().toString();
            Map param = new HashMap();
            param.put("id", id);
            if (local==1) {
                JpaAtributos2();
            }else{
                JpaAtributos();
            }
            
            actualizarConexion(jdbcruta, USER, PASSWORD);
            //em = Persistence.createEntityManagerFactory("Restorant");
            ///em.createEntityManager().getTransaction().begin();
            /// em.createEntityManager().setFlushMode(FlushModeType.AUTO);
            JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param, CONEXION);
            //JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param);
            if (reporte != null) {
                boolean printSucceed = JasperPrintManager.printReport(jasperprint, true);//Imprime Directamente o Muestra el dialogo de impresoras
                jasperprint = null;
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, e);
        } finally {
            // em.close();
            /*try {
                CONEXION.close();
               
            } catch (SQLException ex) {
                Logger.getLogger(EjecutarReporte.class.getName()).log(Level.SEVERE, null, ex);
            }*/
 /* if (em.createEntityManager().isOpen()) {
                //em.createEntityManager().flush();
                em.createEntityManager().close();
                
            }*/
        }
    }

    public void startOrdenCocina(String id) {
        try {

            ruta = new File(Global.rutaActual() + "/Reportes/ordencocina.jasper");
            URL ubicacion = ruta.toURI().toURL();
            JasperReport reporte = null;
            reporte = (JasperReport) JRLoader.loadObject(ubicacion);
            String sql = reporte.getQuery().getText();
            String ruta = ubicacion.toURI().toString();
            Map param = new HashMap();
            param.put("id", id);
            JpaAtributos();
            actualizarConexion(jdbcruta, USER, PASSWORD);
            ///em.createEntityManager().getTransaction().begin();
            JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param, CONEXION);
            JasperViewer visor = new JasperViewer(jasperprint, false);
            visor.setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
            visor.setTitle("Orden de Cocina");
            visor.setVisible(true);

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, e);
        } finally {
            /* if (em.createEntityManager().isOpen()) {
                  em.createEntityManager().close();
               
            }*/
            //em.close();
        }
    }

    public void startOrdenCocinatotpre(String id) {
        try {
            ruta = new File(Global.rutaActual() + "/Reportes/ordencocinav2.jasper");
            URL ubicacion = ruta.toURI().toURL();
            JasperReport reporte = null;
            reporte = (JasperReport) JRLoader.loadObject(ubicacion);
            String sql = reporte.getQuery().getText();
            String ruta = ubicacion.toURI().toString();
            Map param = new HashMap();
            param.put("id", id);
            JpaAtributos();
            actualizarConexion(jdbcruta, USER, PASSWORD);
            ///  em.createEntityManager().getTransaction().begin();
            JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param, CONEXION);
            JasperViewer visor = new JasperViewer(jasperprint, false);
            visor.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            visor.setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
            visor.setTitle("Orden de Cocina");
            visor.setVisible(true);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, e);
        } finally {
            /*    if (em.createEntityManager().isOpen()) {
                  em.createEntityManager().close();
                
            }*/

        }
    }
    

    public void startOrdenCocinaprint(String id) {
        JasperReport reporte = null;
        try {
            ruta = new File(Global.rutaActual() + "/Reportes/ordencocina.jasper");//ordencocina.jasper
            URL ubicacion = ruta.toURI().toURL();
            reporte = (JasperReport) JRLoader.loadObject(ubicacion);
            String sql = reporte.getQuery().getText();
            String ruta = ubicacion.toURI().toString();
            Map param = new HashMap();
            param.put("id", id);
            JpaAtributos();
            actualizarConexion(jdbcruta, USER, PASSWORD);
            //em = Persistence.createEntityManagerFactory("Restorant");
            /// em.createEntityManager().getTransaction().begin();
            /// em.createEntityManager().setFlushMode(FlushModeType.AUTO);
            JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param, CONEXION);
            if (reporte != null) {
                JasperPrintManager.printReport(jasperprint, true);//Imprime Directamente o Muestra el dialogo de impresoras
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, e);
        } finally {
            /*try {
                   CONEXION.close();
                    //em.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EjecutarReporte.class.getName()).log(Level.SEVERE, null, ex);
                }*/
 /* if (em.createEntityManager().isOpen()) {
                em.createEntityManager().close();
               
            }*/
            // em.close();
        }
    }

    public void startOrdenCocinaprintTot(String id) {
        try {

            ruta = new File(Global.rutaActual() + "/Reportes/ordencocinav2.jasper");
            URL ubicacion = ruta.toURI().toURL();
            JasperReport reporte = null;
            reporte = (JasperReport) JRLoader.loadObject(ubicacion);
            String sql = reporte.getQuery().getText();
            String ruta = ubicacion.toURI().toString();
            Map param = new HashMap();
            param.put("id", id);
            JpaAtributos();
            actualizarConexion(jdbcruta, USER, PASSWORD);
            ///em.createEntityManager().getTransaction().begin();
            JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param, CONEXION);
            if (reporte != null) {
                JasperPrintManager.printReport(jasperprint, true);//Imprime Directamente o Muestra el dialogo de impresoras
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, e);
        } finally {
            /*if (em.createEntityManager().isOpen()) {
                em.close();
            }*/
            // em.close();
        }
    }

    public void startReportfac(String dia, String mes, String anio) {
        try {

            ruta = new File(Global.rutaActual() + "/Reportes/facturaVentashoy.jasper");
            URL ubicacion = ruta.toURI().toURL();
            JasperReport reporte = null;
            reporte = (JasperReport) JRLoader.loadObject(ubicacion);
            String sql = reporte.getQuery().getText();
            String ruta = ubicacion.toURI().toString();
            Map param = new HashMap();
            param.put("dia", dia);
            param.put("mes", mes);
            param.put("anio", anio);
            ///JpaAtributos();
            ///actualizarConexion(jdbcruta, USER, PASSWORD);
            ///em.createEntityManager().getTransaction().begin();
            JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param, CONEXION);
            if (reporte != null) {
                JasperViewer visor = new JasperViewer(jasperprint, false);
                visor.setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
                visor.setTitle("Reporte de Ventas " + dia + "-" + mes + "-" + anio);
                visor.setVisible(true);
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, e);
        } finally {
            /* if (em.createEntityManager().isOpen()) {
               // em.close();
               em.createEntityManager().close();
            }*/
        }
    }
    
     public void startReportfac(java.util.Date f1) {
        try {

            ruta = new File(Global.rutaActual() + "/Reportes/facturaVentashoyd.jasper");
            URL ubicacion = ruta.toURI().toURL();
            JasperReport reporte = null;
            reporte = (JasperReport) JRLoader.loadObject(ubicacion);
            String sql = reporte.getQuery().getText();
            String ruta = ubicacion.toURI().toString();
            Map param = new HashMap();
            param.put("f1", f1);

            JpaAtributos();
            actualizarConexion(jdbcruta, USER, PASSWORD);
            ///em.createEntityManager().getTransaction().begin();
            JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param, CONEXION);
            if (reporte != null) {
                JasperViewer visor = new JasperViewer(jasperprint, false);
                visor.setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
                visor.setTitle("Reporte de Ventas " +f1.toLocaleString());
                visor.setVisible(true);
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, e);
        } finally {
            /* if (em.createEntityManager().isOpen()) {
               // em.close();
               em.createEntityManager().close();
            }*/
        }
    }

    public void startReportfac2(String dia, String mes, String anio, String dia2, String mes2, String anio2) {
        try {
            ruta = new File(Global.rutaActual() + "/Reportes/facturaVentashoyr.jasper");
            URL ubicacion = ruta.toURI().toURL();
            JasperReport reporte = null;
            reporte = (JasperReport) JRLoader.loadObject(ubicacion);
            String sql = reporte.getQuery().getText();
            String ruta = ubicacion.toURI().toString();
            Map param = new HashMap();
            param.put("dia", dia);
            param.put("mes", mes);
            param.put("anio", anio);
            param.put("dia2", dia2);
            param.put("mes2", mes2);
            param.put("anio2", anio2);
            JpaAtributos();
            actualizarConexion(jdbcruta, USER, PASSWORD);
            ///em.createEntityManager().getTransaction().begin();
            JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param, CONEXION);
            if (reporte != null) {
                JasperViewer visor = new JasperViewer(jasperprint, false);
                visor.setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
                visor.setTitle("Reporte de Ventas " + dia + "-" + mes + "-" + anio);
                visor.setVisible(true);
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, e);
        } finally {
            /*if (em.createEntityManager().isOpen()) {
                //em.close();
                em.createEntityManager().close();
            }*/
            //em.close();
        }
    }
    
    public void startReportfac2(java.util.Date f1, java.util.Date f2) {
        try {
            ruta = new File(Global.rutaActual() + "/Reportes/facturaVentashoyrd.jasper");
            URL ubicacion = ruta.toURI().toURL();
            JasperReport reporte = null;
            reporte = (JasperReport) JRLoader.loadObject(ubicacion);
            String sql = reporte.getQuery().getText();
            String ruta = ubicacion.toURI().toString();
            Map param = new HashMap();
            param.put("f1", f1);
            param.put("f2", f2);
            JpaAtributos();
            actualizarConexion(jdbcruta, USER, PASSWORD);
            ///em.createEntityManager().getTransaction().begin();
            JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param, CONEXION);
            if (reporte != null) {
                JasperViewer visor = new JasperViewer(jasperprint, false);
                visor.setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
                visor.setTitle("Reporte de Ventas del " + f1.toLocaleString() + " hasta el " + f2.toLocaleString());
                visor.setVisible(true);
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, e);
        } finally {
            /*if (em.createEntityManager().isOpen()) {
                //em.close();
                em.createEntityManager().close();
            }*/
            //em.close();
        }
    }

    public void startReportEx1(String dia, String mes, String anio) {
        try {
            ruta = new File(Global.rutaActual() + "/Reportes/Reporteproex.jasper");
            URL ubicacion = ruta.toURI().toURL();
            JasperReport reporte = null;
            reporte = (JasperReport) JRLoader.loadObject(ubicacion);
            String sql = reporte.getQuery().getText();
            String ruta = ubicacion.toURI().toString();
            Map param = new HashMap();
            param.put("dia", dia);
            param.put("mes", mes);
            param.put("anio", anio);
            JpaAtributos();
            actualizarConexion(jdbcruta, USER, PASSWORD);
            /// em.createEntityManager().getTransaction().begin();
            JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param, CONEXION);
            if (reporte != null) {
                JasperViewer visor = new JasperViewer(jasperprint, false);
                visor.setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
                visor.setTitle("Reporte de Ventas " + dia + "-" + mes + "-" + anio);
                visor.setVisible(true);
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, e);
        } finally {
            /* if (em.createEntityManager().isOpen()) {
               // em.close();
               em.createEntityManager().close();
            }*/
            //em.close();
        }
    }
    
      public void startReportEx1(java.util.Date f1) {
        try {
            ruta = new File(Global.rutaActual() + "/Reportes/Reporteproex.jasper");
            URL ubicacion = ruta.toURI().toURL();
            JasperReport reporte = null;
            reporte = (JasperReport) JRLoader.loadObject(ubicacion);
            String sql = reporte.getQuery().getText();
            String ruta = ubicacion.toURI().toString();
            Map param = new HashMap();
            param.put("f1", f1);
  
            JpaAtributos();
            actualizarConexion(jdbcruta, USER, PASSWORD);
            JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param, CONEXION);
            if (reporte != null) {
                JasperViewer visor = new JasperViewer(jasperprint, false);
                visor.setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
                visor.setTitle("Reporte de Ventas " +f1.toLocaleString());
                visor.setVisible(true);
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, e);
        } finally {
            /* if (em.createEntityManager().isOpen()) {
               // em.close();
               em.createEntityManager().close();
            }*/
            //em.close();
        }
    }
    
    public void startReportReciboFechasVentas(java.util.Date f1, java.util.Date f2,int print) {
        try {
            ruta = new File(Global.rutaActual() + "/Reportes/ReporteVentasGraf.jasper");
            URL ubicacion = ruta.toURI().toURL();
            JasperReport reporte = null;
            reporte = (JasperReport) JRLoader.loadObject(ubicacion);
            String sql = reporte.getQuery().getText();
            String ruta = ubicacion.toURI().toString();
            Map param = new HashMap();
            param.put("f1", f1);
            param.put("f2", f2);
            JpaAtributos();
            actualizarConexion(jdbcruta, USER, PASSWORD);
            if (print == 0) {
                JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param, CONEXION);
                //JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param);
                if (reporte != null) {
                    boolean printSucceed = JasperPrintManager.printReport(jasperprint, true);//Imprime Directamente o Muestra el dialogo de impresoras
                    jasperprint = null;
                }
            } else {
                JasperPrint jasperprint = JasperFillManager.fillReport(reporte, param, CONEXION);
                if (reporte != null) {
                    JasperViewer visor = new JasperViewer(jasperprint, false);
                    visor.setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
                    visor.setTitle("************Reporte de Ventas************");
                    visor.setVisible(true);
                }
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, e);
        } finally {
        }
    }

    public void closeConexion() {
        try {
            CONEXION.close();
        } catch (SQLException ex) {
            Logger.getLogger(EjecutarReporte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
