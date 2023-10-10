package com.example.test.import_module.json;

import aj.org.objectweb.asm.TypeReference;
import com.example.test.model.Client;
import com.example.test.repository.ClientRepository;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class JsonService {
    public static String TYPE = "application/json";

    @Autowired
    ClientRepository repository;
    public void save(MultipartFile file) {
        // read json and write to db
        System.out.println("Client Saved!");
        ObjectMapper mapper = new ObjectMapper();
        JavaType type = mapper.getTypeFactory().
                constructCollectionType(List.class, Client.class);
        System.out.println("Client Saved!");
        try {
            System.out.println("Client Saved!");
            List<Client> clients = mapper.readValue(file.getBytes(),type);
            System.out.println("Client Saved!");
            repository.saveAll(clients);
        } catch (IOException e) {
            throw new RuntimeException("fail to store json data: " + e.getMessage());
        }
    }

    public static boolean hasJsonFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }
            
}
