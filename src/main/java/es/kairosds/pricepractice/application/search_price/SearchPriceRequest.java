package es.kairosds.pricepractice.application.search_price;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchPriceRequest {
    private final LocalDateTime date;
    private final String productID;
    private final String brandID;
}
