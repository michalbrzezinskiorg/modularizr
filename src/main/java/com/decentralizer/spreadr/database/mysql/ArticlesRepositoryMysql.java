package com.decentralizer.spreadr.database.mysql;

import com.decentralizer.spreadr.database.mysql.entities.Articles;
import com.decentralizer.spreadr.modules.cms.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticlesRepositoryMysql extends JpaRepository<Articles, Integer> {
    Optional<Article> findByLink(String url);
}
