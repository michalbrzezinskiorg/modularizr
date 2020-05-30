package com.decentralizer.spreadr.modules.cms;

import com.decentralizer.spreadr.modules.cms.dto.ArticleDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("articles")
public class ArticlesController {

    ArticlesService articlesService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void saveArticle(ArticleDto article) {
        articlesService.create(article);
    }


}
