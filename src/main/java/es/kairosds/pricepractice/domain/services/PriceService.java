package es.kairosds.pricepractice.domain.services;

import java.time.LocalDateTime;
import java.util.Comparator;

import es.kairosds.pricepractice.application.exceptions.PriceNotFoundException;
import es.kairosds.pricepractice.domain.aggregates.Price;
import es.kairosds.pricepractice.domain.repositories.PriceRepository;

public class PriceService {

    private PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }
    
    public Price searchPrice(LocalDateTime date, String productID, String branchID) throws PriceNotFoundException {
        
        return this.priceRepository.findPricesByCriteria(date, productID, branchID).stream()
        .max(Comparator.comparingInt(Price::getPriority)).orElseThrow(() -> new PriceNotFoundException("PriceNotFound"));
    }
}
