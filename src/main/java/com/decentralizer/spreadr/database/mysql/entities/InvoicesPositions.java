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

/**
 * @author michal
 */
@Entity
@Table(name = "invoices_positions")

@Data
public class InvoicesPositions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "invoice_id")
    private int invoiceId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "netto")
    private float netto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "brutto")
    private float brutto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vat")
    private int vat;
    @Basic(optional = false)
    @NotNull
    @Column(name = "order_id")
    private int orderId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantity")
    private int quantity;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "unit")
    private String unit;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "currency")
    private String currency;
    @Size(max = 256)
    @Column(name = "product_name")
    private String productName;
    @Lob
    @Size(max = 65535)
    @Column(name = "product_desc")
    private String productDesc;

}
