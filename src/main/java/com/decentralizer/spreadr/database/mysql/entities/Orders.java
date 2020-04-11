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
@Table(name = "orders")

@Data
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "delivered")
    private boolean delivered;
    @Size(max = 150)
    @Column(name = "payu_order_id")
    private String payuOrderId;
    @Size(max = 20)
    @Column(name = "payu_order_status")
    private String payuOrderStatus;
    @Column(name = "transport")
    private Short transport;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantity")
    private int quantity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "invoice")
    private int invoice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "confirmed")
    private int confirmed;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    @JoinColumn(name = "buyer", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users buyer;
    @JoinColumn(name = "product", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Products product;

}
