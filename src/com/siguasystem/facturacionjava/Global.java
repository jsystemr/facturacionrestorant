/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siguasystem.facturacionjava;

import com.siguasystem.modelo.Cliente;
import com.siguasystem.modelo.ClienteJpaController;
import com.siguasystem.modelo.Factura;
import com.siguasystem.modelo.Usuario;
import com.sun.glass.events.KeyEvent;
import java.awt.KeyboardFocusManager;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.KeyStroke;

/**
 *
 * @author jramos
 */
public abstract class Global {

    private ClienteJpaController fincli = new ClienteJpaController(Persistence.createEntityManagerFactory("Restorant"));
    public EntityManagerFactory emFac = Persistence.createEntityManagerFactory("Restorant");
    public static String rutarpt = "C:/facturacionjava/Reportes/";
    public static Usuario ulogin;
    public static int admin=1;
    public static String rutaActual() {
        String r="";
        File miDir = new File(".");
        try {
            System.out.println("Directorio actual: " + miDir.getCanonicalPath());
            r=miDir.getCanonicalPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  r;
    }

    public static void cambiarFoco(JInternalFrame f) {
        Set forwardKeys = f.getFocusTraversalKeys(
                KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS);
        Set newForwardKeys = new HashSet(forwardKeys);
        newForwardKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
        f.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
                newForwardKeys);
    }
    
     public static void cambiarFoco(JFrame f) {
        Set forwardKeys = f.getFocusTraversalKeys(
                KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS);
        Set newForwardKeys = new HashSet(forwardKeys);
        newForwardKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
        f.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
                newForwardKeys);
    }

    public static void cambiarFoco(JDialog f) {
        Set forwardKeys = f.getFocusTraversalKeys(
                KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS);
        Set newForwardKeys = new HashSet(forwardKeys);
        newForwardKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
        f.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
                newForwardKeys);
    }

    public static void salirFormi(java.awt.event.KeyEvent evt, JInternalFrame f) {
        switch (evt.getKeyCode()) {
            case com.sun.glass.events.KeyEvent.VK_ESCAPE:
                f.dispose();
                break;
            default:
                System.out.println("");
                ;
        }
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

        } catch (Exception e) {
        }
        return lcli;
    }

    public List<Cliente> Filcelclike(String cel) {

        List<Cliente> lcli = new ArrayList<Cliente>();
        try {
            String pt = cel.toLowerCase();
            Query qsql = fincli.getEntityManager().createNativeQuery("select * from cliente c where LOWER(c.telefono) LIKE \'" + pt + "%\' or  LOWER(c.celular) LIKE \'" + pt + "%\'", Cliente.class);//qsql.setParameter("n", txtnom.getText());
            lcli = qsql.getResultList();
            int i = 1;
            for (Iterator<Cliente> iterator = lcli.iterator(); iterator.hasNext();) {
                Cliente p = iterator.next();
                lcli.add(p);
            }
        } catch (Exception e) {
        }
        return lcli;
    }

    public Integer ultimaFact() {
        Integer x = 0;
        try {
            Query qsql = fincli.getEntityManager().createNativeQuery("select * from " + Factura.class.getName() + " c where max(c.idfactura)", Factura.class);//qsql.setParameter("n", txtnom.getText());
            x = Integer.parseInt(qsql.getSingleResult().toString());
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return x;
    }
    
    

}
