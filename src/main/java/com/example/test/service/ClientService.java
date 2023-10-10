package com.example.test.service;

import com.example.test.model.Client;
import com.example.test.model.MoyenneSalaire;
import com.example.test.repository.ClientRepository;
import com.example.test.repository.SalaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    ClientRepository repository;

    @Autowired
    SalaireRepository salaireRepository;

    public List<Client> getAllClients() {
        return repository.findAll();
    }
    
    //getting a specific record by using the method findById() of CrudRepository  
    public Client getClientById(long id)
    {
        return repository.findById(id).get();
    }
    //saving a specific record by using the method save() of CrudRepository  
    public Client saveOrUpdate(Client clients)
    {
       return repository.save(clients);
    }
    //deleting a specific record by using the method deleteById() of CrudRepository  
    public void delete(long id)
    {
        repository.deleteById(id);
    }
    //updating a record  
    public Client update(Client clients)
    {
        return repository.save(clients);
    }

    public List<MoyenneSalaire> getAllMoyenneSalaireByProfession() {
        return salaireRepository.findMoyenneSalaireByProfession();
    }

}
