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
import java.util.Collection;
import java.util.Date;

/**
 * @author michal
 */
@Entity
@Table(name = "institutions")

@Data
public class Institutions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 130)
    @Column(name = "institution_name")
    private String institutionName;
    @Size(max = 130)
    @Column(name = "institution_phone")
    private String institutionPhone;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 130)
    @Column(name = "institution_email")
    private String institutionEmail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "miasto")
    private String miasto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "rss")
    private String rss;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "www")
    private String www;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "snapshot")
    private String snapshot;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dodal")
    private int dodal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "modyfikowal")
    private int modyfikowal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_utworzenia")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataUtworzenia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_modyfikacji")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataModyfikacji;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zablokowana")
    private int zablokowana;
    @Size(max = 200)
    @Column(name = "zrodlo")
    private String zrodlo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rss_parse_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rssParseDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dane_osobowe")
    private short daneOsobowe;
    @ManyToMany(mappedBy = "institutionsCollection")
    private Collection<Hosts> hostsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instId")
    private Collection<EmailsBounced> emailsBouncedCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instytucjaId")
    private Collection<InstitutionsGroups> institutionsGroupsCollection;
    @OneToMany(mappedBy = "nadawcaInstytucja")
    private Collection<Messages> messagesCollection;

}
