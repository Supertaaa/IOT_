//package com.example.myroom;
//import java.io.IOException;
//
//
//import com.example.myroom.Service.AzureService.CloudToDeviceSender;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class MyRoomApplication implements CommandLineRunner {
//	public static void main(String[] args) {
//		SpringApplication.run(MyRoomApplication.class, args);
//
//	}
//
//	@Override
//	public void run(String... args) throws Exception {
//		System.out.println(CloudToDeviceSender.sendMessage("TUAN ANH DEP TRAI", "Fan"));
//	}
//}


package com.example.myroom;


import com.azure.core.annotation.ServiceClient;
import com.azure.core.annotation.ServiceClientBuilder;
import com.microsoft.azure.sdk.iot.service.messaging.IotHubServiceClientProtocol;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.cert.X509Certificate;

public class MyRoomApplication {
	// Thay đổi các biến này với thông tin của bạn
	private static final String connectionString = "HostName=MarHeaven.azure-devices.net;SharedAccessKeyName=iothubowner;SharedAccessKey=JOjVRx/wXjknqM5RLnd3/muKW9eY8W1ZuAIoTO1gVaE=";
	private static final String deviceId = "Fan";

	public static void main(String[] args) throws Exception {

		// Tạo một client để kết nối với IoT Hub



		TrustManager[] trustAll = new TrustManager[] {
				new X509TrustManager() {
					public X509Certificate[] getAcceptedIssuers() { return null; }
					public void checkClientTrusted(X509Certificate[] certs, String authType) {}
					public void checkServerTrusted(X509Certificate[] certs, String authType) {}
				}
		};

		// Tạo một SSLContext với TrustManager không thực hiện xác thực
		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(null, trustAll, new java.security.SecureRandom());



		//ServiceClient serviceClient = ServiceClient.createFromConnectionString(connectionString, IotHubServiceClientProtocol.AMQPS_WS);




		ServiceClient serviceClient = new ServiceClientBuilder()
				.connectionString(connectionString)
				.protocol(IotHubServiceClientProtocol.AMQPS)
				.buildClient();

		serviceClient.builder().se




		// Mở kết nối
		serviceClient.open();


		// Tạo một tin nhắn mới


		Message message = new Message("Hello from cloud!");

		// Gửi tin nhắn đến thiết bị

		serviceClient.send(deviceId, message);

		// Đóng kết nối
		serviceClient.close();

		System.out.println("Message sent to device: " + deviceId);
	}




}
