package es.kairosds.pricepractice.application.mappers;

import es.kairosds.pricepractice.domain.aggregates.Price;
import es.kairosds.pricepractice.domain.vos.Amount;
import es.kairosds.pricepractice.domain.vos.Currency;
import es.kairosds.pricepractice.infrastructure.db.PriceJPA;

public class PriceMapper {

    private PriceMapper() {}

    public static Price toModel(PriceJPA priceJPA) {
        return Price.builder()
            .amount(new Amount(priceJPA.getPrice()))
            .brandID(priceJPA.getBrandId())
            .currency(Currency.valueOf(priceJPA.getCurrency()))
            .dateEnd(priceJPA.getEndDate())
            .dateStart(priceJPA.getStartDate())
            .priceList(priceJPA.getPriceList())
            .productID(priceJPA.getProductId())
            .priority(priceJPA.getPriority())
        .build();
    }
}
