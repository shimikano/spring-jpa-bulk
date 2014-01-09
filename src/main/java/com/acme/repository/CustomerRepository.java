package com.acme.repository;

import org.springframework.data.repository.CrudRepository;

import com.acme.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

  public void bulkPersist(Iterable<Customer> entities);

}
