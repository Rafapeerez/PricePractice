package es.kairosds.pricepractice.application.search_price;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchPriceResponse {
    private final String productID;
    private final String brandID;
    private final String rate;
    private final String dateStart;
    private final String dateEnd;
    private final String price;
}
