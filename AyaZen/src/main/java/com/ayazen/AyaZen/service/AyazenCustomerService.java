package com.ayazen.AyaZen.service;

import com.ayazen.AyaZen.entity.AyazenCustomer;
import com.ayazen.AyaZen.entity.AyazenProject;

import java.util.List;

public interface AyazenCustomerService {

    List<AyazenCustomer> getAllCustomers();

    AyazenCustomer getCustomerById(Long id);

    AyazenCustomer createCustomer(AyazenCustomer customer);

    AyazenCustomer updateCustomer(Long id, AyazenCustomer customer);

    void deleteCustomer(Long id);

    List<AyazenProject> getProjectsByCustomerId(Long customerId);

}
