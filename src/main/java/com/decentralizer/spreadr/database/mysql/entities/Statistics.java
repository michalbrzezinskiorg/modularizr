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
@Table(name = "statistics")

@Data
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "dzien")
    @Temporal(TemporalType.DATE)
    private Date dzien;
    @Size(max = 100)
    @Column(name = "ciacho")
    private String ciacho;
    @Column(name = "odslon")
    private Integer odslon;
    @Size(max = 150)
    @Column(name = "url")
    private String url;
    @Column(name = "projekt")
    private Integer projekt;
    @Column(name = "article")
    private Integer article;
    @Column(name = "author")
    private Integer author;
    @Column(name = "news")
    private Integer news;
    @Size(max = 150)
    @Column(name = "zrodlo")
    private String zrodlo;
    @Size(max = 256)
    @Column(name = "mail")
    private String mail;
    @Column(name = "mailing_id")
    private Integer mailingId;
    @Column(name = "mail_od")
    private Integer mailOd;
    @Column(name = "mail_do")
    private Integer mailDo;
    @Column(name = "uzytkownik_sesja")
    private Integer uzytkownikSesja;
    @Column(name = "uzytkownik_ciasteczka")
    private Integer uzytkownikCiasteczka;
    @Column(name = "sesja_instytucja")
    private Integer sesjaInstytucja;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "ip")
    private String ip;
    @Size(max = 50)
    @Column(name = "country")
    private String country;
    @Size(max = 50)
    @Column(name = "city")
    private String city;
    @Size(max = 200)
    @Column(name = "org")
    private String org;

}
