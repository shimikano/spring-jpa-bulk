package com.acme.repository;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.acme.config.ClearDatabase;
import com.acme.config.TestConfiguration;
import com.acme.model.Customer;

/**
 * The base benchmark (test) class.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public final class CustomerRepositoryTest {

  @Rule
  @Inject
  public ClearDatabase clearDatabase;

  @Inject
  private CustomerRepository repository;

  @Test
  public void saveAndFind() {
    final long id = 199;

    Customer entity = new Customer(id);

    repository.save(entity);

    assertThat(repository.findById(id)).isEqualTo(entity);
  }

}
