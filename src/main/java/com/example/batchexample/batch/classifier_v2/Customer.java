package com.example.batchexample.batch.classifier_v2;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer {
  private String name;
  private String city;
  private int addressNumber;

  public Customer(String name, String city, int addressNumber) {
    this.name = name;
    this.city = city;
    this.addressNumber = addressNumber;
  }

  @Override
  public String toString() {
    return "Customer{" +
      "name='" + name + '\'' +
      ", city='" + city + '\'' +
      ", addressNumber=" + addressNumber +
      '}';
  }
}
