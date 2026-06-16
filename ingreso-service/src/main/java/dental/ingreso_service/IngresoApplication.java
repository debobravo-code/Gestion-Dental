package dental.ingreso_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class IngresoApplication {
    public static void main(String[] args) {
        SpringApplication.run(IngresoApplication.class, args);
    }
}
