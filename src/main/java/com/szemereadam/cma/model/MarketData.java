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
public class MarketData {

    @Id
    @GeneratedValue
    private Long id;

    private double percentageChangeLastHour;

    private double percentageChangeLast24Hours;

    private double price;

    @OneToOne(mappedBy = "marketData")
    private Currency currency;
}
