package com.ezeOutbound.client;


import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.WebServiceTransportException;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.client.SoapFaultClientException;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

@Service
public class BulkLocateSoapClient extends WebServiceGatewaySupport {

    private static final String ENDPOINT = "https://apibeta.cantorps.com/ezelocates";

    private static final String SOAP_ACTION = "https://apibeta.cantorps.com/ezelocates";

    private static final String REQUEST_XML_FILE = "BulkLocateRequest.xml";


    public void sendBulkLocateRequest() {

        try{
            String soapRequest = loadXmlFromClassPath(REQUEST_XML_FILE);

            WebServiceTemplate template = getWebServiceTemplate();

            WebServiceMessage responseMessage = template.sendAndReceive(ENDPOINT,
                    requestMessage -> {
                        try {
                            replaceSoapMessageWithRawEnvelope(requestMessage, soapRequest);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        SoapMessage soapMessage = (SoapMessage) requestMessage;
                        soapMessage.setSoapAction(SOAP_ACTION);
                    },
                    response -> response
            );
            if(responseMessage != null){
                 String responseXml = convertResponseToString(responseMessage);
            }
        }
        catch (SoapFaultClientException e){
           System.out.println("Fault reason:"+ e.getFaultStringOrReason());
           e.printStackTrace();

        }
        catch (WebServiceTransportException e){
            System.out.println("Message:"+ e.getMessage());
            e.printStackTrace();
        }
        catch (Exception e){
            System.out.println("Error sending SOAP request: " + e.getMessage());
            e.printStackTrace();
        }

    }

    private String loadXmlFromClassPath(String fileName) throws Exception {
        ClassPathResource resource = new ClassPathResource(fileName);
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    private void replaceSoapMessageWithRawEnvelope(WebServiceMessage message, String rawEnvelope) throws Exception {
        if(!(message instanceof SaajSoapMessage)) {
            throw new IllegalArgumentException("Expected a SaajSoapMessage");
        }

        SaajSoapMessage saajSoapMessage = (SaajSoapMessage) message;
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage(null, new ByteArrayInputStream(rawEnvelope.getBytes(StandardCharsets.UTF_8)));
        saajSoapMessage.setSaajMessage(soapMessage);
    }

    private String convertResponseToString(WebServiceMessage responseMessage) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        responseMessage.writeTo(outputStream);
        return new String(outputStream.toByteArray(), StandardCharsets.UTF_8);
    }

    private String buildSoapRequest() {
        // Build your SOAP request XML here
        return  "<tns:BulkLocateRequest xmlns:tns=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<tns:BulkLocateID>{894E6730-6F19-4001-AS38-E7FF6C141563}</tns:BulkLocateID>"
                + "<tns:BulkNumber>2</tns:BulkNumber>"
                + "<tns:SiteID>{EF021782-0c64-4CF8-B043-78EA8122AC1D}</tns:SiteID>"
                + "<tns:ReplyURL>https://locateuat.ezecastlesoftare.com/locatehub.locateservice/locateservice.svc/REST/ProcessResponsesXml</tns:ReplyURL>"
                + "<tns:LocateRequest>"
                + "<tns:LocateID>{9FFF51CB2-5EB7-5658-USPB1-F4B651FFF8D3E}</tns:LocateID>"
                + "<tns:SecurityID>AAPL US</tns:SecurityID>"
                + "<tns:SecurityType>SYMBOL</tns:SecurityType>"
                + "<tns:Symbol>AAPL US</tns:Symbol>"
                + "<tns:Amount>20</tns:Amount>"
                + "<tns:SettlementDate>12/29/2022 12:00:00 AM</tns:SettlementDate>"
                + "<tns:TimeOut>12/27/2022 12:00:00 AM</tns:TimeOut>"
                + "<tns:Trader>OPS</tns:Trader>"
                + "<tns:Message>Test locate request</tns:Message>"
                + "<tns:OnBehalfOf></tns:OnBehalfOf>"
                + "</tns:LocateRequest>"
                + "</tns:BulkLocateRequest>";
    }

    private String buildBulkLocateRequestPayload() {
        // Build your SOAP request XML here
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soapenv:Envelope "
                + "xmmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
                + "xmlns:tns=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "soapenv:Header/>"
                + "<soapenv:Body>"
                + "<tns:BulkLocateRequest>"
                + "<tns:BulkLocateID>{894E6730-6F19-4001-AS38-E7FF6C141563}</tns:BulkLocateID>"
                + "<tns:BulkNumber>2</tns:BulkNumber>"
                + "tns:SiteID>{EF021782-0c64-4CF8-B043-78EA8122AC1D}</tns:SiteID>"
                + "<tns:ReplyURL>https://locateuat.ezecastlesoftare.com/locatehub.locateservice/locateservice.svc/REST/ProcessResponsesXml/tns:ReplyURL>"
                + "<tns:LocateRequest>"
                + "<tns:LocateID>{9FFF51CB2-5EB7-5658-USPB1-F4B651FFF8D3E}</tns:LocateID>"
                + "<tns:SecurityID>AAPL US</tns:SecurityID>"
                + "<tns:SecurityType>SYMBOL</tns:SecurityType>"
                + "<tns:Symbol>AAPL US</tns:Symbol>"
                + "<tns:Amount>20</tns:Amount>"
                + "<tns:SettlementDate>12/29/2022 12:00:00 AM</tns:SettlementDate>"
                + "<tns:TimeOut>12/27/2022 12:00:00 AM</tns:TimeOut>"
                + "<tns:Trader>OPS</tns:Trader>"
                + "<tns:Message>Test locate request</tns:Message>"
                + "<tns:OnBehalfOf></tns:OnBehalfOf>"
                + "</tns:LocateRequest>"
                + "</tns:BulkLocateRequest>"
                + "</soapenv:Body>"
                + "</soapenv:Envelope>";
    }

    private static class StringResult extends StreamResult {

        private final StringWriter writer = new StringWriter();

        public StringResult(){
            super();
            setWriter(writer);
        }

        @Override
        public String toString() {
            return writer.toString();
        }
    }
}
