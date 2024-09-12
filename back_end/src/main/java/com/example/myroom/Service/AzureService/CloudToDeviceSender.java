package com.example.myroom.Service.AzureService;

import com.microsoft.azure.sdk.iot.service.IotHubServiceClientProtocol;
import com.microsoft.azure.sdk.iot.service.Message;
import com.microsoft.azure.sdk.iot.service.ServiceClient;
import com.microsoft.azure.sdk.iot.service.exceptions.IotHubException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

@Service
public class CloudToDeviceSender {

    @Value("${azure.connect.string.hub}")
    private String connectString_;

    private static String connectString;

    private static ServiceClient serviceClient;

    @PostConstruct
    private void init() throws IOException {
        connectString = connectString_;
        serviceClient = ServiceClient.createFromConnectionString(connectString, IotHubServiceClientProtocol.AMQPS);
        serviceClient.open();
    }
    @PreDestroy
    private void destroy() throws IOException {
        serviceClient.close();
    }
    public static HashMap<String, Object> sendMessage(String messageContent, String deviceId) throws IOException, IotHubException {
        HashMap<String, Object> result = new HashMap<>();
        Message message = new Message(messageContent);
        serviceClient.send(deviceId, message);
        result.put("error_code", "0");
        return result;
    }
}
