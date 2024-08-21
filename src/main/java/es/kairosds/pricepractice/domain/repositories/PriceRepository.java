package es.kairosds.pricepractice.domain.repositories;

import java.time.LocalDateTime;
import java.util.List;

import es.kairosds.pricepractice.domain.aggregates.Price;

public interface PriceRepository {
    
    public List<Price> findPricesByCriteria(LocalDateTime date, String productID, String branchID);
}
