package com.szemereadam.cma.model;

import lombok.*;


import javax.persistence.*;


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

    @Column(length = 100000)
    private String background;

    @Column(length = 100000)
    private String overview;

    @Column(length = 100000)
    private String tokenDistributionDescription;

    @Column(length = 100000)
    private String technology;

    @OneToOne(mappedBy = "profile")
    private Currency currency;

}
