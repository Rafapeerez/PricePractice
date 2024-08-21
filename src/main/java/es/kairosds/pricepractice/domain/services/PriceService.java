package es.kairosds.pricepractice.domain.services;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;


import es.kairosds.pricepractice.domain.aggregates.Price;
import es.kairosds.pricepractice.domain.exception.PriceNotFoundException;
import es.kairosds.pricepractice.domain.repositories.PriceRepository;

public class PriceService {

    private PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }
    
    public Price searchPrice(LocalDateTime date, String productID, String branchID) throws PriceNotFoundException {
        
        Optional<Price> price = this.priceRepository.findAll().stream()
            .filter(p -> p.getDateStart().isBefore(date) && p.getDateEnd().isAfter(date))
            .filter(p -> p.getProductID().equals(productID))
            .filter(p -> p.getBrandID().equals(branchID))
        .max(Comparator.comparingInt(Price::getPriority));
                
        if (price.isPresent()) {
            return price.get();
        } else {
            throw new PriceNotFoundException("Price not found.");
        }
    }
}
