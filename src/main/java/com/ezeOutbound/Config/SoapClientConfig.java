package com.ezeOutbound.Config;

import com.ezeOutbound.client.BulkLocateSoapClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.client.core.WebServiceTemplate;

@Configuration
public class SoapClientConfig {

    @Value("${bulk.locate.soap.url}")
    private String bulkLocateSoapUrl;

    @Bean
    public WebServiceTemplate webServiceTemplate() {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setDefaultUri(bulkLocateSoapUrl);
        return webServiceTemplate;
    }

    @Bean
    public BulkLocateSoapClient bulkLocateSoapClient(WebServiceTemplate webServiceTemplate) {
        BulkLocateSoapClient client = new BulkLocateSoapClient();
        client.setWebServiceTemplate(webServiceTemplate);
        client.setDefaultUri(bulkLocateSoapUrl);
        return client;
    }
}
