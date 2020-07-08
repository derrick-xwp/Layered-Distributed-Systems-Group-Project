/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBeans;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.inject.Named;

/**
 *
 * @author xingwenp
 */
@Named(value = "jobsManagedBean")
@ManagedBean
@SessionScoped
public class jobsManagedBean implements Serializable {

    private String job_id;
    private String title;
    private String status;
    private String description;
    private int payment;
    private String searchJobId;
    private String offers;
    private String provider;
    private String searchKeyword;
    private String keywords;
    private int max;

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
    
    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public void setSearchJobId(String searchJobId) {
        this.searchJobId = searchJobId;
    }

    public String getProvider() {
        return provider;
    }

    public String getSearchJobId() {
        return searchJobId;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOffers() {
        return offers;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPayment() {
        return payment;
    }

    public void setOffers(String offers) {
        this.offers = offers;
    }

    public jobsManagedBean() {
    }

}
