package es.kairosds.pricepractice.infrastructure.db;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.kairosds.pricepractice.application.mappers.PriceMapper;
import es.kairosds.pricepractice.domain.aggregates.Price;
import es.kairosds.pricepractice.domain.repositories.PriceRepository;

@Repository
public class PriceRepositoryJPAAdapter implements PriceRepository {

    @Autowired
    private PriceRepositoryJPA priceRepositoryJPA;

    @Override
    public List<Price> findAll() {
        return this.priceRepositoryJPA.findAll().stream().map(PriceMapper::toModel).collect(Collectors.toList());
    }
    
}
