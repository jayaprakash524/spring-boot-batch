package com.prakash.spring.boot.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;

public class Patient implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long patientId;

  private String sourceId;

  private String firstName;

  private String middleInitial;

  private String lastName;

  private String emailAddress;

  private String phoneNumber;

  private String street;

  private String city;

  private String state;

  private String zipCode;

  private LocalDate birthDate;

  private String socialSecurityNumber;

  public Patient() {

  }

  public Patient(@NotNull String sourceId, @NotNull String firstName, String middleInitial,
      @NotNull String lastName, String emailAddress, @NotNull String phoneNumber, @NotNull String street,
      @NotNull String city, @NotNull String state, @NotNull String zipCode, @NotNull LocalDate birthDate,
      @NotNull String socialSecurityNumber) {
    super();
    this.sourceId = sourceId;
    this.firstName = firstName;
    this.middleInitial = middleInitial;
    this.lastName = lastName;
    this.emailAddress = emailAddress;
    this.phoneNumber = phoneNumber;
    this.street = street;
    this.city = city;
    this.state = state;
    this.zipCode = zipCode;
    this.birthDate = birthDate;
    this.socialSecurityNumber = socialSecurityNumber;
  }

  public Patient(Long id, @NotNull String sourceId, @NotNull String firstName, String middleInitial,
      @NotNull String lastName, String emailAddress, @NotNull String phoneNumber, @NotNull String street,
      @NotNull String city, @NotNull String state, @NotNull String zipCode, @NotNull LocalDate birthDate,
      @NotNull String socialSecurityNumber) {
    super();
    this.patientId = id;
    this.sourceId = sourceId;
    this.firstName = firstName;
    this.middleInitial = middleInitial;
    this.lastName = lastName;
    this.emailAddress = emailAddress;
    this.phoneNumber = phoneNumber;
    this.street = street;
    this.city = city;
    this.state = state;
    this.zipCode = zipCode;
    this.birthDate = birthDate;
    this.socialSecurityNumber = socialSecurityNumber;
  }

  /**
   * @return the id
   */
  public Long getPatientId() {
    return patientId;
  }

  /**
   * @param patientId
   *            the id to set
   */
  public void setPatientId(Long patientId) {
    this.patientId = patientId;
  }

  /**
   * @return the sourceId
   */
  public String getSourceId() {
    return sourceId;
  }

  /**
   * @param sourceId
   *            the sourceId to set
   */
  public void setSourceId(String sourceId) {
    this.sourceId = sourceId;
  }

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @param firstName
   *            the firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * @return the middleInitial
   */
  public String getMiddleInitial() {
    return middleInitial;
  }

  /**
   * @param middleInitial
   *            the middleInitial to set
   */
  public void setMiddleInitial(String middleInitial) {
    this.middleInitial = middleInitial;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @param lastName
   *            the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @return the emailAddress
   */
  public String getEmailAddress() {
    return emailAddress;
  }

  /**
   * @param emailAddress
   *            the emailAddress to set
   */
  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  /**
   * @return the phoneNumber
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * @param phoneNumber
   *            the phoneNumber to set
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   * @return the street
   */
  public String getStreet() {
    return street;
  }

  /**
   * @param street
   *            the street to set
   */
  public void setStreet(String street) {
    this.street = street;
  }

  /**
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * @param city
   *            the city to set
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * @return the state
   */
  public String getState() {
    return state;
  }

  /**
   * @param state
   *            the state to set
   */
  public void setState(String state) {
    this.state = state;
  }

  /**
   * @return the zipCode
   */
  public String getZipCode() {
    return zipCode;
  }

  /**
   * @param zipCode
   *            the zipCode to set
   */
  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  /**
   * @return the birthDate
   */
  public LocalDate getBirthDate() {
    return birthDate;
  }

  /**
   * @param birthDate
   *            the birthDate to set
   */
  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  /**
   * @return the socialSecurityNumber
   */
  public String getSocialSecurityNumber() {
    return socialSecurityNumber;
  }

  /**
   * @param socialSecurityNumber
   *            the socialSecurityNumber to set
   */
  public void setSocialSecurityNumber(String socialSecurityNumber) {
    this.socialSecurityNumber = socialSecurityNumber;
  }

  @Override
  public String toString() {
    return "PatientEntity{" +
        "patientId=" + patientId +
        ", sourceId='" + sourceId + '\'' +
        ", firstName='" + firstName + '\'' +
        ", middleInitial='" + middleInitial + '\'' +
        ", lastName='" + lastName + '\'' +
        ", emailAddress='" + emailAddress + '\'' +
        ", phoneNumber='" + phoneNumber + '\'' +
        ", street='" + street + '\'' +
        ", city='" + city + '\'' +
        ", state='" + state + '\'' +
        ", zipCode='" + zipCode + '\'' +
        ", birthDate=" + birthDate +
        ", socialSecurityNumber='" + socialSecurityNumber + '\'' +
        '}';
  }

}
