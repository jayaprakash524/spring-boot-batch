package com.prakash.spring.boot.config;

import com.prakash.spring.boot.domain.Patient;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;

public final class PatientPreparedStatementSetter implements ItemPreparedStatementSetter<Patient> {

  @Override
  public void setValues(Patient patient,
      PreparedStatement preparedStatement) throws SQLException {
    preparedStatement.setLong(1, patient.getPatientId());
    preparedStatement.setString(2, patient.getSourceId());
    preparedStatement.setString(3, patient.getFirstName());
    preparedStatement.setString(4, patient.getMiddleInitial());
    preparedStatement.setString(5, patient.getLastName());
    preparedStatement.setString(6, patient.getEmailAddress());
    preparedStatement.setString(7, patient.getPhoneNumber());
    preparedStatement.setString(8, patient.getStreet());
    preparedStatement.setString(9, patient.getCity());
    preparedStatement.setString(10, patient.getState());
    preparedStatement.setString(11, patient.getZipCode());
    preparedStatement.setDate(12, Date.valueOf(patient.getBirthDate()));
    preparedStatement.setString(13, patient.getSocialSecurityNumber());
  }
}

