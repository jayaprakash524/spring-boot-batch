package com.prakash.spring.boot.config;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class DataSourceConfig {

  @Bean(name = "mysqlDataSource")
  public DataSource mysqlDataSource() {
    HikariDataSource hikariDataSource = new HikariDataSource();
    hikariDataSource.setDriverClassName("com.mysql.jdbc.Driver");
    hikariDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/stark");
    hikariDataSource.setUsername("root");
    hikariDataSource.setPassword("password");
    return hikariDataSource;
  }

  @Bean(name = "postgresDataSource")
  @Primary
  public DataSource postgresDataSource() {
    HikariDataSource hikariDataSource = new HikariDataSource();
    hikariDataSource.setDriverClassName("org.postgresql.Driver");
    hikariDataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/scdf");
    hikariDataSource.setUsername("postgres");
    hikariDataSource.setPassword("password");
    return hikariDataSource;
  }

  @Bean(name = "postgresJdbcTemplate")
  @Autowired
  public JdbcTemplate postgresJdbcTemplate(@Qualifier("postgresDataSource") DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

  @Bean
  NamedParameterJdbcTemplate jdbcTemplate(@Qualifier("mysqlDataSource") DataSource dataSource) {
    return new NamedParameterJdbcTemplate(dataSource);
  }

}

