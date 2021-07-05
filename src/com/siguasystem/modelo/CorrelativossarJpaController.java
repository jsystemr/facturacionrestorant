/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siguasystem.modelo;

import com.siguasystem.modelos.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.swing.JOptionPane;

/**
 *
 * @author jramos
 */
public class CorrelativossarJpaController implements Serializable {

    public CorrelativossarJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Correlativossar correlativossar) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(correlativossar);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Correlativossar correlativossar) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            correlativossar = em.merge(correlativossar);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = correlativossar.getId();
                if (findCorrelativossar(id) == null) {
                    throw new NonexistentEntityException("The correlativossar with id " + id + " no longer exists.");
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
            Correlativossar correlativossar;
            try {
                correlativossar = em.getReference(Correlativossar.class, id);
                correlativossar.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The correlativossar with id " + id + " no longer exists.", enfe);
            }
            em.remove(correlativossar);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Correlativossar> findCorrelativossarEntities() {
        return findCorrelativossarEntities(true, -1, -1);
    }

    public List<Correlativossar> findCorrelativossarEntities(int maxResults, int firstResult) {
        return findCorrelativossarEntities(false, maxResults, firstResult);
    }

    private List<Correlativossar> findCorrelativossarEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Correlativossar.class));
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

    public Correlativossar findCorrelativossar(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Correlativossar.class, id);
        } finally {
            em.close();
        }
    }

    public int getCorrelativossarCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Correlativossar> rt = cq.from(Correlativossar.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Correlativossar ultimoCorrela() {
        EntityManager em = getEntityManager();
        Correlativossar cor=new Correlativossar();
        try {
            Query qsql = em.createNativeQuery("select * from correlativossar c where id=(select max(id) from correlativossar) and estado=1", Correlativossar.class);//qsql.setParameter("n", txtnom.getText());
            cor = (Correlativossar) qsql.getResultList().get(0);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No hay ningun correlativo o tiene activo mas de uno, verificar.", "Error correlativos", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error->" + e.getMessage());
        }finally{
        em.close();
        }
        return cor;
    }

}
