package com.shadow.service.impl;

import com.shadow.exception.ResourceNotFoundException;
import com.shadow.model.Customer;
import com.shadow.repository.CustomerRepository;
import com.shadow.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        Customer existing = customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer", id)
        );

        existing.setFullName(customer.getFullName());
        existing.setEmail(customer.getEmail());
        existing.setPhone(customer.getPhone());

        return customerRepository.save(existing);
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer existing = customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer", id)
        );
        customerRepository.delete(existing);
    }

    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer", id)
        );
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> searchCustomers(String keyword) {
        return customerRepository.findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                keyword, keyword
        );
    }
}
