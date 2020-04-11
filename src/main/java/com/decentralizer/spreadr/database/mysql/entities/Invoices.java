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
@Table(name = "invoices")
@Data
public class Invoices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numer_fv")
    private int numerFv;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_vat")
    private boolean isVat;
    @Basic(optional = false)
    @NotNull
    @Column(name = "payment_total")
    private float paymentTotal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_wystawienia")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataWystawienia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sprzedawca")
    private int sprzedawca;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nabywca")
    private int nabywca;
    @Size(max = 254)
    @Column(name = "seller_name")
    private String sellerName;
    @Size(max = 254)
    @Column(name = "seller_address")
    private String sellerAddress;
    @Size(max = 254)
    @Column(name = "seller_nip")
    private String sellerNip;
    @Size(max = 254)
    @Column(name = "buyer")
    private String buyer;
    @Size(max = 254)
    @Column(name = "buyer_nip")
    private String buyerNip;
    @Size(max = 254)
    @Column(name = "buyer_address")
    private String buyerAddress;
    @Size(max = 254)
    @Column(name = "delivery_address")
    private String deliveryAddress;
    @Size(max = 254)
    @Column(name = "payment_to")
    private String paymentTo;
    @Size(max = 254)
    @Column(name = "payment_date")
    private String paymentDate;

}
