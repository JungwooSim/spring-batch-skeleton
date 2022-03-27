package com.example.firstbatch.batch.domain;

import com.example.firstbatch.batch.domain.vo.StockVo;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@ToString
@Getter
public class StockAggregateDto {
    private Double openMin;
    private Double openMax;
    private Double openAverage = 0.0;

    public void setStock(StockVo stockVo) {
        this.openMin = Objects.isNull(this.openMin) ? stockVo.getOpen() : Math.min(this.openMin, stockVo.getOpen());
        this.openMax = Objects.isNull(this.openMax) ? stockVo.getOpen() : Math.max(this.openMax, stockVo.getOpen());
        this.openAverage += stockVo.getOpen();
    }
}
