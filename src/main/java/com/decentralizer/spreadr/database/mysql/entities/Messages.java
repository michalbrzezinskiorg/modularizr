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
@Table(name = "messages")

@Data
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 150)
    @Column(name = "inny_nadawca")
    private String innyNadawca;
    @Size(max = 150)
    @Column(name = "inny_odbiorca")
    private String innyOdbiorca;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "temat")
    private String temat;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "tresc")
    private String tresc;
    @Lob
    @Size(max = 65535)
    @Column(name = "headers")
    private String headers;
    @Lob
    @Size(max = 65535)
    @Column(name = "attachments")
    private String attachments;
    @Basic(optional = false)
    @NotNull
    @Column(name = "odbiorca_czytal")
    private boolean odbiorcaCzytal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ile_razy_czytal")
    private int ileRazyCzytal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kiedy_stworzono")
    @Temporal(TemporalType.TIMESTAMP)
    private Date kiedyStworzono;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kiedy_czytano")
    private int kiedyCzytano;
    @Basic(optional = false)
    @NotNull
    @Column(name = "modyfikacja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modyfikacja;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ukryj_u_odbiorcy")
    private boolean ukryjUOdbiorcy;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ukryj_u_nadawcy")
    private boolean ukryjUNadawcy;
    @Size(max = 200)
    @Column(name = "imap_message_id")
    private String imapMessageId;
    @Size(max = 100)
    @Column(name = "tracklink")
    private String tracklink;
    @JoinColumn(name = "nadawca", referencedColumnName = "id")
    @ManyToOne
    private Users nadawca;
    @JoinColumn(name = "nadawca_instytucja", referencedColumnName = "id")
    @ManyToOne
    private Institutions nadawcaInstytucja;
    @JoinColumn(name = "odbiorca", referencedColumnName = "id")
    @ManyToOne
    private Users odbiorca;

}
