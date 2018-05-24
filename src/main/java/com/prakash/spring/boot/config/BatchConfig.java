package com.prakash.spring.boot.config;

import com.prakash.spring.boot.domain.Patient;
import com.prakash.spring.boot.listener.JobCompletionListener;
import com.prakash.spring.boot.step.Processor;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.PostgresPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class BatchConfig {

  @Autowired
  public JobBuilderFactory jobBuilderFactory;

  @Autowired
  public StepBuilderFactory stepBuilderFactory;

  private static final String QUERY_FIND_PATIENTS =
      "SELECT " +
          "patient_id, " +
          "source_id, " +
          "first_name, " +
          "middle_initial, " +
          "last_name, " +
          "email_address, " +
          "phone_number, " +
          "street, " +
          "city, " +
          "state, " +
          "zip_code, " +
          "birth_date, " +
          "social_security_number " +
          "FROM PATIENT " +
          "ORDER BY patient_id ASC";

  private static final String QUERY_INSERT_PATIENTS = "INSERT " +
      "INTO PATIENT(patient_id, source_id, first_name, middle_initial, last_name, email_address, phone_number, street, city, state, zip_code, birth_date,"
      + " social_security_number) " +
      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
  @Bean
  public Job processJob(Step step) {
    return jobBuilderFactory.get("processJob")
        .incrementer(new RunIdIncrementer()).listener(listener())
        .flow(step).end().build();
  }

  @Bean
  public Step step(ItemReader<Patient> itemReader, ItemWriter<Patient> itemWriter) {
    return stepBuilderFactory.get("step").<Patient, Patient> chunk(100)
        .reader(itemReader).processor(new Processor())
        .writer(itemWriter).build();
  }


  @Bean
  ItemReader<Patient> patientItemReader(@Qualifier("postgresDataSource") DataSource dataSource) {
    JdbcPagingItemReader<Patient> databaseReader = new JdbcPagingItemReader<>();
    databaseReader.setDataSource(dataSource);
    databaseReader.setPageSize(1000);
    PagingQueryProvider queryProvider = createQueryProvider();
    databaseReader.setQueryProvider(queryProvider);
    databaseReader.setRowMapper(new BeanPropertyRowMapper<>(Patient.class));
    return databaseReader;
  }

  private PagingQueryProvider createQueryProvider() {
    PostgresPagingQueryProvider queryProvider = new PostgresPagingQueryProvider();
    queryProvider.setSelectClause("SELECT patient_id, source_id, first_name, middle_initial, last_name, email_address, phone_number, street,"
        + "city, state, zip_code, birth_date, social_security_number");
    queryProvider.setFromClause("FROM patient");
    queryProvider.setSortKeys(sortByPatientId());
    return queryProvider;
  }

  private Map<String, Order> sortByPatientId() {
    Map<String, Order> sortConfiguration = new HashMap<>();
    sortConfiguration.put("patient_id", Order.ASCENDING);
    return sortConfiguration;
  }

  @Bean
  ItemWriter<Patient> patientItemWriter(@Qualifier("mysqlDataSource") DataSource dataSource,
      NamedParameterJdbcTemplate jdbcTemplate) {
    JdbcBatchItemWriter<Patient> databaseItemWriter = new JdbcBatchItemWriter<>();
    databaseItemWriter.setDataSource(dataSource);
    databaseItemWriter.setJdbcTemplate(jdbcTemplate);
    databaseItemWriter.setSql(QUERY_INSERT_PATIENTS);
    ItemPreparedStatementSetter<Patient> valueSetter = new PatientPreparedStatementSetter();
    databaseItemWriter.setItemPreparedStatementSetter(valueSetter);
    return databaseItemWriter;
  }

  @Bean
  public JobExecutionListener listener() {
    return new JobCompletionListener();
  }

}
