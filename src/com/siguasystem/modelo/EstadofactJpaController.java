/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siguasystem.modelo;

import com.siguasystem.modelo.exceptions.IllegalOrphanException;
import com.siguasystem.modelo.exceptions.NonexistentEntityException;
import com.siguasystem.modelo.exceptions.PreexistingEntityException;
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
public class EstadofactJpaController implements Serializable {

    public EstadofactJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estadofact estadofact) throws PreexistingEntityException, Exception {
        if (estadofact.getFacturaList() == null) {
            estadofact.setFacturaList(new ArrayList<Factura>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Factura> attachedFacturaList = new ArrayList<Factura>();
            for (Factura facturaListFacturaToAttach : estadofact.getFacturaList()) {
                facturaListFacturaToAttach = em.getReference(facturaListFacturaToAttach.getClass(), facturaListFacturaToAttach.getId());
                attachedFacturaList.add(facturaListFacturaToAttach);
            }
            estadofact.setFacturaList(attachedFacturaList);
            em.persist(estadofact);
            for (Factura facturaListFactura : estadofact.getFacturaList()) {
                Estadofact oldEstadofactOfFacturaListFactura = facturaListFactura.getEstadofact();
                facturaListFactura.setEstadofact(estadofact);
                facturaListFactura = em.merge(facturaListFactura);
                if (oldEstadofactOfFacturaListFactura != null) {
                    oldEstadofactOfFacturaListFactura.getFacturaList().remove(facturaListFactura);
                    oldEstadofactOfFacturaListFactura = em.merge(oldEstadofactOfFacturaListFactura);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstadofact(estadofact.getIdestadofact()) != null) {
                throw new PreexistingEntityException("Estadofact " + estadofact + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estadofact estadofact) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estadofact persistentEstadofact = em.find(Estadofact.class, estadofact.getIdestadofact());
            List<Factura> facturaListOld = persistentEstadofact.getFacturaList();
            List<Factura> facturaListNew = estadofact.getFacturaList();
            List<String> illegalOrphanMessages = null;
            for (Factura facturaListOldFactura : facturaListOld) {
                if (!facturaListNew.contains(facturaListOldFactura)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Factura " + facturaListOldFactura + " since its estadofact field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Factura> attachedFacturaListNew = new ArrayList<Factura>();
            for (Factura facturaListNewFacturaToAttach : facturaListNew) {
                facturaListNewFacturaToAttach = em.getReference(facturaListNewFacturaToAttach.getClass(), facturaListNewFacturaToAttach.getId());
                attachedFacturaListNew.add(facturaListNewFacturaToAttach);
            }
            facturaListNew = attachedFacturaListNew;
            estadofact.setFacturaList(facturaListNew);
            estadofact = em.merge(estadofact);
            for (Factura facturaListNewFactura : facturaListNew) {
                if (!facturaListOld.contains(facturaListNewFactura)) {
                    Estadofact oldEstadofactOfFacturaListNewFactura = facturaListNewFactura.getEstadofact();
                    facturaListNewFactura.setEstadofact(estadofact);
                    facturaListNewFactura = em.merge(facturaListNewFactura);
                    if (oldEstadofactOfFacturaListNewFactura != null && !oldEstadofactOfFacturaListNewFactura.equals(estadofact)) {
                        oldEstadofactOfFacturaListNewFactura.getFacturaList().remove(facturaListNewFactura);
                        oldEstadofactOfFacturaListNewFactura = em.merge(oldEstadofactOfFacturaListNewFactura);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estadofact.getIdestadofact();
                if (findEstadofact(id) == null) {
                    throw new NonexistentEntityException("The estadofact with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estadofact estadofact;
            try {
                estadofact = em.getReference(Estadofact.class, id);
                estadofact.getIdestadofact();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadofact with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Factura> facturaListOrphanCheck = estadofact.getFacturaList();
            for (Factura facturaListOrphanCheckFactura : facturaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estadofact (" + estadofact + ") cannot be destroyed since the Factura " + facturaListOrphanCheckFactura + " in its facturaList field has a non-nullable estadofact field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(estadofact);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estadofact> findEstadofactEntities() {
        return findEstadofactEntities(true, -1, -1);
    }

    public List<Estadofact> findEstadofactEntities(int maxResults, int firstResult) {
        return findEstadofactEntities(false, maxResults, firstResult);
    }

    private List<Estadofact> findEstadofactEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estadofact.class));
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

    public Estadofact findEstadofact(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estadofact.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadofactCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estadofact> rt = cq.from(Estadofact.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
