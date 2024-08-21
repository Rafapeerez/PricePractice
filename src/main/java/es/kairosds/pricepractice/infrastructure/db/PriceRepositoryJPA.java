package es.kairosds.pricepractice.infrastructure.db;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PriceRepositoryJPA extends JpaRepository<PriceJPA, Long> {
    @Query("SELECT p FROM PriceJPA p WHERE p.brandId = :brandId AND p.productId = :productId AND p.startDate < :date AND p.endDate > :date")
    List<PriceJPA> findPricesByCriteria(
        @Param("brandId") String brandId, 
        @Param("productId") String productId, 
        @Param("date") LocalDateTime date
    );
}
