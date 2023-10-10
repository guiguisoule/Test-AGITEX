package com.example.test.import_module.excel;
import java.io.IOException;
import java.util.List;

import com.example.test.model.Client;
import com.example.test.repository.ClientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ExcelService {
    @Autowired
    ClientRepository repository;

    public void save(MultipartFile file) {
        try {
            List<Client> Clients = ExcelHelper.excelToClients(file.getInputStream());
            repository.saveAll(Clients);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

//    public List<Client> getAllClients() {
//        return repository.findAll();
//    }
}
