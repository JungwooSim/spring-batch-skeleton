package com.example.firstbatch.batch.domain;

import com.example.firstbatch.batch.domain.vo.StockVo;
import lombok.Getter;

@Getter
public class StockAggregateDto {
    private Double openMin;
    private Double openMax;
    private Double openAverage;

    public void setStock(StockVo stockVo) {
        this.openMin = Math.min(this.openMin, stockVo.getOpen());
        this.openMax = Math.max(this.openMax, stockVo.getOpen());
        this.openAverage += stockVo.getOpen();
    }
}
