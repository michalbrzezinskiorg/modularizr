/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.decentralizer.spreadr.database.mysql.entities;

import lombok.Data;

import javax.persistence.*;

/**
 * @author michal
 */
@Entity
@Table(name = "institutions_groups")
@Data
public class InstitutionsGroups {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Groups groupId;
    @JoinColumn(name = "dodal_relacje", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users dodalRelacje;
    @JoinColumn(name = "instytucja_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Institutions instytucjaId;

}
