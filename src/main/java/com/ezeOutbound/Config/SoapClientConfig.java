package com.ezeOutbound.Config;

import com.ezeOutbound.client.BulkLocateSoapClient;
import jakarta.xml.soap.MessageFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.transport.http.MessageDispatcherServlet;

@Configuration
@EnableWs
public class SoapClientConfig {

    private String username = "ezelocates-beta";

    private String password = "ezeLocates123#!";

    private String endpoint = "https://apibeta.cantorps.com/ezelocates";

    @Bean
    public SaajSoapMessageFactory messageFactory() throws Exception {
        SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory();
        messageFactory.setMessageFactory(MessageFactory.newInstance());
        messageFactory.afterPropertiesSet();
        return messageFactory;
    }

    @Bean
    public WebServiceMessageSenderWithAuth webServiceMessageSenderWithAuth() {
        return new WebServiceMessageSenderWithAuth(username, password);
    }
    @Bean
    public WebServiceTemplate webServiceTemplate(SaajSoapMessageFactory messageFactory, WebServiceMessageSenderWithAuth webServiceMessageSenderWithAuth) {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMessageFactory(messageFactory);
        webServiceTemplate.setMessageSender(webServiceMessageSenderWithAuth);
        webServiceTemplate.setDefaultUri(endpoint);
        return webServiceTemplate;
    }

    @Bean
    public BulkLocateSoapClient bulkLocateSoapClient(WebServiceTemplate webServiceTemplate) {
        BulkLocateSoapClient client = new BulkLocateSoapClient();
        client.setWebServiceTemplate(webServiceTemplate);
        client.setDefaultUri("https://apibeta.cantorps.com/ezelocates");
        return client;
    }
}
