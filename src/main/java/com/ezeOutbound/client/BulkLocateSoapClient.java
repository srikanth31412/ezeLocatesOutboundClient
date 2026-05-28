package com.ezeOutbound.client;


import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;

@Service
public class BulkLocateSoapClient extends WebServiceGatewaySupport {

    public String sendBulkLocateRequest() {

        String soapRequest = buildSoapRequest();

        Source requestPayload = new StreamSource(new StringReader(soapRequest));
        StringResult responseResult = new StringResult();

        getWebServiceTemplate().sendSourceAndReceiveToResult(requestPayload, responseResult);

        return responseResult.toString();

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
