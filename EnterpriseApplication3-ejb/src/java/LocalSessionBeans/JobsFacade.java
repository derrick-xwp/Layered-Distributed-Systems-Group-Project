/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LocalSessionBeans;

import Entity.Jobs;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author xingwenp
 */
@Stateless
public class JobsFacade extends AbstractFacade<Jobs> {

   @PersistenceContext(name = "jobmarket")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public JobsFacade() {
        super(Jobs.class);
    }

    public void persist(Object object) {
        em.persist(object);
    }
    
    // REVISIT
    public List<Jobs> getAll() {
        return em.createNamedQuery("Jobs.findAll").getResultList();
    }
    
    public List<Jobs> findByJobId() {
        return em.createNamedQuery("Jobs.findByJobId").getResultList();
    }

    public List<Jobs> findByTitle() {
        return em.createNamedQuery("Jobs.findByTitle").getResultList();
    }  
    
    public List<Jobs> findByKeywords() {
        return em.createNamedQuery("Jobs.findByKeywords").getResultList();
    }  
    
    public List<Jobs> findByDescription() {
        return em.createNamedQuery("Jobs.findByDescription").getResultList();
    }
    
    public List<Jobs> findByPayment() {
        return em.createNamedQuery("Jobs.findByPayment").getResultList();
    }
    
    public List<Jobs> findByStatus() {
        return em.createNamedQuery("Jobs.findByStatus").getResultList();
    }
    

    public List<Jobs> findByOffers(String user) {
        
        Query query = em.createNamedQuery("Jobs.findByOffers");

        query.setParameter("offers", user);
  
        return query.getResultList();
        
    }
        public List<Jobs> findByOpenjobs() {
        return em.createNamedQuery("Jobs.findByOpenjobs").getResultList();
    }
    

    public List<Jobs> findByMyjobsWithOffers(String username) {
   
     Query query = em.createNamedQuery("Jobs.findByMyjobsWithOffers");

        query.setParameter("provider", username);
  
        return  query.getResultList();
    }
        
        
    public List<Jobs> findByProvider(String username) {
   
     Query query = em.createNamedQuery("Jobs.findByProvider");

        query.setParameter("provider", username);
  
        return  query.getResultList();
    }
}
