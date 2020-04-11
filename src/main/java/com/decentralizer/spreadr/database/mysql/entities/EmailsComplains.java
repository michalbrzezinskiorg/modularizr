/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.decentralizer.spreadr.database.mysql.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author michal
 */
@Entity
@Table(name = "emails_complains")

@Data
public class EmailsComplains {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "email_id")
    private int emailId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "od")
    private int od;
    @Basic(optional = false)
    @NotNull
    @Column(name = "do")
    private int do1;
    @Size(max = 256)
    @Column(name = "temat")
    private String temat;
    @Lob
    @Size(max = 65535)
    @Column(name = "tresc")
    private String tresc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "z\u0142ozono")
    @Temporal(TemporalType.TIMESTAMP)
    private Date złozono;
    @Basic(optional = false)
    @NotNull
    @Column(name = "modyfikowano")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modyfikowano;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ukryj")
    private boolean ukryj;

    public EmailsComplains() {
    }

    public EmailsComplains(Integer id) {
        this.id = id;
    }

    public EmailsComplains(Integer id, int emailId, int od, int do1, Date złozono, Date modyfikowano, boolean ukryj) {
        this.id = id;
        this.emailId = emailId;
        this.od = od;
        this.do1 = do1;
        this.złozono = złozono;
        this.modyfikowano = modyfikowano;
        this.ukryj = ukryj;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getEmailId() {
        return emailId;
    }

    public void setEmailId(int emailId) {
        this.emailId = emailId;
    }

    public int getOd() {
        return od;
    }

    public void setOd(int od) {
        this.od = od;
    }

    public int getDo1() {
        return do1;
    }

    public void setDo1(int do1) {
        this.do1 = do1;
    }

    public String getTemat() {
        return temat;
    }

    public void setTemat(String temat) {
        this.temat = temat;
    }

    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public Date getZłozono() {
        return złozono;
    }

    public void setZłozono(Date złozono) {
        this.złozono = złozono;
    }

    public Date getModyfikowano() {
        return modyfikowano;
    }

    public void setModyfikowano(Date modyfikowano) {
        this.modyfikowano = modyfikowano;
    }

    public boolean getUkryj() {
        return ukryj;
    }

    public void setUkryj(boolean ukryj) {
        this.ukryj = ukryj;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmailsComplains)) {
            return false;
        }
        EmailsComplains other = (EmailsComplains) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "newpackage.EmailsComplains[ id=" + id + " ]";
    }

}
