package es.kairosds.pricepractice.domain.aggregates;

import java.time.LocalDateTime;

import es.kairosds.pricepractice.domain.util.FormatUtil;
import es.kairosds.pricepractice.domain.vos.Amount;
import es.kairosds.pricepractice.domain.vos.Currency;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Price {
    private final String brandID;
    private final LocalDateTime dateStart;
    private final LocalDateTime dateEnd;
    private final Integer priceList;
    private final String productID;    
    private final Integer priority;
    private final Amount amount;
    private final Currency currency;

    public String getFinalPrice() {
        return FormatUtil.formatDouble(this.amount.getValue()) + " " + this.currency.getValue();
    }

}
