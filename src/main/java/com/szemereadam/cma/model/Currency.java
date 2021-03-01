package com.szemereadam.cma.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
public class Currency {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Column(unique = true)
    private String symbol;

    @OneToOne(cascade = CascadeType.ALL)
    private Profile profile;

    @OneToOne(cascade = CascadeType.ALL)
    private MarketData marketData;


    @Singular("currency")
    @ManyToMany
    @JsonBackReference
    private Set<Client> clientList;
}