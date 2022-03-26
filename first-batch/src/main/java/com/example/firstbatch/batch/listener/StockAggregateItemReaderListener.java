package com.example.firstbatch.batch.listener;

import com.example.firstbatch.batch.domain.vo.StockVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;

@Slf4j
public class StockAggregateItemReaderListener implements ItemReadListener<StockVo> {
    @Override
    public void beforeRead() {

    }

    @Override
    public void afterRead(StockVo stockVo) {
        log.info("stockVo : {}", stockVo.toString());
    }

    @Override
    public void onReadError(Exception ex) {

    }
}
