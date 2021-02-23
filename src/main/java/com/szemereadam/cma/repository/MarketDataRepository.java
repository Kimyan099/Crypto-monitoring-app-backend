package com.szemereadam.cma.repository;

import com.szemereadam.cma.model.MarketData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketDataRepository extends JpaRepository<MarketData, Long> {
}
