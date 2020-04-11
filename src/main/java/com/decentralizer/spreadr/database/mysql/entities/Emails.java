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
@Table(name = "emails")

@Data
public class Emails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 256)
    @Column(name = "subject")
    private String subject;
    @Lob
    @Size(max = 65535)
    @Column(name = "content")
    private String content;
    @Lob
    @Size(max = 65535)
    @Column(name = "tags")
    private String tags;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Basic(optional = false)
    @NotNull
    @Column(name = "edited")
    @Temporal(TemporalType.TIMESTAMP)
    private Date edited;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "link")
    private String link;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "from", referencedColumnName = "id")
    private Users from;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hide")
    private boolean hide;
    @Basic(optional = false)
    @NotNull
    @Column(name = "deleted")
    private boolean deleted;
    @Size(max = 250)
    @Column(name = "featured_image")
    private String featuredImage;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "emailId")
    private Collection<EmailsMailingGroups> emailsMailingGroupsCollection;

}
