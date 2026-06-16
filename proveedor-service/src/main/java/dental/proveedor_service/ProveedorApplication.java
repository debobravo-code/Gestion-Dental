package dental.proveedor_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProveedorApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProveedorApplication.class, args);
    }
}
