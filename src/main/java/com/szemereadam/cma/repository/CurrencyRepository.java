package com.szemereadam.cma.repository;

import com.szemereadam.cma.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}
