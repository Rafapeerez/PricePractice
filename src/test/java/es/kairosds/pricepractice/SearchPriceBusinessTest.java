package es.kairosds.pricepractice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import es.kairosds.pricepractice.application.exceptions.PriceNotFoundException;
import es.kairosds.pricepractice.application.search_price.SearchPriceRequest;
import es.kairosds.pricepractice.application.search_price.SearchPriceResponse;
import es.kairosds.pricepractice.application.search_price.SearchPriceUseCase;
import es.kairosds.pricepractice.domain.aggregates.Price;
import es.kairosds.pricepractice.domain.repositories.PriceRepository;
import es.kairosds.pricepractice.domain.services.PriceService;
import es.kairosds.pricepractice.domain.util.FormatUtil;
import es.kairosds.pricepractice.domain.vos.Amount;
import es.kairosds.pricepractice.domain.vos.Currency;

class SearchPriceBusinessTest {

    private final PriceRepository priceRepository = Mockito.mock(PriceRepository.class);
    private final PriceService priceService = new PriceService(priceRepository);
    private final SearchPriceUseCase searchPriceUseCase = new SearchPriceUseCase(priceService);

    @Test
    void shouldReturnsFormattedDateToString() {
        LocalDateTime date = LocalDateTime.of(2024, 8, 22, 15, 30, 0);

        String result = FormatUtil.toFormat(date);

        assertEquals("2024-08-22-15.30.00", result);
    }

    @Test
    void shouldReturnsFormattedDoubleToString() {
        Double num = 123.456789;

        String result = FormatUtil.formatDouble(num);

        assertEquals("123,46", result);
    }
        

    @Test
    void shouldSearchPriceFirstCase() throws PriceNotFoundException {

        Mockito.when(priceRepository.findPricesByCriteria(
            LocalDateTime.of(2020, Month.JUNE, 14, 10, 00, 00),
            "35455",
            "1"))
        .thenReturn(this.getPrice1());
            
        SearchPriceRequest request = SearchPriceRequest.builder()
                .date(LocalDateTime.of(2020, Month.JUNE, 14, 10, 00, 00))
                .productID("35455")
                .brandID("1")
                .build();
            
        SearchPriceResponse response = searchPriceUseCase.execute(request);
            
        assertEquals("35455", response.getProductID());
        assertEquals("1", response.getBrandID());
        assertEquals("2020-06-14-00.00.00", response.getDateStart());
        assertEquals("2020-12-31-23.59.59", response.getDateEnd());
        assertEquals("1", response.getRate());
        assertEquals("35,50 EUR", response.getPrice());
    }

    @Test
    void shouldSearchPriceSecondCase() throws PriceNotFoundException {
        Mockito.when(priceRepository.findPricesByCriteria(
            LocalDateTime.of(2020, Month.JUNE, 14, 16, 0, 0),
            "35455",
            "1"))
        .thenReturn(this.getPrice2());
                
        SearchPriceRequest request = SearchPriceRequest.builder()
                .date(LocalDateTime.of(2020, Month.JUNE, 14, 16, 0, 0))
                .productID("35455")
                .brandID("1")
        .build();

        SearchPriceResponse response = searchPriceUseCase.execute(request);

        assertEquals("35455", response.getProductID());
        assertEquals("1", response.getBrandID());
        assertEquals("2020-06-14-15.00.00", response.getDateStart());
        assertEquals("2020-06-14-18.30.00", response.getDateEnd());
        assertEquals("2", response.getRate());
        assertEquals("25,45 EUR", response.getPrice());
    }

    @Test
    void shouldSearchPriceThirdCase() throws PriceNotFoundException {
        Mockito.when(priceRepository.findPricesByCriteria(
            LocalDateTime.of(2020, Month.JUNE, 14, 21, 0, 0),
            "35455",
            "1"))
        .thenReturn(this.getPrice1());

        SearchPriceRequest request = SearchPriceRequest.builder()
                .date(LocalDateTime.of(2020, Month.JUNE, 14, 21, 0, 0))
                .productID("35455")
                .brandID("1")
        .build();

        SearchPriceResponse response = searchPriceUseCase.execute(request);

        assertEquals("35455", response.getProductID());
        assertEquals("1", response.getBrandID());
        assertEquals("2020-06-14-00.00.00", response.getDateStart());
        assertEquals("2020-12-31-23.59.59", response.getDateEnd());
        assertEquals("1", response.getRate());
        assertEquals("35,50 EUR", response.getPrice());
    }

    @Test
    void shouldSearchPriceFourthCase() throws PriceNotFoundException {
        Mockito.when(priceRepository.findPricesByCriteria(
            LocalDateTime.of(2020, Month.JUNE, 15, 10, 0, 0),
            "35455",
            "1"))
        .thenReturn(this.getPrice4());

        SearchPriceRequest request = SearchPriceRequest.builder()
                .date(LocalDateTime.of(2020, Month.JUNE, 15, 10, 0, 0))
                .productID("35455")
                .brandID("1")
        .build();

        SearchPriceResponse response = searchPriceUseCase.execute(request);

        assertEquals("35455", response.getProductID());
        assertEquals("1", response.getBrandID());
        assertEquals("2020-06-15-00.00.00", response.getDateStart());
        assertEquals("2020-06-15-11.00.00", response.getDateEnd());
        assertEquals("3", response.getRate());
        assertEquals("30,50 EUR", response.getPrice());
    }
        
    @Test
    void shouldSearchPriceFifthCase() throws PriceNotFoundException {
        Mockito.when(priceRepository.findPricesByCriteria(
                LocalDateTime.of(2020, Month.JUNE, 16, 21, 0, 0),
                "35455",
                "1"))
                .thenReturn(this.getPrice5());

        SearchPriceRequest request = SearchPriceRequest.builder()
                .date(LocalDateTime.of(2020, Month.JUNE, 16, 21, 0, 0))
                .productID("35455")
                .brandID("1")
                .build();

        SearchPriceResponse response = searchPriceUseCase.execute(request);

        assertEquals("35455", response.getProductID());
        assertEquals("1", response.getBrandID());
        assertEquals("2020-06-15-16.00.00", response.getDateStart());
        assertEquals("2020-12-31-23.59.59", response.getDateEnd());
        assertEquals("4", response.getRate());
        assertEquals("38,95 EUR", response.getPrice());
    }
        
    private List<Price> getPrice1() {
        return List.of(Price.builder()
                .brandID("1")
                .dateStart(LocalDateTime.of(2020, Month.JUNE, 14, 0, 0, 0))
                .dateEnd(LocalDateTime.of(2020, Month.DECEMBER, 31, 23, 59, 59))
                .priceList(1)
                .priority(0)
                .productID("35455")
                .amount(new Amount(35.50))
                .currency(Currency.valueOf("EUR"))
                .build());
    }

    private List<Price> getPrice2() {
        return List.of(Price.builder()
                .brandID("1")
                .dateStart(LocalDateTime.of(2020, Month.JUNE, 14, 15, 0, 0))
                .dateEnd(LocalDateTime.of(2020, Month.JUNE, 14, 18, 30, 0))
                .priceList(2)
                .priority(1)
                .productID("35455")
                .amount(new Amount(25.45))
                .currency(Currency.valueOf("EUR"))
                .build());
    }

    private List<Price> getPrice4() {
        return List.of(Price.builder()
                .brandID("1")
                .dateStart(LocalDateTime.of(2020, Month.JUNE, 15, 0, 0, 0))
                .dateEnd(LocalDateTime.of(2020, Month.JUNE, 15, 11, 0, 0))
                .priceList(3)
                .priority(1)
                .productID("35455")
                .amount(new Amount(30.50))
                .currency(Currency.valueOf("EUR"))
                .build());
    }

    private List<Price> getPrice5() {
        return List.of(Price.builder()
                .brandID("1")
                .dateStart(LocalDateTime.of(2020, Month.JUNE, 15, 16, 0, 0))
                .dateEnd(LocalDateTime.of(2020, Month.DECEMBER, 31, 23, 59, 59))
                .priceList(4)
                .priority(1)
                .productID("35455")
                .amount(new Amount(38.95))
                .currency(Currency.valueOf("EUR"))
                .build());
    }

}
