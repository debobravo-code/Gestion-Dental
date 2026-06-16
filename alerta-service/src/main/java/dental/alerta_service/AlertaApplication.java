package dental.alerta_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AlertaApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlertaApplication.class, args);
    }
}
