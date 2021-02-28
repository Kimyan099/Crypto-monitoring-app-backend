package com.szemereadam.cma.repository;

import com.szemereadam.cma.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {

    @Query("FROM News ORDER BY publishedAt DESC")
    @Modifying(clearAutomatically = true)
    List<News> getAllNewsDESC();

    @Query("FROM News ORDER BY publishedAt ASC")
    @Modifying(clearAutomatically = true)
    List<News> getAllNewsASC();
}
