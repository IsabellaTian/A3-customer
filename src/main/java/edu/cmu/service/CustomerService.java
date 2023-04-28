package edu.cmu.service;

import edu.cmu.model.Customer;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    ResponseEntity<?> addCustomer(Customer customer);

    Customer findById(int id);

    Customer findByUserId(String userId);

    void deleteAll();
}
