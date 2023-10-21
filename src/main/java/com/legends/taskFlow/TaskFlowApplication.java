package com.legends.taskFlow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskFlowApplication {

    public static void main(String[] args) {
//        System.setProperty("javax.net.ssl.trustStore", "/home/<домашняя директория>/.mysql/YATrustStore");
//        System.setProperty("javax.net.ssl.trustStorePassword", "<пароль хранилища сертификатов>");
        SpringApplication.run(TaskFlowApplication.class, args);
    }

}
