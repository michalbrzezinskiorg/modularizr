/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.decentralizer.spreadr.database.mysql.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;

/**
 * @author michal
 */
@Entity
@Table(name = "hosts")

@Data
public class Hosts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 150)
    @Column(name = "name")
    private String name;
    @Size(max = 150)
    @Column(name = "www")
    private String www;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @JoinTable(name = "host_inst_rel", joinColumns = {
            @JoinColumn(name = "host_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "institution_id", referencedColumnName = "id")})
    @ManyToMany
    private Collection<Institutions> institutionsCollection;

    public Hosts() {
    }

    public Hosts(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWww() {
        return www;
    }

    public void setWww(String www) {
        this.www = www;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public Collection<Institutions> getInstitutionsCollection() {
        return institutionsCollection;
    }

    public void setInstitutionsCollection(Collection<Institutions> institutionsCollection) {
        this.institutionsCollection = institutionsCollection;
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
        if (!(object instanceof Hosts)) {
            return false;
        }
        Hosts other = (Hosts) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "newpackage.Hosts[ id=" + id + " ]";
    }

}
