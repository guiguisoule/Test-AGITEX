package com.example.test.controller;

import com.example.test.import_module.csv.CSVHelper;
import com.example.test.import_module.csv.CSVService;
import com.example.test.import_module.excel.ExcelHelper;
import com.example.test.import_module.excel.ExcelService;
import com.example.test.import_module.json.JsonService;
import com.example.test.model.Client;
import com.example.test.model.MoyenneSalaire;
import com.example.test.model.ResponseMessage;
import com.example.test.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    ClientService clientsService;

    @Autowired
    CSVService csvService;
    @Autowired
    ExcelService excelService;
    @Autowired
    JsonService jsonService;

    //creating a get mapping that retrieves all the books detail from the database   
    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getAllClients() {
        try {
            List<Client> Clients = clientsService.getAllClients();

            if (Clients.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(Clients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/clients-moyenne-salaire-by-profession")
    public ResponseEntity<List<MoyenneSalaire>> getMoyenneSalaireByProfession() {
        try {
            List<MoyenneSalaire> list = clientsService.getAllMoyenneSalaireByProfession();
            if (list.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //creating a get mapping that retrieves the detail of a specific client  
    @GetMapping("/client/{id}")
    private ResponseEntity<Client> getClients(@PathVariable("id") Long clientid)
    {
        try {
            Client client = clientsService.getClientById(clientid);

            if (clientid == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(client, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //creating a delete mapping that deletes a specified client  
    @DeleteMapping("/client/{id}")
    private ResponseEntity<Boolean> deleteClient(@PathVariable("id") Long clientid)
    {
        try {
            if (clientid == null) {
                return new ResponseEntity<>(false, HttpStatus.NO_CONTENT);
            }else {
                clientsService.delete(clientid);
                return new ResponseEntity<>(true, HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //creating post mapping that post the client detail in the database  
    @PostMapping("/clients")
    private ResponseEntity<Client> saveClient(@RequestBody Client client)
    {
        try {
            if (client == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }else {
                Client client_ = clientsService.saveOrUpdate(client);
                return new ResponseEntity<>(client_, HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //creating put mapping that updates the client detail   
    @PutMapping("/clients")
    private ResponseEntity<Client> update(@RequestBody Client client)
    {
        try {
            if (client == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }else {
                Client client_ = clientsService.saveOrUpdate(client);
                return new ResponseEntity<>(client_, HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (CSVHelper.hasCSVFormat(file)) {
            try {
                csvService.save(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the csv file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                excelService.save(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the excel file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        if (JsonService.hasJsonFormat(file)) {
            try {
                System.out.println("Client Saved!");
                jsonService.save(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the json file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Please upload a valid file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }
}
