package com.decentralizer.spreadr.database.mysql;

import com.decentralizer.spreadr.database.mysql.entities.Articles;
import com.decentralizer.spreadr.modules.cms.ArticlesRepositoryForCms;
import com.decentralizer.spreadr.modules.cms.domain.Article;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ArticlesRepositoryForCmsForCMS implements ArticlesRepositoryForCms {
    private final ArticlesRepositoryMysql articlesRepositoryMysql;
    private final ModelMapper modelMapper;

    @Override
    public Mono<Article> findByUrl(String url) {
        return articlesRepositoryMysql.findByLink(url).map(a -> Mono.just(a)).orElse(Mono.empty());
    }

    @Override
    public void save(Article article) {
        articlesRepositoryMysql.save(modelMapper.map(article, Articles.class));
    }

    public Flux<com.decentralizer.spreadr.modules.welcome.domain.Article> getWelcomePageArticles(Pageable pageable) {
        Page<Articles> all = articlesRepositoryMysql.findAll(pageable);
        Flux<com.decentralizer.spreadr.modules.welcome.domain.Article> articles = pageToFlux(all.map(a -> modelMapper.map(a, com.decentralizer.spreadr.modules.welcome.domain.Article.class)));
        return articles;
    }

    private Flux<com.decentralizer.spreadr.modules.welcome.domain.Article> pageToFlux(Page<com.decentralizer.spreadr.modules.welcome.domain.Article> all) {
        return Flux.concat(all.toList().stream().map(Mono::just).collect(Collectors.toList()));
    }
}
