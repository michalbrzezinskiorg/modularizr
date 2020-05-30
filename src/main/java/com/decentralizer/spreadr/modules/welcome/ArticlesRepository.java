package com.decentralizer.spreadr.modules.welcome;

import com.decentralizer.spreadr.modules.welcome.domain.Article;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;

public interface ArticlesRepository {
    Flux<Article> getWelcomePageArticles(Pageable pageable);
}
