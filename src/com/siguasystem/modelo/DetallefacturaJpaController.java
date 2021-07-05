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
public class DetallefacturaJpaController implements Serializable {

    public DetallefacturaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void saveDFactura(Detallefactura df) {
        System.out.println("Guardando Detalle Factura...");
        // manager es el EntityManager obtenido anteriorment
         EntityManager em = getEntityManager();
         em.setFlushMode(FlushModeType.COMMIT);
        try {
            em.getTransaction().begin();
            em.persist(df);
            em.getTransaction().commit();
            System.out.println("Detalle Factura Guardada...");
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }
    
    public void create(Detallefactura detallefactura) throws PreexistingEntityException, Exception {
        if (detallefactura.getDetallefacturaPK() == null) {
            detallefactura.setDetallefacturaPK(new DetallefacturaPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(detallefactura);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDetallefactura(detallefactura.getDetallefacturaPK()) != null) {
                throw new PreexistingEntityException("Detallefactura " + detallefactura + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Detallefactura detallefactura) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            detallefactura = em.merge(detallefactura);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                DetallefacturaPK id = detallefactura.getDetallefacturaPK();
                if (findDetallefactura(id) == null) {
                    throw new NonexistentEntityException("The detallefactura with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(DetallefacturaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Detallefactura detallefactura;
            try {
                detallefactura = em.getReference(Detallefactura.class, id);
                detallefactura.getDetallefacturaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detallefactura with id " + id + " no longer exists.", enfe);
            }
            em.remove(detallefactura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Detallefactura> findDetallefacturaEntities() {
        return findDetallefacturaEntities(true, -1, -1);
    }

    public List<Detallefactura> findDetallefacturaEntities(int maxResults, int firstResult) {
        return findDetallefacturaEntities(false, maxResults, firstResult);
    }

    private List<Detallefactura> findDetallefacturaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Detallefactura.class));
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

    public Detallefactura findDetallefactura(DetallefacturaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Detallefactura.class, id);
        } finally {
            em.close();
        }
    }

    public List<Detallefactura> findDetallefacturaEntities2(DetallefacturaPK dt2) {
        EntityManager em = getEntityManager();
        try {
            Query sql = em.createNativeQuery("select * from detallefactura where idfactura=" + dt2.getIdFactura(), Detallefactura.class);
            return sql.getResultList();
        } finally {
            em.close();
        }
    }
    
    
    
    public Detallefactura findDetallefactura2(DetallefacturaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Detallefactura.class, id);
        } finally {
            em.close();
        }
    }

    public List<Detallefactura> findDetallefacturaFechas(String f1,String f2) {
        EntityManager em = getEntityManager();
        try {
            Query sql = em.createNativeQuery("select * from detallefactura where fecha between ?1 and ?2 order by idfactura", Detallefactura.class)
                    .setParameter(1, f1)
                    .setParameter(2, f2);
            return sql.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Detallefactura> findDetallefacturaFechasPre(String f1,String f2) {
        EntityManager em = getEntityManager();
        try {
            Query sql = em.createNativeQuery("select * from detallefactura where fecha between ?1 and ?2 and precio>0 order by idfactura", Detallefactura.class)
                    .setParameter(1, f1)
                    .setParameter(2, f2);
            return sql.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Detallefactura> findDetallefacturaFechasPreNlineas(String f1,String f2) {
        EntityManager em = getEntityManager();
        try {
            Query sql = em.createNativeQuery("SELECT nrolinea,d.nomproducto,d.precio,d.cantidad,d.comentarios,d.idfactura,d.fecha,d.producto,d.stadodet,d.descuento,d.isv,f.cliente,c.rtncli FROM detallefactura d inner join factura f on\n" +
"d.idfactura=f.idfactura inner join cliente c on c.idcliente=f.cliente "
                    + "where d.fecha between ?1 and ?2 and precio>0 and nrolinea>2 and LENGTH(c.rtncli)=0 or c.rtncli is null order by d.idfactura", Detallefactura.class)
                    .setParameter(1, f1)
                    .setParameter(2, f2);
            return sql.getResultList();
        } finally {
            em.close();
        }
    }

    public int getDetallefacturaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Detallefactura> rt = cq.from(Detallefactura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public int getDetestadoCount(Integer id) {
        EntityManager em = null;
        int res = 0;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Query sqlupd = em.createNativeQuery("select count(*) from detallefactura where idfactura=? and stadodet=0");
            sqlupd.setParameter(1, id);
            res = Integer.parseInt(sqlupd.getSingleResult().toString());
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println("eRROR->" + ex.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return res;
    }

    public int actualizarEstadodet(Integer id) {
        EntityManager em = null;
        int res = 0;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Query sqlupd = em.createNativeQuery("update detallefactura set stadodet=1  where idfactura=?");
            sqlupd.setParameter(1, id);
            res = sqlupd.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println("eRROR->" + ex.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return res;
    }
    
     public int delDetFacturas(String f1,String f2,Integer nlineas) {
        EntityManager em = null;
        int res = 0;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ///validar que la factura no tiene rtn.
            //si la factura tiene rtn, entonces no ejecutar esta linea, falta filtrar por factura
            Query sqlupd = em.createNativeQuery("delete from detallefactura where fecha between ? and ? and precio>0 and nrolinea>?")
            .setParameter(1, f1)
                    .setParameter(2, f2)
                    .setParameter(3, nlineas);
            res = sqlupd.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println("eRROR->" + ex.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return res;
    }
     
      public int updTotFacturas(String f1,String f2) {
        EntityManager em = null;
        int res = 0;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            //Version 1
            //Query sqlupd = em.createNativeQuery("update factura f set f.totalfac=(SELECT sum((d.precio+d.isv)*d.cantidad) as total FROM detallefactura d where d.idfactura=f.idfactura) where f.fecha between ? and ?")
            //Version2
            Query sqlupd = em.createNativeQuery("update factura f set f.totalfac=(SELECT sum(d.precio*d.cantidad) as total FROM detallefactura d where d.idfactura=f.idfactura),f.efectivo=f.totalfac where f.fecha between ? and ?")
            .setParameter(1, f1)
            .setParameter(2, f2);
            res = sqlupd.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println("eRROR->" + ex.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return res;
    }

}
