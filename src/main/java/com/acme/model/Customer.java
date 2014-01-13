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

  @Override
  public int hashCode() {
    return (int) (id ^ (id >>> 32));
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Customer)) {
      return false;
    }

    return ((Customer) obj).id == id;
  }

  @Override
  public String toString() {
    return "Customer #" + id;
  }

  Customer() {
    // for Hibernate
  }

}
