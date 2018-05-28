package com.prakash.spring.boot.config;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
public class SamplePartitioner implements Partitioner {

  private JdbcTemplate jdbcTemplate;
  private String table;
  private String column;

  public SamplePartitioner(JdbcTemplate jdbcTemplate, String table, String column) {
    this.jdbcTemplate = jdbcTemplate;
    this.table = table;
    this.column = column;
  }

  @Override
  public Map<String, ExecutionContext> partition(int gridSize) {
    int min = jdbcTemplate
        .queryForObject("SELECT MIN(" + column + ") from " + table, Integer.class);
    int max = jdbcTemplate
        .queryForObject("SELECT MAX(" + column + ") from " + table, Integer.class);
    int targetSize = (max - min) / gridSize + 1;
    Map<String, ExecutionContext> result = new HashMap<String, ExecutionContext>();
    int number = 0;
    int start = min;
    int end = start + targetSize - 1;
    while (start <= max) {
      log.info("Starting : Thread" + number);
      ExecutionContext executionContext = new ExecutionContext();
      if (end >= max) {
        end = max;
      }
      executionContext.putInt("startingIndex", start);
      executionContext.putInt("endingIndex", end);
      executionContext.putString("name", "Thread" + number);
      result.put("partition" + number, executionContext);
      start += targetSize;
      end += targetSize;
      number++;
    }
    log.info("END: Created Partitions of size: "+ result.size());
    return result;
  }
}
