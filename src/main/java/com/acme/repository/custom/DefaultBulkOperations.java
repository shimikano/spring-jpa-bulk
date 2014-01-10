package com.acme.repository.custom;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;

import com.acme.model.Customer;

public final class DefaultBulkOperations implements BulkOperations {

  private final EntityManager em;

  public DefaultBulkOperations(EntityManager em) {
    this.em = em;
  }

  @Override
  @Transactional
  public void bulkPersist(List<Customer> entities) {
    for (Customer entity : entities) {
      em.persist(entity);
    }
  }

}
