package com.decentralizer.spreadr.modules.welcome;

import com.decentralizer.spreadr.modules.welcome.dto.ArticlesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("welcome")
public class WelcomeController {

    private final WelcomeService welcomeService;

    @GetMapping("articles")
    Flux<ArticlesDto> welcomeArticles(Pageable pageable) {
        return welcomeService.getArticles(pageable);
    }

}
