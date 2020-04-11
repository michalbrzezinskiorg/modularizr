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
@Table(name = "mailing_groups")

@Data
public class EmailsMailingGroups {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "link")
    private String link;
    @Column(name = "confirmed")
    private Boolean confirmed;
    @Basic(optional = false)
    @NotNull
    @Column(name = "group_id")
    private int groupId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Basic(optional = false)
    @NotNull
    @Column(name = "finished")
    @Temporal(TemporalType.TIMESTAMP)
    private Date finished;
    @Column(name = "ile_wyslano")
    private Integer ileWyslano;
    @Column(name = "ile_do_wyslania")
    private Integer ileDoWyslania;
    @Basic(optional = false)
    @NotNull
    @Column(name = "done")
    private short done;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ukryj")
    private short ukryj;
    @Basic(optional = false)
    @NotNull
    @Column(name = "paused")
    private short paused;
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users authorId;
    @JoinColumn(name = "email_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Emails emailId;

}
