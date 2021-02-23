package com.szemereadam.cma.model;

import lombok.*;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
public class Profile {

    @Id
    @GeneratedValue
    private Long id;

    private String background;

    private String overview;

    private String description;

    @OneToOne(mappedBy = "profile")
    private Currency currency;

}
