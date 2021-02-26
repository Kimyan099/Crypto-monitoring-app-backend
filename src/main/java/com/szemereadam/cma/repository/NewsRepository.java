package com.szemereadam.cma.repository;

import com.szemereadam.cma.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
