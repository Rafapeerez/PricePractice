package es.kairosds.pricepractice.application.search_price;

import es.kairosds.pricepractice.domain.aggregates.Price;
import es.kairosds.pricepractice.domain.exception.PriceNotFoundException;
import es.kairosds.pricepractice.domain.services.PriceService;
import es.kairosds.pricepractice.domain.util.FormatUtil;

public class SearchPriceUseCase {
    
    private PriceService priceService;

    public SearchPriceUseCase(PriceService priceService) {
        this.priceService = priceService;
    }

    public SearchPriceResponse execute(SearchPriceRequest request) throws PriceNotFoundException {
        Price price = this.priceService.searchPrice(request.getDate(), request.getProductID(), request.getBrandID());

        return SearchPriceResponse.builder()
            .productID(price.getProductID())
            .brandID(price.getBrandID())
            .dateStart(FormatUtil.toFormat(price.getDateStart()))
            .dateEnd(FormatUtil.toFormat(price.getDateEnd()))
            .price(price.getFinalPrice())
            .rate(price.getPriceList())
        .build();        
    }

}
