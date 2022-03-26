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
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;

import java.net.MalformedURLException;
import java.util.List;

/**
 * writer 에서 에러발생중.. 잡아야함 (콤마 개수가 안맞음)
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class StockAggregate {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ResourceLoader resourceLoader;

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
                .<StockVo, StockAggregateDto> chunk(10)
                .reader(stockAggregateJobReader())
                .listener(new StockAggregateItemReaderListener())
                .faultTolerant().skip(FlatFileParseException.class)
                .processor(stockAggregateJobProcessor())
                .writer(stockAggregateJobWriter())
                .build();
    }

    @Bean
    public ItemReader<StockVo> stockAggregateJobReader() throws MalformedURLException {
        UrlResource urlResource = new UrlResource("file:/Users/bigpenguin/project/spring-batch-skeleton/data/stock/950130.csv");

        FlatFileItemReader<StockVo> reader = new FlatFileItemReaderBuilder<StockVo>()
                .name("stockAggregateJobReader")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>())
                .targetType(StockVo.class)
                .linesToSkip(1)
                .delimited().delimiter(",")
//                .names("Date", "Open", "High", "Low", "Close", "Volumn", "AdjOpen", "AdjHigh", "AdjLow", "AdjClose", "AdjVolumn")
                .names("date", "open", "high", "low", "close", "volumn", "adjOpen", "adjHigh", "adjLow", "adjClose", "adjVolumn")

                .resource(urlResource)
                .build();
        return reader;
    }

    @Bean
    public ItemProcessor<StockVo, StockAggregateDto> stockAggregateJobProcessor() {

        ItemProcessor<StockVo, StockAggregateDto> itemProcessor = new ItemProcessor<>() {
            @Override
            public StockAggregateDto process(StockVo stockVo) throws Exception {
                return null;
            }
        };

        return itemProcessor;
    }

    @Bean
    public ItemWriter<StockAggregateDto> stockAggregateJobWriter() {
        ItemWriter<StockAggregateDto> itemWriter = new ItemWriter<>() {
            @Override
            public void write(List<? extends StockAggregateDto> items) throws Exception {

            }
        };

        return itemWriter;
    }

}
