package com.decentralizer.spreadr.modules.cms.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class Article {
    private Integer id;
    @NotNull
    @Size(min = 1, max = 256)
    private String temat;
    @NotNull
    @Size(min = 1, max = 65535)
    private String tresc;
    @Size(max = 65535)
    private String tags;
    @NotNull
    private int autor;
    @NotNull
    private LocalDate data;
    private LocalDate edytowano;
    @NotNull
    @Size(min = 1, max = 200)
    private String link;
    @NotNull
    private boolean hide;
    @NotNull
    private boolean deleted;
    @Size(max = 250)
    private String featuredImage;
}
