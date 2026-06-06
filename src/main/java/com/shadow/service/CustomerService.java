package com.shadow.service;

import com.shadow.model.Customer;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer);

    Customer updateCustomer(Long id, Customer customer);

    void deleteCustomer(Long id);

    Customer getCustomer(Long id);

    List<Customer> getAllCustomers();

    List<Customer> searchCustomers(String keyword);
}