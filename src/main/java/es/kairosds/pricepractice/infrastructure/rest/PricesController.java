package es.kairosds.pricepractice.infrastructure.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.kairosds.pricepractice.application.exceptions.BadRequestException;
import es.kairosds.pricepractice.application.exceptions.MissingDataException;
import es.kairosds.pricepractice.application.exceptions.PriceNotFoundException;
import es.kairosds.pricepractice.application.search_price.SearchPriceRequest;
import es.kairosds.pricepractice.application.search_price.SearchPriceResponse;
import es.kairosds.pricepractice.application.search_price.SearchPriceUseCase;
import es.kairosds.pricepractice.domain.util.FormatUtil;

@RestController
public class PricesController {

    @Autowired
    private SearchPriceUseCase useCase;

    @GetMapping("/price/search")
    public ResponseEntity<SearchPriceResponse> searchPrice(
        @RequestParam(required = true) String date,
        @RequestParam(required = true) String productId,
        @RequestParam(required = true) String brandId
    ) throws PriceNotFoundException {

        if (date == null || date.isEmpty()) {
            throw new MissingDataException("Date parameter is missing or empty");
        }
        if (productId == null || productId.isEmpty()) {
            throw new MissingDataException("Product ID is missing or empty");
        }
        if (brandId == null || brandId.isEmpty()) {
            throw new MissingDataException("Brand ID is missing or empty");
        }

        try {
            FormatUtil.dateParse(date);
        } catch (Exception e) {
            throw new BadRequestException("Invalid date format: " + date);
        }

        SearchPriceRequest request = SearchPriceRequest.builder()
            .date(FormatUtil.dateParse(date))
            .productID(productId)
            .brandID(brandId)
        .build();

        SearchPriceResponse response = useCase.execute(request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
