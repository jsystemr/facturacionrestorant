/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siguasystem.modelo;

import Reportes.exceptions.NonexistentEntityException;
import com.siguasystem.modelo.Historicaproductos;
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
 * @author jramos
 */
public class HistoricaproductosJpaController implements Serializable {

    public HistoricaproductosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Historicaproductos historicaproductos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(historicaproductos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Historicaproductos historicaproductos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            historicaproductos = em.merge(historicaproductos);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = historicaproductos.getId();
                if (findHistoricaproductos(id) == null) {
                    throw new NonexistentEntityException("The historicaproductos with id " + id + " no longer exists.");
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
            Historicaproductos historicaproductos;
            try {
                historicaproductos = em.getReference(Historicaproductos.class, id);
                historicaproductos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historicaproductos with id " + id + " no longer exists.", enfe);
            }
            em.remove(historicaproductos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Historicaproductos> findHistoricaproductosEntities() {
        return findHistoricaproductosEntities(true, -1, -1);
    }

    public List<Historicaproductos> findHistoricaproductosEntities(int maxResults, int firstResult) {
        return findHistoricaproductosEntities(false, maxResults, firstResult);
    }

    private List<Historicaproductos> findHistoricaproductosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Historicaproductos.class));
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

    public Historicaproductos findHistoricaproductos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Historicaproductos.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistoricaproductosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Historicaproductos> rt = cq.from(Historicaproductos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
