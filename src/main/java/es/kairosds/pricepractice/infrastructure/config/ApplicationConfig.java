package es.kairosds.pricepractice.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.kairosds.pricepractice.application.search_price.SearchPriceUseCase;
import es.kairosds.pricepractice.domain.repositories.PriceRepository;
import es.kairosds.pricepractice.domain.services.PriceService;

@Configuration
public class ApplicationConfig {
    
    @Bean
    public PriceService priceService(PriceRepository priceRepository) {
        return new PriceService(priceRepository);
    }

    @Bean
    public SearchPriceUseCase searchPriceUseCase(PriceService priceService) {
        return new SearchPriceUseCase(priceService);
    }

}
