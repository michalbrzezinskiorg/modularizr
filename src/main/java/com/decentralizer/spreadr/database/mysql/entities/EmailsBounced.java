/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.decentralizer.spreadr.database.mysql.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author michal
 */
@Entity
@Table(name = "emails_bounced")

@Data
public class EmailsBounced {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_blokady")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataBlokady;
    @JoinColumn(name = "inst_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Institutions instId;

    public EmailsBounced() {
    }

    public EmailsBounced(Integer id) {
        this.id = id;
    }

    public EmailsBounced(Integer id, Date dataBlokady) {
        this.id = id;
        this.dataBlokady = dataBlokady;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataBlokady() {
        return dataBlokady;
    }

    public void setDataBlokady(Date dataBlokady) {
        this.dataBlokady = dataBlokady;
    }

    public Institutions getInstId() {
        return instId;
    }

    public void setInstId(Institutions instId) {
        this.instId = instId;
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
        if (!(object instanceof EmailsBounced)) {
            return false;
        }
        EmailsBounced other = (EmailsBounced) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "newpackage.EmailsBounced[ id=" + id + " ]";
    }

}
