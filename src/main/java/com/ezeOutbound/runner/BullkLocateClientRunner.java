package com.ezeOutbound.runner;

import com.ezeOutbound.client.BulkLocateSoapClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BullkLocateClientRunner implements CommandLineRunner {

    private final BulkLocateSoapClient bulkLocateSoapClient;

    public BullkLocateClientRunner(BulkLocateSoapClient bulkLocateSoapClient) {
        this.bulkLocateSoapClient = bulkLocateSoapClient;
    }

    @Override
    public void run(String... args) throws Exception {

        try{
            System.out.println("Calling Bulk Locate SOAP Client...");
            String response = bulkLocateSoapClient.sendBulkLocateRequest();
            System.out.println("SOAP response: " + response);
        }
        catch (Exception e) {
            System.err.println("Error calling Bulk Locate SOAP Client: " + e.getMessage());
        }
    }
}
