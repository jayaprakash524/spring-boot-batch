package com.prakash.spring.boot.config;

import com.prakash.spring.boot.listener.JobCompletionListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class JobConfig {

  @Autowired
  private JobBuilderFactory jobBuilderFactory;

  @Autowired
  private Step partitionStep;

  @Bean
  public Job processJob() {
    return jobBuilderFactory.get("processJob")
        .incrementer(new RunIdIncrementer()).listener(listener())
        .flow(partitionStep).end().build();

  }

  @Bean
  public JobExecutionListener listener() {
    return new JobCompletionListener();
  }

}
