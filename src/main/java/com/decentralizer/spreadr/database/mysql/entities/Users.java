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
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

/**
 * @author michal
 */
@Entity
@Table(name = "users")

@Data
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "imie")
    private String imie;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stopien_uprawnien")
    private int stopienUprawnien;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "login")
    private String login;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "haslo")
    private String haslo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "potwierdzony")
    private short potwierdzony;
    @Size(max = 150)
    @Column(name = "link")
    private String link;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rejestracja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rejestracja;
    @Column(name = "ostatnie_logowanie")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ostatnieLogowanie;
    @Column(name = "ostatnia_zmiana")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ostatniaZmiana;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "url")
    private String url;
    @Size(max = 150)
    @Column(name = "rss")
    private String rss;
    @Size(max = 150)
    @Column(name = "rss_projects")
    private String rssProjects;
    @Size(max = 150)
    @Column(name = "rss_articles")
    private String rssArticles;
    @Size(max = 150)
    @Column(name = "rss_products")
    private String rssProducts;
    @Column(name = "fb_id")
    private BigInteger fbId;
    @Size(max = 250)
    @Column(name = "fb_picture")
    private String fbPicture;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rank")
    private int rank;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<UsersProfiles> usersProfilesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producent")
    private Collection<Products> productsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<Companies> companiesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dodalRelacje")
    private Collection<InstitutionsGroups> institutionsGroupsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "authorId")
    private Collection<EmailsMailingGroups> emailsMailingGroupsCollection;
    @OneToMany(mappedBy = "nadawca")
    private Collection<Messages> messagesCollection;
    @OneToMany(mappedBy = "odbiorca")
    private Collection<Messages> messagesCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "buyer")
    private Collection<Orders> ordersCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "from")
    private Collection<Emails> emails;

}
