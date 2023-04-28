package edu.cmu.repository;

import edu.cmu.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    boolean existsByUserId(String userId);

    Customer findCustomerByUserId(String userId);
}
