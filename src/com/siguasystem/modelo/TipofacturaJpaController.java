/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siguasystem.modelo;

import com.siguasystem.modelo.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author joramos
 */
public class TipofacturaJpaController implements Serializable {

    public TipofacturaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipofactura tipofactura) {
        if (tipofactura.getFacturaList() == null) {
            tipofactura.setFacturaList(new ArrayList<Factura>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Factura> attachedFacturaList = new ArrayList<Factura>();
            for (Factura facturaListFacturaToAttach : tipofactura.getFacturaList()) {
                facturaListFacturaToAttach = em.getReference(facturaListFacturaToAttach.getClass(), facturaListFacturaToAttach.getId());
                attachedFacturaList.add(facturaListFacturaToAttach);
            }
            tipofactura.setFacturaList(attachedFacturaList);
            em.persist(tipofactura);
            for (Factura facturaListFactura : tipofactura.getFacturaList()) {
                Tipofactura oldTipofactOfFacturaListFactura = facturaListFactura.getTipofact();
                facturaListFactura.setTipofact(tipofactura);
                facturaListFactura = em.merge(facturaListFactura);
                if (oldTipofactOfFacturaListFactura != null) {
                    oldTipofactOfFacturaListFactura.getFacturaList().remove(facturaListFactura);
                    oldTipofactOfFacturaListFactura = em.merge(oldTipofactOfFacturaListFactura);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipofactura tipofactura) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipofactura persistentTipofactura = em.find(Tipofactura.class, tipofactura.getIdTipoFactura());
            List<Factura> facturaListOld = persistentTipofactura.getFacturaList();
            List<Factura> facturaListNew = tipofactura.getFacturaList();
            List<Factura> attachedFacturaListNew = new ArrayList<Factura>();
            for (Factura facturaListNewFacturaToAttach : facturaListNew) {
                facturaListNewFacturaToAttach = em.getReference(facturaListNewFacturaToAttach.getClass(), facturaListNewFacturaToAttach.getId());
                attachedFacturaListNew.add(facturaListNewFacturaToAttach);
            }
            facturaListNew = attachedFacturaListNew;
            tipofactura.setFacturaList(facturaListNew);
            tipofactura = em.merge(tipofactura);
            for (Factura facturaListOldFactura : facturaListOld) {
                if (!facturaListNew.contains(facturaListOldFactura)) {
                    facturaListOldFactura.setTipofact(null);
                    facturaListOldFactura = em.merge(facturaListOldFactura);
                }
            }
            for (Factura facturaListNewFactura : facturaListNew) {
                if (!facturaListOld.contains(facturaListNewFactura)) {
                    Tipofactura oldTipofactOfFacturaListNewFactura = facturaListNewFactura.getTipofact();
                    facturaListNewFactura.setTipofact(tipofactura);
                    facturaListNewFactura = em.merge(facturaListNewFactura);
                    if (oldTipofactOfFacturaListNewFactura != null && !oldTipofactOfFacturaListNewFactura.equals(tipofactura)) {
                        oldTipofactOfFacturaListNewFactura.getFacturaList().remove(facturaListNewFactura);
                        oldTipofactOfFacturaListNewFactura = em.merge(oldTipofactOfFacturaListNewFactura);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipofactura.getIdTipoFactura();
                if (findTipofactura(id) == null) {
                    throw new NonexistentEntityException("The tipofactura with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipofactura tipofactura;
            try {
                tipofactura = em.getReference(Tipofactura.class, id);
                tipofactura.getIdTipoFactura();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipofactura with id " + id + " no longer exists.", enfe);
            }
            List<Factura> facturaList = tipofactura.getFacturaList();
            for (Factura facturaListFactura : facturaList) {
                facturaListFactura.setTipofact(null);
                facturaListFactura = em.merge(facturaListFactura);
            }
            em.remove(tipofactura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipofactura> findTipofacturaEntities() {
        return findTipofacturaEntities(true, -1, -1);
    }

    public List<Tipofactura> findTipofacturaEntities(int maxResults, int firstResult) {
        return findTipofacturaEntities(false, maxResults, firstResult);
    }

    private List<Tipofactura> findTipofacturaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipofactura.class));
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

    public Tipofactura findTipofactura(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipofactura.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipofacturaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipofactura> rt = cq.from(Tipofactura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
