package es.kairosds.pricepractice.infrastructure.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepositoryJPA extends JpaRepository<PriceJPA, Long> {
    
}
