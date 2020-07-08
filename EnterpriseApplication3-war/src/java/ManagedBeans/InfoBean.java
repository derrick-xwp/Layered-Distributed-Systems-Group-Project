/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBeans;

import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author xingwep
 */
@Named(value = "infoBean")
@ManagedBean
@SessionScoped
public class InfoBean implements Serializable {

    private String skills;
    private String message;
    private int account;
    private String username;
    private int freelancerID;




    public String getMessage() {
        return message;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getAccount() {
        return account;
    }

    public String getUsername() {
        return username;
    }

    public String getSkills() {
        return skills;
    }

    public int getFreelancerID() {
        return freelancerID;
    }

    public void setFreelancerID(int freelance_id) {
        this.freelancerID = freelance_id;
    }

    public InfoBean() {
    }

}
