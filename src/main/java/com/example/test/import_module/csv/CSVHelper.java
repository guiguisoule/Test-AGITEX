package com.example.test.import_module.csv;

import com.example.test.model.Client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;
public class CSVHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERs = { "id", "nom", "prenom", "age", "profession", "salaire" };

    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<Client> csvToClients(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Client> Clients = new ArrayList<Client>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Client Client = new Client(
                        Long.parseLong(csvRecord.get("id")),
                        csvRecord.get("nom"),
                        csvRecord.get("prenom"),
                        Long.parseLong(csvRecord.get("age")),
                        csvRecord.get("profession"),
                        Long.parseLong(csvRecord.get("salaire"))
                );

                Clients.add(Client);
            }

            return Clients;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

}
