package es.kairosds.pricepractice.domain.repositories;

import java.util.List;

import es.kairosds.pricepractice.domain.aggregates.Price;

public interface PriceRepository {
    
    public List<Price> findAll();
}
