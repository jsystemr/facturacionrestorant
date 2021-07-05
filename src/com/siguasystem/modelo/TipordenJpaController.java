/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siguasystem.modelo;

import com.siguasystem.modelos.exceptions.NonexistentEntityException;
import com.siguasystem.modelos.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

/**
 *
 * @author jramos
 */

public class TipordenJpaController implements Serializable {

    public TipordenJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tiporden tiporden) throws PreexistingEntityException, Exception {
        if (tiporden.getFacturaList() == null) {
            tiporden.setFacturaList(new ArrayList<Factura>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Factura> attachedFacturaList = new ArrayList<Factura>();
            for (Factura facturaListFacturaToAttach : tiporden.getFacturaList()) {
                facturaListFacturaToAttach = em.getReference(facturaListFacturaToAttach.getClass(), facturaListFacturaToAttach.getFacturaPK());
                attachedFacturaList.add(facturaListFacturaToAttach);
            }
            tiporden.setFacturaList(attachedFacturaList);
            em.persist(tiporden);
            for (Factura facturaListFactura : tiporden.getFacturaList()) {
                Tiporden oldIdordenOfFacturaListFactura = facturaListFactura.getIdorden();
                facturaListFactura.setIdorden(tiporden);
                facturaListFactura = em.merge(facturaListFactura);
                if (oldIdordenOfFacturaListFactura != null) {
                    oldIdordenOfFacturaListFactura.getFacturaList().remove(facturaListFactura);
                    oldIdordenOfFacturaListFactura = em.merge(oldIdordenOfFacturaListFactura);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTiporden(tiporden.getIdtiporden()) != null) {
                throw new PreexistingEntityException("Tiporden " + tiporden + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tiporden tiporden) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tiporden persistentTiporden = em.find(Tiporden.class, tiporden.getIdtiporden());
            List<Factura> facturaListOld = persistentTiporden.getFacturaList();
            List<Factura> facturaListNew = tiporden.getFacturaList();
            List<Factura> attachedFacturaListNew = new ArrayList<Factura>();
            for (Factura facturaListNewFacturaToAttach : facturaListNew) {
                facturaListNewFacturaToAttach = em.getReference(facturaListNewFacturaToAttach.getClass(), facturaListNewFacturaToAttach.getFacturaPK());
                attachedFacturaListNew.add(facturaListNewFacturaToAttach);
            }
            facturaListNew = attachedFacturaListNew;
            tiporden.setFacturaList(facturaListNew);
            tiporden = em.merge(tiporden);
            for (Factura facturaListOldFactura : facturaListOld) {
                if (!facturaListNew.contains(facturaListOldFactura)) {
                    facturaListOldFactura.setIdorden(null);
                    facturaListOldFactura = em.merge(facturaListOldFactura);
                }
            }
            for (Factura facturaListNewFactura : facturaListNew) {
                if (!facturaListOld.contains(facturaListNewFactura)) {
                    Tiporden oldIdordenOfFacturaListNewFactura = facturaListNewFactura.getIdorden();
                    facturaListNewFactura.setIdorden(tiporden);
                    facturaListNewFactura = em.merge(facturaListNewFactura);
                    if (oldIdordenOfFacturaListNewFactura != null && !oldIdordenOfFacturaListNewFactura.equals(tiporden)) {
                        oldIdordenOfFacturaListNewFactura.getFacturaList().remove(facturaListNewFactura);
                        oldIdordenOfFacturaListNewFactura = em.merge(oldIdordenOfFacturaListNewFactura);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tiporden.getIdtiporden();
                if (findTiporden(id) == null) {
                    throw new NonexistentEntityException("The tiporden with id " + id + " no longer exists.");
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
            Tiporden tiporden;
            try {
                tiporden = em.getReference(Tiporden.class, id);
                tiporden.getIdtiporden();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tiporden with id " + id + " no longer exists.", enfe);
            }
            List<Factura> facturaList = tiporden.getFacturaList();
            for (Factura facturaListFactura : facturaList) {
                facturaListFactura.setIdorden(null);
                facturaListFactura = em.merge(facturaListFactura);
            }
            em.remove(tiporden);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tiporden> findTipordenEntities() {
        return findTipordenEntities(true, -1, -1);
    }

    public List<Tiporden> findTipordenEntities(int maxResults, int firstResult) {
        return findTipordenEntities(false, maxResults, firstResult);
    }

    private List<Tiporden> findTipordenEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tiporden.class));
            Query q = em.createQuery(cq);
            //q.setHint(QueryHints.REFRESH, HintValues.TRUE);//Actualiza
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Tiporden findTiporden(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tiporden.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipordenCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tiporden> rt = cq.from(Tiporden.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Tiporden> ListaTipoOrd() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNativeQuery("select * from tiporden ", Tiporden.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
}
