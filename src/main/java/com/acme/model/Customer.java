package com.acme.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Customer {

  @Id
  private long id; // application assigned

  public Customer(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }

  Customer() {
    // for Hibernate
  }

}
