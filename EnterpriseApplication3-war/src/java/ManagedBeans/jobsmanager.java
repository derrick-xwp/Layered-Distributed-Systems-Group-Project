/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBeans;

import Entity.Jobs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.annotation.Resource;
import LocalSessionBeans.JobsFacade;

import LocalSessionBeans.UsersFacade;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.annotation.ManagedBean;

/**
 *
 * @author xingwep
 */
@Named(value = "jobsManager")
@ManagedBean
@RequestScoped
public class jobsmanager implements Serializable {

    @Resource(mappedName = "java:app/myLogFiles/dest")
    private javax.jms.Queue java_appMyLogFilesDest;

    @Inject
    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext context;

    @EJB
    JobsFacade JobsFacade;
    @Inject
    InfoManager ProfilesManager;

    @Inject
    usersBean usersManagedBean;
   

    @Inject
    jobsManagedBean jobsManagedBean;

    @EJB

    UsersFacade UsersFacade;
    JobsFacade jobFacde;

    public jobsmanager() {
    }

    public List<Jobs> getAll() {
        return JobsFacade.findAll();
    }

    public int count() {
        return JobsFacade.count();
    }

    public void delete(Jobs job) {
        
        JobsFacade.remove(job);

    }

    public String add() {
        Jobs newjob = new Jobs();
        newjob.setJobid(jobsManagedBean.getJob_id());
        newjob.setTitle(jobsManagedBean.getTitle());
        newjob.setKeywords(jobsManagedBean.getKeywords());
        newjob.setDescription(jobsManagedBean.getDescription());
        newjob.setPayment(jobsManagedBean.getPayment());
        newjob.setStatus(jobsManagedBean.getStatus());
        newjob.setOffers(null);
        newjob.setProvider(usersManagedBean.getUsername());

        JobsFacade.create(newjob);
        sendJMSMessageToDest("Log : " + usersManagedBean.getUsername() + " added job" + jobsManagedBean.getJob_id());
        return "jobprovider/jobproviderMainpage.xhtml";
    }

    public void save() {
        Jobs job = new Jobs();
        job.setJobid(jobsManagedBean.getJob_id());
        job.setTitle(jobsManagedBean.getTitle());
        job.setKeywords(jobsManagedBean.getKeywords());
        job.setDescription(jobsManagedBean.getDescription());
        job.setPayment(jobsManagedBean.getPayment());
        job.setStatus(jobsManagedBean.getStatus());
        job.setOffers(jobsManagedBean.getOffers());
        job.setProvider(jobsManagedBean.getProvider());

        JobsFacade.edit(job);
        sendJMSMessageToDest("Log : " + usersManagedBean.getUsername() + " changed job" + jobsManagedBean.getJob_id());

    }


    public void completedJob(Jobs job) {
        job.setStatus("completed");
        JobsFacade.edit(job);
        try {
            Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app");
            Statement statement = connect.createStatement();
            String sql = "update jobs set status='completed' where  jobid='" + job.getJobid() + "'";
            int result = statement.executeUpdate(sql);
            connect.close();

        } catch (SQLException sql) {
            System.out.println("Message: " + sql.getMessage());
        }
        
        sendJMSMessageToDest("Log : " + usersManagedBean.getUsername() + " completed job" + job.getJobid());

    }

    public List<Jobs> searchKeyword() {
        String keyword = jobsManagedBean.getSearchKeyword();
        List<Jobs> check = new ArrayList<>();
        List<Jobs> all = getAllJobs();
        for (Jobs job : all) {
            if (job.getKeywords().equalsIgnoreCase(keyword)) {
                check.add(job);
            }
        }
        sendJMSMessageToDest("Log : " + usersManagedBean.getUsername() + " searched job by keywords" + keyword);
        return check;
    }

    public List<Jobs> getAllJobs() {
        ArrayList<Jobs> databaseJobss = new ArrayList<>();

        try {
            Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app");
            Statement statement = connect.createStatement();

            String sql = "SELECT * FROM APP.JOBS";

            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {

                Jobs jobs = new Jobs(result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getInt(5), result.getString(6), result.getString(7), result.getString(8));
                databaseJobss.add(jobs);

            }

            connect.close();

        } catch (SQLException sql) {

        }

        return JobsFacade.findAll();
    }

    public List<Jobs> searchJobId() {
        String jobId = jobsManagedBean.getSearchJobId();
        List<Jobs> check = new ArrayList<>();
        List<Jobs> all = getAllJobs();
        for (Jobs j : all) {
            if (j.getJobid().equalsIgnoreCase(jobId)) {
                check.add(j);
            }
        }
         sendJMSMessageToDest("Log : " + usersManagedBean.getUsername() + " searched job by jobid" + jobId);
        return check;
    }

    public List<Jobs> myProvidedJobs() {
        String keyword = usersManagedBean.getUsername();

        ArrayList<Jobs> databaseJobss = new ArrayList<>();
        int max=0;
        
        try {
            Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app");
            Statement statement = connect.createStatement();

            String sql = "SELECT * FROM APP.JOBS ";

            ResultSet result = statement.executeQuery(sql);
            
            while (result.next()) {

                Jobs jobs = new Jobs(result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getInt(5), result.getString(6), result.getString(7), result.getString(8));
                databaseJobss.add(jobs);
                max=max++;
                jobsManagedBean.setMax(max+1);
            }

            connect.close();

        } catch (SQLException sql) {
           System.out.println("Message: " + sql.getMessage());

        }
        
        
        try {
            Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app");
            Statement statement = connect.createStatement();

            String sql = "SELECT * FROM APP.JOBS where provider='" + keyword + "'";

            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {

                Jobs jobs = new Jobs(result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getInt(5), result.getString(6), result.getString(7), result.getString(8));
                databaseJobss.add(jobs);
             
            }

            connect.close();

        } catch (SQLException sql) {
          System.out.println("Message: " + sql.getMessage());

        }
       sendJMSMessageToDest("Log : " + usersManagedBean.getUsername() + " see all his provided jobs" );
        return JobsFacade.findByProvider(usersManagedBean.getUsername());
    }

    public List<Jobs> openJobs() {
        List<Jobs> databaseJobss = new ArrayList<>();

        try {
            Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app");
            Statement statement = connect.createStatement();

            String sql = "SELECT * FROM APP.JOBS where APP.JOBS.status='open' and APP.JOBS.pffers !='"+usersManagedBean.getUsername()+"'";

            ResultSet res = statement.executeQuery(sql);

            while (res.next()) {
                Jobs jobs = new Jobs(res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getInt(5), res.getString(6), res.getString(7), res.getString(8));
                databaseJobss.add(jobs);
            }
            connect.close();

        } catch (SQLException sql) {
           System.out.println("Message: " + sql.getMessage());

        }

        return JobsFacade.findByOpenjobs();

    }

    public List<Jobs> myJobsWithOffers() {

        return JobsFacade.findByMyjobsWithOffers(usersManagedBean.getUsername());

    }

    public ArrayList<Jobs> dbJobOffers() {
        ArrayList<Jobs> databaseJobss = new ArrayList<>();

        try {
            Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app");
            Statement statement = connect.createStatement();

            String sql = "SELECT * FROM APP.JOBS";

            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {

                Jobs jobs = new Jobs(result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getInt(5), result.getString(6), result.getString(7), result.getString(8));
                databaseJobss.add(jobs);

            }

            connect.close();

        } catch (SQLException sql) {
        System.out.println("Message: " + sql.getMessage());

        }

        return databaseJobss;
    }

    
    public List<Jobs> myJobApplications() {

        List<Jobs> databaseJobss = new ArrayList<>();

        try {
            Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app");
            Statement statement = connect.createStatement();

            String sql = "SELECT * FROM Jobs  WHERE Jobs.offers = '" + usersManagedBean.getUsername() + "'";

            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {

                Jobs jobs = new Jobs(result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getInt(5), result.getString(6), result.getString(7), result.getString(8));
                databaseJobss.add(jobs);
            }

            connect.close();

        } catch (SQLException sql) {
            System.out.println("Message: " + sql.getMessage());

        }

        return JobsFacade.findByOffers(usersManagedBean.getUsername());

    }

    public void underTake(Jobs j) {

        String id = j.getJobid();
        j.setOffers(usersManagedBean.getUsername());
        JobsFacade.edit(j);

     /*   try {
            Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app");
            Statement statement = connect.createStatement();

            String sql1 = "update jobs set offers='" + usersManagedBean.getUsername() + "' where jobid= '" + id + "'";

            statement.executeUpdate(sql1);
            connect.close();
        } catch (SQLException sql) {
            System.out.println("Message: " + sql.getMessage());
        }
*/
     
        sendJMSMessageToDest("Log: " + usersManagedBean.getUsername() + " offered to undertake " + j.getTitle() + " job.");
    }

    public void acceptRequest(Jobs job) {
        job.setStatus("closed");
        JobsFacade.edit(job);

        sendJMSMessageToDest("Log: " + usersManagedBean.getUsername() + " choose to accept a request from " + job.getOffers()+" for job "+job.getTitle());

    }

    public void rejectRequest(Jobs job) {
        job.setStatus("open");
        job.setOffers("0");
        JobsFacade.edit(job);

        sendJMSMessageToDest("Log: " + usersManagedBean.getUsername() + " rejected a request from " + job.getOffers());
    }


    public void myRevoke(Jobs off) {

        List<Jobs> databaseJobss = JobsFacade.findByOffers(usersManagedBean.getUsername());
        try {
            Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app");
            Statement statement = connect.createStatement();
            String sql = "update jobs set offers=null,status='open' where offers='" + usersManagedBean.getUsername() + "' and jobid='" + off.getJobid() + "'";
            int result = statement.executeUpdate(sql);
            connect.close();
        } catch (SQLException sql) {
            System.out.println("Message: " + sql.getMessage());
        }

    }


    private void sendJMSMessageToDest(String messageData) {
        context.createProducer().send(java_appMyLogFilesDest, messageData);
    }

}
