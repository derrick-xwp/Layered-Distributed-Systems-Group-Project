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
@Table(name = "USERINFO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Userinfo.findAll", query = "SELECT u FROM Userinfo u")
    , @NamedQuery(name = "Userinfo.findByUsername", query = "SELECT u FROM Userinfo u WHERE u.username = :username")
    , @NamedQuery(name = "Userinfo.findByFreelancerid", query = "SELECT u FROM Userinfo u WHERE u.freelancerid = :freelancerid")
    , @NamedQuery(name = "Userinfo.findBySkills", query = "SELECT u FROM Userinfo u WHERE u.skills = :skills")
    , @NamedQuery(name = "Userinfo.findByMessage", query = "SELECT u FROM Userinfo u WHERE u.message = :message")  
    , @NamedQuery(name = "Userinfo.findByFreelancer",query = "SELECT u FROM Userinfo u WHERE u.username = :username")
    , @NamedQuery(name = "Userinfo.findByAccount", query = "SELECT u FROM Userinfo u WHERE u.account = :account")})
public class Userinfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 50)
    @Column(name = "USERNAME")
    private String username;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "FREELANCERID")
    private Integer freelancerid;
    @Size(max = 50)
    @Column(name = "SKILLS")
    private String skills;
    @Size(max = 500)
    @Column(name = "MESSAGE")
    private String message;
    @Column(name = "ACCOUNT")
    private Integer account;

    public Userinfo() {
    }
    public Userinfo(String username, int freelanceId, String skills, String message, int account) {
        this.username = username;
        this.freelancerid = freelanceId;
        this.skills = skills;
        this.message = message;
        this.account = account;
    }

    public Userinfo(Integer freelancerid) {
        this.freelancerid = freelancerid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getFreelancerid() {
        return freelancerid;
    }

    public void setFreelancerid(Integer freelancerid) {
        this.freelancerid = freelancerid;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (freelancerid != null ? freelancerid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Userinfo)) {
            return false;
        }
        Userinfo other = (Userinfo) object;
        if ((this.freelancerid == null && other.freelancerid != null) || (this.freelancerid != null && !this.freelancerid.equals(other.freelancerid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Userinfo[ freelancerid=" + freelancerid + " ]";
    }
    
}
