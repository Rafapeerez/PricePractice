package es.kairosds.pricepractice.infrastructure.db;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.kairosds.pricepractice.domain.aggregates.Price;
import es.kairosds.pricepractice.domain.repositories.PriceRepository;
import es.kairosds.pricepractice.infrastructure.mappers.PriceMapper;

@Repository
public class PriceRepositoryJPAAdapter implements PriceRepository {

    @Autowired
    private PriceRepositoryJPA priceRepositoryJPA;

    @Override
    public List<Price> findPricesByCriteria(LocalDateTime date, String productID, String branchID) {
        return this.priceRepositoryJPA.findPricesByCriteria(branchID, productID, date).stream().map(PriceMapper::toModel).collect(Collectors.toList());
    }
}
