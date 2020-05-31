package com.decentralizer.spreadr.modules.welcome;

import com.decentralizer.spreadr.modules.welcome.dto.ArticlesDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
class WelcomeService {

    ArticlesRepository articlesRepository;
    ModelMapper modelMapper;

    public Flux<ArticlesDto> getArticles(Pageable pageable) {
        return articlesRepository.getWelcomePageArticles(pageable).map(article -> modelMapper.map(article, ArticlesDto.class));
    }
}
