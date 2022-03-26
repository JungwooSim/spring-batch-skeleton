package com.example.firstbatch.batch.domain.vo;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@Getter
public class StockVo {
    private LocalDate date;
    private Double open;
    private Double high;
    private Long low;
    private Long close;
    private Long volumn;
    private Long adjOpen;
    private Long adjHigh;
    private Long adjLow;
    private Double adjClose;
    private Long adjVolumn;
}
