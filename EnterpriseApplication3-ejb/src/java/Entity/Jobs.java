/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author xingwenp
 */
@Entity
@Table(name = "JOBS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jobs.findAll", query = "SELECT j FROM Jobs j")
    , @NamedQuery(name = "Jobs.findByOpenjobs", query = "SELECT j FROM Jobs j WHERE j.status ='open'")
    , @NamedQuery(name = "Jobs.findByTitle", query = "SELECT j FROM Jobs j WHERE j.title = :title")
    , @NamedQuery(name = "Jobs.findByKeywords", query = "SELECT j FROM Jobs j WHERE j.keywords = :keywords")
    , @NamedQuery(name = "Jobs.findByDescription", query = "SELECT j FROM Jobs j WHERE j.description = :description")
    , @NamedQuery(name = "Jobs.findByPayment", query = "SELECT j FROM Jobs j WHERE j.payment = :payment")
    , @NamedQuery(name = "Jobs.findByStatus", query = "SELECT j FROM Jobs j WHERE j.status = :status")
    , @NamedQuery(name = "Jobs.findByOffers", query = "SELECT j FROM Jobs j WHERE j.offers = :offers")
    , @NamedQuery(name = "Jobs.findByMyjobsWithOffers",query = "SELECT j FROM Jobs j WHERE j.provider=:provider and j.offers != '0'")
    , @NamedQuery(name = "Jobs.findByProvider", query = "SELECT j FROM Jobs j WHERE j.provider = :provider")})
public class Jobs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 50)
    @Column(name = "PROVIDER")
    private String provider;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "JOBID")
    private String jobid;
    @Size(max = 50)
    @Column(name = "TITLE")
    private String title;
    @Size(max = 50)
    @Column(name = "KEYWORDS")
    private String keywords;
    @Size(max = 500)
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "PAYMENT")
    private Integer payment;
    @Size(max = 50)
    @Column(name = "STATUS")
    private String status;
    @Size(max = 50)
    @Column(name = "OFFERS")
    private String offers;

    public Jobs() {
    }
        
    public Jobs(String jobId, String title, String keyword, String description, int payment, String status, String offers, String provider) {

        this.description = description;
        this.keywords = keyword;
        this.jobid = jobId;
        this.title = title;
        this.payment = payment;
        this.status = status;
        this.offers = offers;
        this.provider = provider;
    }

    public Jobs(String jobid) {
        this.jobid = jobid;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
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

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPayment() {
        return payment;
    }

    public void setPayment(Integer payment) {
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

    public void setOffers(String offers) {
        this.offers = offers;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jobid != null ? jobid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Jobs)) {
            return false;
        }
        Jobs other = (Jobs) object;
        if ((this.jobid == null && other.jobid != null) || (this.jobid != null && !this.jobid.equals(other.jobid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Jobs[ jobid=" + jobid + " ]";
    }
    
}
