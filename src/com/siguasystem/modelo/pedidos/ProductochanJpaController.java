/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siguasystem.modelo.pedidos;

import com.siguasystem.modelo.pedidos.exceptions.NonexistentEntityException;
import com.siguasystem.modelo.pedidos.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author joramos
 */
public class ProductochanJpaController implements Serializable {

    public ProductochanJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Productochan productochan) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(productochan);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProductochan(productochan.getId()) != null) {
                throw new PreexistingEntityException("Productochan " + productochan + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Productochan productochan) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            productochan = em.merge(productochan);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = productochan.getId();
                if (findProductochan(id) == null) {
                    throw new NonexistentEntityException("The productochan with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Productochan productochan;
            try {
                productochan = em.getReference(Productochan.class, id);
                productochan.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productochan with id " + id + " no longer exists.", enfe);
            }
            em.remove(productochan);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Productochan> findProductochanEntities() {
        return findProductochanEntities(true, -1, -1);
    }

    public List<Productochan> findProductochanEntities(int maxResults, int firstResult) {
        return findProductochanEntities(false, maxResults, firstResult);
    }

    private List<Productochan> findProductochanEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Productochan.class));
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

    public Productochan findProductochan(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Productochan.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductochanCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Productochan> rt = cq.from(Productochan.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
