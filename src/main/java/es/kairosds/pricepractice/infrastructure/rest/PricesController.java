package es.kairosds.pricepractice.infrastructure.rest;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.kairosds.pricepractice.application.exceptions.PriceNotFoundException;
import es.kairosds.pricepractice.application.search_price.SearchPriceRequest;
import es.kairosds.pricepractice.application.search_price.SearchPriceResponse;
import es.kairosds.pricepractice.application.search_price.SearchPriceUseCase;

@RestController
public class PricesController {

    @Autowired
    private SearchPriceUseCase useCase;

    @GetMapping("/price/search")
    public ResponseEntity<SearchPriceResponse> searchPrice(
        @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime date,
        @RequestParam(required = true) Integer productId,
        @RequestParam(required = true) Integer brandId
    ) throws PriceNotFoundException {

        SearchPriceRequest request = SearchPriceRequest.builder()
            .date(date)
            .productID(productId.toString())
            .brandID(brandId.toString())
        .build();

        SearchPriceResponse response = useCase.execute(request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
