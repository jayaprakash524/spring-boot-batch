package com.prakash.spring.boot.step;

import com.prakash.spring.boot.domain.Patient;
import org.springframework.batch.item.ItemProcessor;

public class PatientProcessor implements ItemProcessor<Patient, Patient> {
  private String threadName;

  public String getThreadName() {
    return threadName;
  }

  public void setThreadName(String threadName) {
    this.threadName = threadName;
  }

  @Override
  public Patient process(Patient patient) throws Exception {
    System.out.println(threadName + " processing : " + patient.getPatientId());
    return patient;
  }
}
