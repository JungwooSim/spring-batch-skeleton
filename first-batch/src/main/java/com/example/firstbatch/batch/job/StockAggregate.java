package com.example.firstbatch.batch.job;

import com.example.firstbatch.batch.domain.StockAggregateDto;
import com.example.firstbatch.batch.domain.vo.StockVo;
import com.example.firstbatch.batch.listener.StockAggregateItemReaderListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.UrlResource;

import java.net.MalformedURLException;

/**
 * TODO : processor 에서 chunk 사이즈가 최대값이 되었을 때 평균구할 수 있도록 로직 추가필요
 * write 를 통해 파일 쓰는 작업은 완료
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class StockAggregate {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final int chunkSize = 10;

    @Bean
    public Job stockAggregateJob() throws MalformedURLException {
        return jobBuilderFactory.get("stockAggregateJob")
                .incrementer(new RunIdIncrementer())
                .start(stockAggregateStep())
                .build();
    }

    @Bean
    public Step stockAggregateStep() throws MalformedURLException {
        return stepBuilderFactory.get("stockAggregateStep")
                .<StockVo, StockAggregateDto> chunk(chunkSize)
                .reader(stockAggregateJobReader())
                .listener(new StockAggregateItemReaderListener())
//                .faultTolerant().skip(FlatFileParseException.class)
                .processor(stockAggregateJobProcessor())
                .writer(stockAggregateJobWriter())
                .build();
    }

    @Bean
    public ItemReader<StockVo> stockAggregateJobReader() throws MalformedURLException {
        UrlResource urlResource = new UrlResource("file:/Users/bigpenguin/project/spring-batch-skeleton/data/stock/950130.csv");

        FlatFileItemReader<StockVo> reader = new FlatFileItemReaderBuilder<StockVo>()
                .name("stockAggregateJobReader")
                .targetType(StockVo.class)
                .linesToSkip(1)
                .delimited().delimiter(",")
                .names("date", "open", "high", "low", "close", "volumn", "adjOpen", "adjHigh", "adjLow")
                .resource(urlResource)
                .build();
        return reader;
    }

    @Bean
    public ItemProcessor<StockVo, StockAggregateDto> stockAggregateJobProcessor() {
        StockAggregateDto stockAggregateDto = new StockAggregateDto();

        ItemProcessor<StockVo, StockAggregateDto> itemProcessor = new ItemProcessor<>() {
            @Override
            public StockAggregateDto process(StockVo stockVo) throws Exception {
                stockAggregateDto.setStock(stockVo);
                return stockAggregateDto;
            }
        };

        return itemProcessor;
    }

    @Bean
    public ItemWriter<StockAggregateDto> stockAggregateJobWriter() {
        return new FlatFileItemWriterBuilder<StockAggregateDto>()
                .name("stockAggregateJobWriter")
                .resource(new FileSystemResource("./data/stock/stockAggregateData.txt"))
                .delimited()
                .delimiter("|")
                .names(new String[]{"openMin", "openMax", "openAverage"})
                .build();
    }

}
