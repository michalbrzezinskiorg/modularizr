package com.decentralizer.spreadr.modules.welcome;

import com.decentralizer.spreadr.modules.welcome.dto.ArticlesDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;

public class WelcomeService {

    ArticlesRepository articlesRepository;
    ModelMapper modelMapper;

    public Flux<ArticlesDto> getArticles(Pageable pageable) {
        return articlesRepository.getWelcomePageArticles(pageable).map(article -> modelMapper.map(article, ArticlesDto.class));
    }
}
