package es.kairosds.pricepractice.infrastructure.db;

import java.time.LocalDateTime;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "PRICES")
public class PriceJPA {
    
    @Id
    @Column(name = "PRICE_ID")
    private Long priceId;

    @Column(name = "BRAND_ID")
    private String brandId;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;
    
    @Column(name = "END_DATE")
    private LocalDateTime endDate;
    
    @Column(name = "PRICE_LIST")
    private String priceList;
    
    @Column(name = "PRODUCT_ID")
    private String productId;
    
    @Column(name = "PRIORITY")
    private Integer priority;
    
    @Column(name = "PRICE")
    private Double price;
    
    @Column(name = "CURR")
    private String currency;

}
