package org.example.section4.Repository;

import org.example.section4.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository  extends CrudRepository<Customer,Long> {

    Optional<Customer> findByEmail(String email);

}
