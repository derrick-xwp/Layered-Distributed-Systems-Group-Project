/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBeans;

import Entity.Jobs;

import Entity.Userinfo;
import LocalSessionBeans.JobsFacade;
import LocalSessionBeans.UserinfoFacade;
import LocalSessionBeans.UsersFacade;
import java.sql.*;
import java.util.*;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;

/**
 *
 * @author xingwenp
 */
@Named(value = "infoManager")
@ManagedBean
@SessionScoped
public class InfoManager implements Serializable {

    @Inject
    usersBean usersManagedBean;
    @EJB
    UserinfoFacade infoFacade;

    @Inject
    InfoBean infoBean;
    @EJB
          

    JobsFacade jobFacde;
    
    @Resource(mappedName = "java:app/myLogFiles/dest")
    private javax.jms.Queue java_appMyLogFilesDest;

    @Inject
    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext context;
    
    public InfoManager() {
    }

    public void editInfo(Userinfo p) {
        infoBean.setUsername(p.getUsername());
        infoBean.setFreelancerID(p.getFreelancerid());
        infoBean.setSkills(p.getSkills());
        infoBean.setMessage(p.getMessage());
        infoBean.setAccount(p.getAccount());
        sendJMSMessageToDest("Log : " + p.getUsername() + " userinfo is edited");

    }

    public void viewFreelancer(Jobs job) {

        List<Userinfo> info = infoFacade.findByFreelancer(job.getOffers());

        infoBean.setUsername(info.get(0).getUsername());
        infoBean.setFreelancerID(info.get(0).getFreelancerid());
        infoBean.setSkills(info.get(0).getSkills());
        infoBean.setMessage(info.get(0).getMessage());
        sendJMSMessageToDest("Log : " + usersManagedBean.getUsername() + " is viewing all freelancers");

    }


    public ArrayList<Userinfo> infoFromDataBase() {
        ArrayList<Userinfo> databaseInfo = new ArrayList<>();

        try {
            Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app");
            Statement statement = connect.createStatement();
            String my_instructions = "SELECT * FROM APP.UserInfo";
            ResultSet res = statement.executeQuery(my_instructions);
            while (res.next()) {
                Userinfo info = new Userinfo(res.getString(1), res.getInt(2), res.getString(3), res.getString(4), res.getInt(5));
                databaseInfo.add(info);
            }
            connect.close();
        } catch (SQLException sql) {
           System.out.println("Message: " + sql.getMessage());
        }
        return databaseInfo;
    }

    public void complete(Jobs job) {

        String user = job.getOffers();
        int amountToPay = job.getPayment();
        for (Userinfo info : infoFromDataBase()) {
            if (info.getUsername().equalsIgnoreCase(user)) {
                info.setAccount(info.getAccount() + amountToPay);
                
                infoFacade.edit(info);
            }
        }
       
    job.setStatus("completed");
    sendJMSMessageToDest("Log : " + usersManagedBean.getUsername() + " set job :"+job.getJobid()+"to be completed");
    jobFacde.edit(job);

    }

    public ArrayList<Userinfo> checkInfo() {
        String info = infoBean.getUsername();

        ArrayList<Userinfo> check = new ArrayList<>();
        for (Userinfo i : infoFromDataBase()) {
            if (i.getUsername().equalsIgnoreCase(info)) {
                check.add(i);
            }
        }
        return check;
    }

    public ArrayList<Userinfo> myInfo() {
        ArrayList<Userinfo> check = new ArrayList<>();
        for (Userinfo i : infoFromDataBase()) {
            if (i.getUsername().equalsIgnoreCase(usersManagedBean.getUsername())) {
                check.add(i);
            }
        }
        sendJMSMessageToDest("Log : " + usersManagedBean.getUsername() + " is watching his own info");
        return check;
    }

    public void saveInfo() {
        Userinfo info = new Userinfo();
        info.setUsername(infoBean.getUsername());
        info.setFreelancerid(infoBean.getFreelancerID());
        info.setSkills(infoBean.getSkills());
        info.setMessage(infoBean.getMessage());
        info.setAccount(infoBean.getAccount());
        infoFacade.edit(info);
        ArrayList<Userinfo> databaseInfo = new ArrayList<>();

        try {
            Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app");
            Statement statement = connect.createStatement();
            String sql = "update userinfo set message='"+info.getMessage()+"'"+ "skills='"+info.getSkills()+"' where username='"+info.getUsername()+"'";
            statement.executeUpdate(sql);
            connect.close();
        } catch (SQLException sql) {
            System.out.println("Message: " + sql.getMessage());
        }

    }

    public List<Userinfo> getAllInfo() {
        return infoFacade.findAll();
    }

    public int count() {
        return infoFacade.count();
    }

    public String deleteInfo(Userinfo p) {
        infoFacade.remove(p);
        sendJMSMessageToDest("Log : " + usersManagedBean.getUsername() + " is deleted user:"+p.getUsername());
        return null;
    }

    public String addInfo() {
        Userinfo info = new Userinfo();
        info.setSkills(infoBean.getSkills());
        info.setMessage(infoBean.getMessage());
        info.setAccount(infoBean.getAccount());
        info.setUsername(infoBean.getUsername());
        info.setFreelancerid(infoBean.getFreelancerID());
        infoFacade.create(info);
        sendJMSMessageToDest("Log : " + usersManagedBean.getUsername() + " is creatingd user:"+infoBean.getUsername());
        return "provider/providerMainPage.xhtml";
    }
    private void sendJMSMessageToDest(String messageData) {
        context.createProducer().send(java_appMyLogFilesDest, messageData);
    }
}
