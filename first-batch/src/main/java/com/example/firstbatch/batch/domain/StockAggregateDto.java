package com.example.firstbatch.batch.domain;

import lombok.Getter;

@Getter
public class StockAggregateDto {
    private Long openMin;
    private Long openHigh;
    private Long openAverage;
}
