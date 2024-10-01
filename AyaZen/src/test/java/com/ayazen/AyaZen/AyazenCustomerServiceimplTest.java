package com.ayazen.AyaZen;

import com.ayazen.AyaZen.entity.AyazenCustomer;
import com.ayazen.AyaZen.entity.AyazenProject;
import com.ayazen.AyaZen.repository.AyazenCustomerRepository;
import com.ayazen.AyaZen.service.AyazenCustomerServiceImpl;
import com.ayazen.AyaZen.service.AyazenTaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AyazenCustomerServiceimplTest {

    @Mock
    private AyazenCustomerRepository customerRepository;

    @Mock
    private AyazenTaskService taskService;

    @InjectMocks
    private AyazenCustomerServiceImpl customerService;

    @Test
    public void testGetAllCustomers() {
        List<AyazenCustomer> customers = new ArrayList<>();
        customers.add(new AyazenCustomer());
        customers.add(new AyazenCustomer());
        when(customerRepository.findAll()).thenReturn(customers);

        List<AyazenCustomer> result = customerService.getAllCustomers();

        assertEquals(2, result.size());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void testGetCustomerById_ExistingCustomer() {
        AyazenCustomer customer = new AyazenCustomer();
        customer.setId(1L);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        AyazenCustomer result = customerService.getCustomerById(1L);

        assertEquals(1L, result.getId());
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetCustomerById_NonExistingCustomer() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> customerService.getCustomerById(1L));
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateCustomer() {
        AyazenCustomer customer = new AyazenCustomer();
        customer.setName("Novo Cliente");
        when(customerRepository.save(any(AyazenCustomer.class))).thenReturn(customer);

        AyazenCustomer result = customerService.createCustomer(customer);

        assertEquals("Novo Cliente", result.getName());
        verify(customerRepository, times(1)).save(customer);
    }


    @Test
    public void testUpdateCustomer_NonExistingCustomer() {
        AyazenCustomer updatedCustomer = new AyazenCustomer();
        updatedCustomer.setName("Cliente Atualizado");
        when(customerRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> customerService.updateCustomer(1L, updatedCustomer));
        verify(customerRepository, times(1)).existsById(1L);
        verify(customerRepository, never()).save(any());
    }

    @Test
    public void testDeleteCustomer_ExistingCustomer() {
        when(customerRepository.existsById(1L)).thenReturn(true);
        doNothing().when(customerRepository).deleteById(1L);

        customerService.deleteCustomer(1L);

        verify(customerRepository, times(1)).existsById(1L);
        verify(customerRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteCustomer_NonExistingCustomer() {
        when(customerRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> customerService.deleteCustomer(1L));
        verify(customerRepository, times(1)).existsById(1L);
        verify(customerRepository, never()).deleteById(any());
    }

    @Test
    public void testGetProjectsByCustomerId() {
        AyazenCustomer customer = new AyazenCustomer();
        customer.setId(1L);
        List<AyazenProject> projects = new ArrayList<>();
        projects.add(new AyazenProject());
        projects.add(new AyazenProject());
        customer.setProjects(projects);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        List<AyazenProject> result = customerService.getProjectsByCustomerId(1L);

        assertEquals(2, result.size());
        verify(customerRepository, times(1)).findById(1L);
    }
}
