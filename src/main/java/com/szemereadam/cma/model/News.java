package com.szemereadam.cma.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
public class News {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String content;

    private String publishedAt;

    private String author;

    private String url;
}
