package com.ayazen.AyaZen.service;

import  com.ayazen.AyaZen.entity.AyazenCustomer;
import com.ayazen.AyaZen.entity.AyazenProject;
import com.ayazen.AyaZen.repository.AyazenCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class AyazenCustomerServiceImpl implements AyazenCustomerService {

    @Autowired
    private AyazenCustomerRepository customerRepository;

    @Override
    public List<AyazenCustomer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public AyazenCustomer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @Override
    public AyazenCustomer createCustomer(AyazenCustomer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public AyazenCustomer updateCustomer(Long id, AyazenCustomer customer) {
        if (!customerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
        }
        customer.setId(id);
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
        }
        customerRepository.deleteById(id);
    }

    @Override
    public List<AyazenProject> getProjectsByCustomerId(Long customerId) {
        AyazenCustomer customer = getCustomerById(customerId);
        return customer.getProjects();
    }

}
