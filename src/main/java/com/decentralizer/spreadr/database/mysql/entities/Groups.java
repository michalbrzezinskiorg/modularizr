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
import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;

/**
 * @author michal
 */
@Entity
@Table(name = "groups")

@Data
public class Groups {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "grupa_name")
    private String grupaName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dodal")
    private int dodal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "groupId")
    private Collection<InstitutionsGroups> institutionsGroupsCollection;

    public Groups() {
    }

    public Groups(Integer id) {
        this.id = id;
    }

    public Groups(Integer id, String grupaName, int dodal) {
        this.id = id;
        this.grupaName = grupaName;
        this.dodal = dodal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGrupaName() {
        return grupaName;
    }

    public void setGrupaName(String grupaName) {
        this.grupaName = grupaName;
    }

    public int getDodal() {
        return dodal;
    }

    public void setDodal(int dodal) {
        this.dodal = dodal;
    }

    @XmlTransient
    public Collection<InstitutionsGroups> getInstitutionsGroupsCollection() {
        return institutionsGroupsCollection;
    }

    public void setInstitutionsGroupsCollection(Collection<InstitutionsGroups> institutionsGroupsCollection) {
        this.institutionsGroupsCollection = institutionsGroupsCollection;
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
        if (!(object instanceof Groups)) {
            return false;
        }
        Groups other = (Groups) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "newpackage.Groups[ id=" + id + " ]";
    }

}
