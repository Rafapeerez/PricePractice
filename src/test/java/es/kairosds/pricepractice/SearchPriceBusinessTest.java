package es.kairosds.pricepractice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import es.kairosds.pricepractice.application.search_price.SearchPriceRequest;
import es.kairosds.pricepractice.application.search_price.SearchPriceResponse;
import es.kairosds.pricepractice.application.search_price.SearchPriceUseCase;
import es.kairosds.pricepractice.domain.aggregates.Price;
import es.kairosds.pricepractice.domain.exception.PriceNotFoundException;
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
        void shouldSearchPriceFirstCase() throws PriceNotFoundException {

                Mockito.when(priceRepository.findAll()).thenReturn(this.getPrices());

                SearchPriceRequest request = SearchPriceRequest.builder()
                        .date(FormatUtil.dateParse("2020-06-14-10.00.00"))
                        .productID("35455")
                        .brandID("1")
                .build();

                SearchPriceResponse response = searchPriceUseCase.execute(request);

                assertEquals("35455", response.getProductID());
                assertEquals("1", response.getBrandID());
                assertEquals("2020-06-14-00.00.00", response.getDateStart());
                assertEquals("2020-12-31-23.59.59", response.getDateEnd());
                assertEquals("1", response.getRate());
                assertEquals("35.50 EUR", response.getPrice());
        }

        @Test
        void shouldSearchPriceSecondCase() throws PriceNotFoundException {
                Mockito.when(priceRepository.findAll()).thenReturn(this.getPrices());

                SearchPriceRequest request = SearchPriceRequest.builder()
                        .date(FormatUtil.dateParse("2020-06-14-16.00.00"))
                        .productID("35455")
                        .brandID("1")
                .build();

                SearchPriceResponse response = searchPriceUseCase.execute(request);

                assertEquals("35455", response.getProductID());
                assertEquals("1", response.getBrandID());
                assertEquals("2020-06-14-15.00.00", response.getDateStart());
                assertEquals("2020-06-14-18.30.00", response.getDateEnd());
                assertEquals("2", response.getRate());
                assertEquals("25.45 EUR", response.getPrice());
        }

        @Test
        void shouldSearchPriceThirdCase() throws PriceNotFoundException {
                Mockito.when(priceRepository.findAll()).thenReturn(this.getPrices());

                SearchPriceRequest request = SearchPriceRequest.builder()
                        .date(FormatUtil.dateParse("2020-06-14-21.00.00"))
                        .productID("35455")
                        .brandID("1")
                .build();

                SearchPriceResponse response = searchPriceUseCase.execute(request);

                assertEquals("35455", response.getProductID());
                assertEquals("1", response.getBrandID());
                assertEquals("2020-06-14-00.00.00", response.getDateStart());
                assertEquals("2020-12-31-23.59.59", response.getDateEnd());
                assertEquals("1", response.getRate());
                assertEquals("35.50 EUR", response.getPrice());
        }

        @Test
        void shouldSearchPriceFourthCase() throws PriceNotFoundException {
                Mockito.when(priceRepository.findAll()).thenReturn(this.getPrices());

                SearchPriceRequest request = SearchPriceRequest.builder()
                        .date(FormatUtil.dateParse("2020-06-15-10.00.00"))
                        .productID("35455")
                        .brandID("1")
                .build();

                SearchPriceResponse response = searchPriceUseCase.execute(request);

                assertEquals("35455", response.getProductID());
                assertEquals("1", response.getBrandID());
                assertEquals("2020-06-15-00.00.00", response.getDateStart());
                assertEquals("2020-06-15-11.00.00", response.getDateEnd());
                assertEquals("3", response.getRate());
                assertEquals("30.50 EUR", response.getPrice());
        }
        
        @Test
        void shouldSearchPriceFifthCase() throws PriceNotFoundException {
                Mockito.when(priceRepository.findAll()).thenReturn(this.getPrices());

                SearchPriceRequest request = SearchPriceRequest.builder()
                        .date(FormatUtil.dateParse("2020-06-16-21.00.00"))
                        .productID("35455")
                        .brandID("1")
                .build();

                SearchPriceResponse response = searchPriceUseCase.execute(request);

                assertEquals("35455", response.getProductID());
                assertEquals("1", response.getBrandID());
                assertEquals("2020-06-15-16.00.00", response.getDateStart());
                assertEquals("2020-12-31-23.59.59", response.getDateEnd());
                assertEquals("4", response.getRate());
                assertEquals("38.95 EUR", response.getPrice());
        }

        private List<Price> getPrices() {
                List<Price> listPrices = new ArrayList<>();

                listPrices.add(Price.builder().brandID("1").dateStart(FormatUtil.dateParse("2020-06-14-00.00.00"))
                        .dateEnd(FormatUtil.dateParse("2020-12-31-23.59.59")).priceList("1").priority(0).productID("35455")
                        .amount(new Amount(Double.valueOf(35.50))).currency(Currency.valueOf("EUR")).build());

                listPrices.add(Price.builder().brandID("1").dateStart(FormatUtil.dateParse("2020-06-14-15.00.00"))
                        .dateEnd(FormatUtil.dateParse("2020-06-14-18.30.00")).priceList("2").priority(1).productID("35455")
                        .amount(new Amount(Double.valueOf(25.45))).currency(Currency.valueOf("EUR")).build());

                listPrices.add(Price.builder().brandID("1").dateStart(FormatUtil.dateParse("2020-06-15-00.00.00"))
                        .dateEnd(FormatUtil.dateParse("2020-06-15-11.00.00")).priceList("3").priority(1).productID("35455")
                        .amount(new Amount(Double.valueOf(30.50))).currency(Currency.valueOf("EUR")).build());

                listPrices.add(Price.builder().brandID("1").dateStart(FormatUtil.dateParse("2020-06-15-16.00.00"))
                        .dateEnd(FormatUtil.dateParse("2020-12-31-23.59.59")).priceList("4").priority(1).productID("35455")
                        .amount(new Amount(Double.valueOf(38.95))).currency(Currency.valueOf("EUR")).build());

                return listPrices;
        }
}
