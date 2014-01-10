package com.acme.repository;

import org.springframework.data.repository.CrudRepository;

import com.acme.model.Customer;
import com.acme.repository.custom.BulkOperations;

public interface CustomerRepository extends CrudRepository<Customer, Long>, BulkOperations {
  // empty
}
