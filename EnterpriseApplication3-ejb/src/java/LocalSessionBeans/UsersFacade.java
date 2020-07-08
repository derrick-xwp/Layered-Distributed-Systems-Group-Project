/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LocalSessionBeans;

import Entity.Users;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author xingwenp
 */
@Stateless
public class UsersFacade extends AbstractFacade<Users> {

   @PersistenceContext(name = "jobmarket")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersFacade() {
        super(Users.class);
    }
    
    

    public List<Users> getAll() {
        return em.createNamedQuery("Users.findAll").getResultList();
    }
    
    public List<Users> findByUsername() {
        return em.createNamedQuery("Users.findByUsername").getResultList();
    }

    public List<Users> findByPassword() {
        return em.createNamedQuery("Users.findByPassword").getResultList();
    }  
    
    public List<Users> findByAccess() {
        return em.createNamedQuery("Users.findByAccess").getResultList();
    }  
    
    public List<Users> findByFirstName() {
        return em.createNamedQuery("Users.findByFirstName").getResultList();
    }
    
    public List<Users> findByLastName() {
        return em.createNamedQuery("Users.findByLastName").getResultList();
    }
    
    public List<Users> findByAddress() {
        return em.createNamedQuery("Users.findByAddress").getResultList();
    }
     
     
    
    
    
    
    
}
