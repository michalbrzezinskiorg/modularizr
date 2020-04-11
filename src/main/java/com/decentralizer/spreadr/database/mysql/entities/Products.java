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
@Table(name = "products")

@Data
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Lob
    @Size(max = 65535)
    @Column(name = "tags")
    private String tags;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "link")
    private String link;
    @Column(name = "dostepna_ilosc")
    private Integer dostepnaIlosc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "jednostka")
    private String jednostka;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cena")
    private double cena;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "waluta")
    private String waluta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vat")
    private short vat;
    @Basic(optional = false)
    @NotNull
    @Column(name = "netto")
    private double netto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "marza")
    private short marza;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "transport_eu")
    private Float transportEu;
    @Column(name = "transport_usa")
    private Float transportUsa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nazwa")
    private String nazwa;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "opis")
    private String opis;
    @Size(max = 150)
    @Column(name = "featured_image")
    private String featuredImage;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hide")
    private boolean hide;
    @Basic(optional = false)
    @NotNull
    @Column(name = "deleted")
    private boolean deleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_utworznia")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataUtworznia;
    @Column(name = "data_edycji")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEdycji;
    @JoinColumn(name = "producent", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users producent;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private Collection<Orders> ordersCollection;

}
