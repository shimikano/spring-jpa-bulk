package com.acme.repository.custom;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.StatelessSession;
import org.springframework.transaction.annotation.Transactional;

import com.acme.model.Customer;

public final class StatelessSessionBulkOperations implements BulkOperations {

  private final StatelessSession statelessSession;

  public StatelessSessionBulkOperations(EntityManager em, StatelessSession statelessSession) {
    this.statelessSession = statelessSession;
  }

  @Override
  @Transactional
  public void bulkPersist(List<Customer> entities) {
    for (Customer entity : entities) {
      statelessSession.insert(entity);
    }
  }

}
