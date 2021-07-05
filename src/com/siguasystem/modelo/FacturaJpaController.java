/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siguasystem.modelo;

import com.siguasystem.modelos.exceptions.NonexistentEntityException;
import com.siguasystem.modelos.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.FlushModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author jramos
 */
public class FacturaJpaController implements Serializable {

    public FacturaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void saveFactura(Factura f) {
        System.out.println("Guardando factura...");
        // manager es el EntityManager obtenido anteriorment
         EntityManager em = getEntityManager();
         em.setFlushMode(FlushModeType.COMMIT);
        try {
            em.getTransaction().begin();
            em.persist(f);
            em.getTransaction().commit();
            System.out.println("Factura Guardada...");
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    public void create(Factura factura) throws PreexistingEntityException, Exception {
        if (factura.getFacturaPK() == null) {
            factura.setFacturaPK(new FacturaPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tiporden idorden = factura.getIdorden();
            if (idorden != null) {
                idorden = em.getReference(idorden.getClass(), idorden.getIdtiporden());
                factura.setIdorden(idorden);
            }
            Tipofactura tipofact = factura.getTipofact();
            if (tipofact != null) {
                tipofact = em.getReference(tipofact.getClass(), tipofact.getIdTipoFactura());
                factura.setTipofact(tipofact);
            }
            Cliente cliente = factura.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getIdCliente());
                factura.setCliente(cliente);
            }
            Empresa empresa = factura.getEmpresa();
            if (empresa != null) {
                empresa = em.getReference(empresa.getClass(), empresa.getIdEmpresa());
                factura.setEmpresa(empresa);
            }
            Estadofact estadofact = factura.getEstadofact();
            if (estadofact != null) {
                estadofact = em.getReference(estadofact.getClass(), estadofact.getIdestadofact());
                factura.setEstadofact(estadofact);
            }
            Usuario usuario = factura.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getLogin());
                factura.setUsuario(usuario);
            }
            em.persist(factura);
            if (idorden != null) {
                idorden.getFacturaList().add(factura);
                idorden = em.merge(idorden);
            }
            if (tipofact != null) {
                tipofact.getFacturaList().add(factura);
                tipofact = em.merge(tipofact);
            }
            /*if (cliente != null) {
                cliente.getFacturaList().add(factura);
                cliente = em.merge(cliente);
            }*/
            if (empresa != null) {
                empresa.getFacturaList().add(factura);
                empresa = em.merge(empresa);
            }
            if (estadofact != null) {
                estadofact.getFacturaList().add(factura);
                estadofact = em.merge(estadofact);
            }
            if (usuario != null) {
                usuario.getFacturaList().add(factura);
                usuario = em.merge(usuario);
            }
            em.flush();
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFactura(factura.getFacturaPK()) != null) {
                throw new PreexistingEntityException("Factura " + factura + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Factura factura) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Factura persistentFactura = em.find(Factura.class, factura.getFacturaPK());
            Tiporden idordenOld = persistentFactura.getIdorden();
            Tiporden idordenNew = factura.getIdorden();
            Tipofactura tipofactOld = persistentFactura.getTipofact();
            Tipofactura tipofactNew = factura.getTipofact();
            Cliente clienteOld = persistentFactura.getCliente();
            Cliente clienteNew = factura.getCliente();
            Empresa empresaOld = persistentFactura.getEmpresa();
            Empresa empresaNew = factura.getEmpresa();
            Estadofact estadofactOld = persistentFactura.getEstadofact();
            Estadofact estadofactNew = factura.getEstadofact();
            Usuario usuarioOld = persistentFactura.getUsuario();
            Usuario usuarioNew = factura.getUsuario();
            if (idordenNew != null) {
                idordenNew = em.getReference(idordenNew.getClass(), idordenNew.getIdtiporden());
                factura.setIdorden(idordenNew);
            }
            if (tipofactNew != null) {
                tipofactNew = em.getReference(tipofactNew.getClass(), tipofactNew.getIdTipoFactura());
                factura.setTipofact(tipofactNew);
            }
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getIdCliente());
                factura.setCliente(clienteNew);
            }
            if (empresaNew != null) {
                empresaNew = em.getReference(empresaNew.getClass(), empresaNew.getIdEmpresa());
                factura.setEmpresa(empresaNew);
            }
            if (estadofactNew != null) {
                estadofactNew = em.getReference(estadofactNew.getClass(), estadofactNew.getIdestadofact());
                factura.setEstadofact(estadofactNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getLogin());
                factura.setUsuario(usuarioNew);
            }
            factura = em.merge(factura);
            if (idordenOld != null && !idordenOld.equals(idordenNew)) {
                idordenOld.getFacturaList().remove(factura);
                idordenOld = em.merge(idordenOld);
            }
            if (idordenNew != null && !idordenNew.equals(idordenOld)) {
                idordenNew.getFacturaList().add(factura);
                idordenNew = em.merge(idordenNew);
            }
            if (tipofactOld != null && !tipofactOld.equals(tipofactNew)) {
                tipofactOld.getFacturaList().remove(factura);
                tipofactOld = em.merge(tipofactOld);
            }
            if (tipofactNew != null && !tipofactNew.equals(tipofactOld)) {
                tipofactNew.getFacturaList().add(factura);
                tipofactNew = em.merge(tipofactNew);
            }
           /* if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.getFacturaList().remove(factura);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.getFacturaList().add(factura);
                clienteNew = em.merge(clienteNew);
            }*/
            if (empresaOld != null && !empresaOld.equals(empresaNew)) {
                empresaOld.getFacturaList().remove(factura);
                empresaOld = em.merge(empresaOld);
            }
            if (empresaNew != null && !empresaNew.equals(empresaOld)) {
                empresaNew.getFacturaList().add(factura);
                empresaNew = em.merge(empresaNew);
            }
            if (estadofactOld != null && !estadofactOld.equals(estadofactNew)) {
                estadofactOld.getFacturaList().remove(factura);
                estadofactOld = em.merge(estadofactOld);
            }
            if (estadofactNew != null && !estadofactNew.equals(estadofactOld)) {
                estadofactNew.getFacturaList().add(factura);
                estadofactNew = em.merge(estadofactNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getFacturaList().remove(factura);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getFacturaList().add(factura);
                usuarioNew = em.merge(usuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                FacturaPK id = factura.getFacturaPK();
                if (findFactura(id) == null) {
                    throw new NonexistentEntityException("The factura with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(FacturaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Factura factura;
            try {
                factura = em.getReference(Factura.class, id);
                factura.getFacturaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The factura with id " + id + " no longer exists.", enfe);
            }
            Tiporden idorden = factura.getIdorden();
            if (idorden != null) {
                idorden.getFacturaList().remove(factura);
                idorden = em.merge(idorden);
            }
            Tipofactura tipofact = factura.getTipofact();
            if (tipofact != null) {
                tipofact.getFacturaList().remove(factura);
                tipofact = em.merge(tipofact);
            }
            Cliente cliente = factura.getCliente();
            /*if (cliente != null) {
                cliente.getFacturaList().remove(factura);
                cliente = em.merge(cliente);
            }*/
            Empresa empresa = factura.getEmpresa();
            if (empresa != null) {
                empresa.getFacturaList().remove(factura);
                empresa = em.merge(empresa);
            }
            Estadofact estadofact = factura.getEstadofact();
            if (estadofact != null) {
                estadofact.getFacturaList().remove(factura);
                estadofact = em.merge(estadofact);
            }
            Usuario usuario = factura.getUsuario();
            if (usuario != null) {
                usuario.getFacturaList().remove(factura);
                usuario = em.merge(usuario);
            }
            em.remove(factura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Factura> findFacturaEntities() {
        return findFacturaEntities(true, -1, -1);
    }

    public List<Factura> findFacturaEntities(int maxResults, int firstResult) {
        return findFacturaEntities(false, maxResults, firstResult);
    }

    private List<Factura> findFacturaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Factura.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Factura findFactura(FacturaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Factura.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Factura> rt = cq.from(Factura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
