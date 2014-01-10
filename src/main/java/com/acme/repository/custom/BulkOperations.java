package com.acme.repository.custom;

import java.util.List;

import com.acme.model.Customer;

public interface BulkOperations {

  public void bulkPersist(List<Customer> entities);

}
