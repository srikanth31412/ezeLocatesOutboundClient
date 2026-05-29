package com.ezeOutbound.Config;

import org.springframework.ws.transport.http.HttpUrlConnection;
import org.springframework.ws.transport.http.HttpUrlConnectionMessageSender;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class WebServiceMessageSenderWithAuth extends HttpUrlConnectionMessageSender {

    private final String username;
    private final String password;

    public WebServiceMessageSenderWithAuth(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    protected void prepareConnection(HttpURLConnection connection) throws IOException {
        String userNamePassword = username + ":" + password;
        String encodedAuth = java.util.Base64.getEncoder().encodeToString(userNamePassword.getBytes(StandardCharsets.UTF_8));
        connection.setRequestProperty("Authorization", "Basic " + encodedAuth);
        connection.setRequestProperty("Accept", "text/xml, application/xml, */*");
        super.prepareConnection(connection);
    }   // For demonstration, hardcoding credentials. In production, use secure configuration.
}
