package com.prakash.spring.boot.step;

import com.prakash.spring.boot.domain.Patient;
import java.util.List;
import org.springframework.batch.item.ItemWriter;

public class Writer implements ItemWriter<Patient> {
  @Override
  public void write(List<? extends Patient> patients) throws Exception {
    for (Patient patient : patients) {
      System.out.println("Writing the data " + patient.toString());
    }
  }
}
