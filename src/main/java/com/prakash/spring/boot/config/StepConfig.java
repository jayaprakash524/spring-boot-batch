package com.prakash.spring.boot.config;

import com.prakash.spring.boot.domain.Patient;
import com.prakash.spring.boot.step.PatientProcessor;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Slf4j
@Configuration
public class StepConfig {

  @Autowired
  DataSource mysqlDataSource;

  @Autowired
  DataSource postgresDataSource;

  @Autowired
  NamedParameterJdbcTemplate jdbcTemplate;

  @Autowired
  JdbcTemplate postgresJdbcTemplate;

  @Autowired
  public StepBuilderFactory stepBuilderFactory;


  private static final String QUERY_INSERT_PATIENTS = "INSERT " +
      "INTO PATIENT(patient_id, source_id, first_name, middle_initial, last_name, email_address, phone_number, street, city, state, zip_code, birth_date,"
      + " social_security_number) " +
      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


  @Bean
  public Step partitionStep() {
    return stepBuilderFactory.get("partitionStep").partitioner("step", partitioner())
        .partitionHandler(partitionHandler()).build();
  }

  @Bean
  public Partitioner partitioner() {
    return new SamplePartitioner(postgresJdbcTemplate, "patient", "patient_id");
  }

  @Bean
  public PartitionHandler partitionHandler() {
    TaskExecutorPartitionHandler handler = new TaskExecutorPartitionHandler();
    handler.setGridSize(10);
    handler.setTaskExecutor(taskExecutor());
    handler.setStep(step());
    try {
      handler.afterPropertiesSet();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return handler;
  }

  @Bean
  public SimpleAsyncTaskExecutor taskExecutor() {
    return new SimpleAsyncTaskExecutor();
  }

  @Bean
  public Step step() {
    log.info("...........Called Patient Step .........");
    return stepBuilderFactory.get("step").<Patient, Patient>chunk(100)
        .reader(patientItemReader(0, 0, null))
        .processor(patientProcessor(null))
        .writer(patientItemWriter()).build();
  }


  @Bean
  @StepScope
  public JdbcPagingItemReader<Patient> patientItemReader(
      @Value("#{stepExecutionContext[startingIndex]}") final int startingIndex,
      @Value("#{stepExecutionContext[endingIndex]}") final int endingIndex,
      @Value("#{stepExecutionContext[name]}") final String name) {
    log.info("Patient Reader start " + endingIndex + " " + endingIndex);
    JdbcPagingItemReader<Patient> reader = new JdbcPagingItemReader<>();
    reader.setDataSource(postgresDataSource);
    reader.setQueryProvider(queryProvider());
    Map<String, Object> parameterValues = new HashMap<>();
    parameterValues.put("startingIndex", startingIndex);
    parameterValues.put("endingIndex", endingIndex);
    log.info("Parameter Value " + name + " " + parameterValues);
    reader.setParameterValues(parameterValues);
    reader.setPageSize(1000);
    reader.setRowMapper(new BeanPropertyRowMapper<Patient>() {{
      setMappedClass(Patient.class);
    }});
    log.info("slaveReader end " + startingIndex + " " + endingIndex);
    return reader;
  }

  @Bean
  public PagingQueryProvider queryProvider() {
    log.info("queryProvider start ");
    SqlPagingQueryProviderFactoryBean provider = new SqlPagingQueryProviderFactoryBean();
    provider.setDataSource(postgresDataSource);
    provider.setSelectClause(
        "SELECT patient_id, source_id, first_name, middle_initial, last_name, email_address, phone_number, street,"
            + "city, state, zip_code, birth_date, social_security_number");

    provider.setFromClause("from patient");
    provider.setWhereClause("where patient_id >= :startingIndex and patient_id <= :endingIndex");
    provider.setSortKey("patient_id");
    log.info("queryProvider end ");
    try {
      return provider.getObject();
    } catch (Exception e) {
      log.info("queryProvider exception ");
      e.printStackTrace();
    }

    return null;
  }

  @Bean
  @StepScope
  public PatientProcessor patientProcessor(@Value("#{stepExecutionContext[name]}") String name) {
    log.info("********called Patient processor **********");
    PatientProcessor userPatientProcessor = new PatientProcessor();
    userPatientProcessor.setThreadName(name);
    return userPatientProcessor;
  }

  @Bean
  @StepScope
  ItemWriter<Patient> patientItemWriter() {
    JdbcBatchItemWriter<Patient> databaseItemWriter = new JdbcBatchItemWriter<>();
    databaseItemWriter.setDataSource(mysqlDataSource);
    databaseItemWriter.setJdbcTemplate(jdbcTemplate);
    databaseItemWriter.setSql(QUERY_INSERT_PATIENTS);
    ItemPreparedStatementSetter<Patient> valueSetter = new PatientPreparedStatementSetter();
    databaseItemWriter.setItemPreparedStatementSetter(valueSetter);
    return databaseItemWriter;
  }

}
