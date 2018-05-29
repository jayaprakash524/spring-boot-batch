package com.prakash.spring.boot.listener;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepListenerSupport;
import org.springframework.batch.core.scope.context.ChunkContext;

@Slf4j
public class LogStepListener<T, S> extends StepListenerSupport<T, S> {

  private StepExecution stepExecution;
  private Integer commitInterval;

  @Override
  public void beforeStep(StepExecution pStepExecution) {
    this.stepExecution = pStepExecution;
  }

  @Override
  public void beforeChunk(ChunkContext chunkContext) {
    if (commitInterval != null) {
      log.trace("Step {} - Reading next {} items", stepExecution.getStepName(), commitInterval);
    }
  }

  @Override
  public void beforeWrite(List<? extends S> items) {
    log.trace("Step {} - Writing {} items", stepExecution.getStepName(), items.size());
  }

  @Override
  public void afterWrite(List<? extends S> items) {
    if ((items != null) && !items.isEmpty()) {
      log.trace("Step {} - {} items writed", stepExecution.getStepName(), items.size());
    }
  }

  @Override
  public ExitStatus afterStep(StepExecution pStepExecution) {
    if (log.isInfoEnabled()) {
      StringBuilder msg = new StringBuilder();
      msg.append("Step ").append(pStepExecution.getStepName());
      msg.append(" - Read count: ").append(pStepExecution.getReadCount());
      msg.append(" - Write count: ").append(pStepExecution.getWriteCount());
      msg.append(" - Commit count: ").append(pStepExecution.getCommitCount());
      log.info(msg.toString());
    }
    return super.afterStep(pStepExecution);
  }

  /**
   * Sets the chunk commit interval
   *
   * @param commitInterval chunk commit interval (may be <code>null</code>)
   */
  public void setCommitInterval(Integer commitInterval) {
    this.commitInterval = commitInterval;
  }
}
