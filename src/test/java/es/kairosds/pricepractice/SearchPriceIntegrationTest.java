package es.kairosds.pricepractice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import es.kairosds.pricepractice.application.search_price.SearchPriceResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = PricepracticeApplication.class)
class SearchPriceIntegrationTest {
    
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldWorksFirstCase() {

        ResponseEntity<SearchPriceResponse> res = restTemplate.getForEntity(
                "/price/search?date=2020-06-14T10:00:00&productId=35455&brandId=1", SearchPriceResponse.class);
        
        assertNotNull(res.getBody(), "The answer must not be null");
        assertTrue(res.getStatusCode().is2xxSuccessful());

        SearchPriceResponse response = res.getBody();

        assertEquals("35455", response.getProductID());
        assertEquals("1", response.getBrandID());
        assertEquals("2020-06-14-00.00.00", response.getDateStart());
        assertEquals("2020-12-31-23.59.59", response.getDateEnd());
        assertEquals("1", response.getRate());
        assertEquals("35,50 EUR", response.getPrice());
    }
    
    @Test
    void shouldWorksSecondCase() {

        ResponseEntity<SearchPriceResponse> res = restTemplate.getForEntity(
                "/price/search?date=2020-06-14T16:00:00&productId=35455&brandId=1", SearchPriceResponse.class);

        assertTrue(res.getStatusCode().is2xxSuccessful());

        SearchPriceResponse response = res.getBody();

        assertEquals("35455", response.getProductID());
        assertEquals("1", response.getBrandID());
        assertEquals("2020-06-14-15.00.00", response.getDateStart());
        assertEquals("2020-06-14-18.30.00", response.getDateEnd());
        assertEquals("2", response.getRate());
        assertEquals("25,45 EUR", response.getPrice());
    } 
    @Test
    void shouldWorksThirdCase() {
        ResponseEntity<SearchPriceResponse> res = restTemplate.getForEntity(
                "/price/search?date=2020-06-14T21:00:00&productId=35455&brandId=1", SearchPriceResponse.class);

        assertNotNull(res.getBody(), "The answer must not be null");
        assertTrue(res.getStatusCode().is2xxSuccessful());

        SearchPriceResponse response = res.getBody();

        assertEquals("35455", response.getProductID());
        assertEquals("1", response.getBrandID());
        assertEquals("2020-06-14-00.00.00", response.getDateStart());
        assertEquals("2020-12-31-23.59.59", response.getDateEnd());
        assertEquals("1", response.getRate());
        assertEquals("35,50 EUR", response.getPrice());
    }
    
    @Test
    void shouldWorksFourthCase() {
    
        ResponseEntity<SearchPriceResponse> res = restTemplate.getForEntity(
                "/price/search?date=2020-06-15T10:00:00&productId=35455&brandId=1", SearchPriceResponse.class);

        assertNotNull(res.getBody(), "The answer must not be null");
        assertTrue(res.getStatusCode().is2xxSuccessful());
    
        SearchPriceResponse response = res.getBody();

        assertEquals("35455", response.getProductID());
        assertEquals("1", response.getBrandID());
        assertEquals("2020-06-15-00.00.00", response.getDateStart());
        assertEquals("2020-06-15-11.00.00", response.getDateEnd());
        assertEquals("3", response.getRate());
        assertEquals("30,50 EUR", response.getPrice());
    
    } 

    @Test
    void shouldWorksFifthCase() {

        ResponseEntity<SearchPriceResponse> res = restTemplate.getForEntity(
                "/price/search?date=2020-06-16T21:00:00&productId=35455&brandId=1", SearchPriceResponse.class);

        assertNotNull(res.getBody(), "The answer must not be null");
        assertTrue(res.getStatusCode().is2xxSuccessful());

        SearchPriceResponse response = res.getBody();

        assertEquals("35455", response.getProductID());
        assertEquals("1", response.getBrandID());
        assertEquals("2020-06-15-16.00.00", response.getDateStart());
        assertEquals("2020-12-31-23.59.59", response.getDateEnd());
        assertEquals("4", response.getRate());
        assertEquals("38,95 EUR", response.getPrice());
    }
    
    @Test
    void shouldAlertNotFoundPrice() {
        ResponseEntity<SearchPriceResponse> res = restTemplate.getForEntity(
                "/price/search?date=2020-06-16T21:00:00&productId=1&brandId=4", SearchPriceResponse.class);

        assertTrue(res.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(404)));
    }
    
    @Test
    void shouldAlertMissingInfo() {
        ResponseEntity<SearchPriceResponse> res = restTemplate.getForEntity(
                "/price/search?date=2020-06-16T21:00:00&productId=35455&brandId=", SearchPriceResponse.class);

        assertTrue(res.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(400)));
    }
    
    @Test
    void shouldAlertBadRequest() {
        ResponseEntity<SearchPriceResponse> res = restTemplate.getForEntity(
                "/price/search?date=2020-06-16T21:00:00&brandId=1", SearchPriceResponse.class);
        
        assertTrue(res.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(400)));
    }
}
