/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LocalSessionBeans;


import Entity.Userinfo;
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
public class UserinfoFacade extends AbstractFacade<Userinfo> {

    @PersistenceContext(name = "jobmarket")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserinfoFacade() {
        super(Userinfo.class);
    }

    public void persist(Object object) {
        em.persist(object);
    }

        public List<Userinfo> findByFreelancer(String username) {
   
     Query query = em.createNamedQuery("Userinfo.findByFreelancer");

        query.setParameter("username", username);
  
        return  query.getResultList();
    }
}
