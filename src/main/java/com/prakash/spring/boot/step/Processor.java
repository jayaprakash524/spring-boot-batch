package com.prakash.spring.boot.step;

import com.prakash.spring.boot.domain.Patient;
import org.springframework.batch.item.ItemProcessor;

public class Processor implements ItemProcessor<Patient, Patient> {
  @Override
  public Patient process(Patient patient) throws Exception {
    return patient;
  }
}
