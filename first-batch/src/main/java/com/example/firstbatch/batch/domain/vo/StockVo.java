package com.example.firstbatch.batch.domain.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@ToString
@Getter
@Slf4j
@Setter
public class StockVo {
    private LocalDate date;
    private Double open;
    private Double high;
    private Long low;
    private Long close;
    private Long volumn;
    private Long adjOpen;
    private Double adjHigh;
    private Long adjLow;
    private Double adjClose;
    private Long adjVolumn;

    public void setDate(String date) {
        LocalDate parseDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        this.date = parseDate;
    }

    public void setAdjHigh(String adjHigh) {
        this.adjHigh = Double.valueOf(adjHigh);
    }
}
