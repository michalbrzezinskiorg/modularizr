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
@Table(name = "institutions_notes")
@Data
public class InstitutionsNotes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "notatka")
    private String notatka;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stworzono")
    @Temporal(TemporalType.TIMESTAMP)
    private Date stworzono;
    @Basic(optional = false)
    @NotNull
    @Column(name = "edytowano")
    @Temporal(TemporalType.TIMESTAMP)
    private Date edytowano;
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_id")
    private int userId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "instytucja_id")
    private int instytucjaId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hide")
    private boolean hide;

}
