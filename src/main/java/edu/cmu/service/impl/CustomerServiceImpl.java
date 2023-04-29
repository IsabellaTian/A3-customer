package edu.cmu.service.impl;

import edu.cmu.model.Customer;
import edu.cmu.repository.CustomerRepository;
import edu.cmu.response.ErrorMessage;
import edu.cmu.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    final
    CustomerRepository customerRepository;
    KafkaTemplate<String, Object> kafkaTemplate;

    public CustomerServiceImpl(CustomerRepository customerRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.customerRepository = customerRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public ResponseEntity<?> addCustomer(Customer customer) {
        if (customerRepository.existsByUserId(customer.getUserId())) {
            return new ResponseEntity<>(new ErrorMessage("This user ID already exists in the system."), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        var newCustomer = customerRepository.save(customer);
        kafkaTemplate.send("yiweitia.customer.evt", customer);
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

    @Override
    public Customer findById(int id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public Customer findByUserId(String userId) {
        return customerRepository.findCustomerByUserId(userId);
    }

    @Override
    public void deleteAll() {
        customerRepository.deleteAll();
    }
}
