package dental.Gestion;

import dental.Gestion.model.Egreso;
import dental.Gestion.repository.EgresoRepository;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final EgresoRepository egresoRepository;

    @Override
    public void run(String... args) {

        Faker faker = new Faker();
        Random random = new Random();

        if (egresoRepository.count() > 0) {
            System.out.println("Ya existen egresos en la base de datos. No se insertan datos falsos.");
            return;
        }

        for (int i = 0; i < 10; i++) {
            Egreso egreso = new Egreso();

            egreso.setFecha(LocalDate.now().minusDays(random.nextInt(10)));
            egreso.setHora(LocalTime.now());
            egreso.setCantidad(faker.number().numberBetween(1, 20));
            egreso.setMotivo(faker.options().option(
                    "Atención odontológica",
                    "Reposición de box",
                    "Urgencia dental",
                    "Entrega a programa dental"
            ));
            egreso.setObservacion(faker.options().option(
                    "Entrega de insumos para box dental",
                    "Salida de insumo para atención clínica",
                    "Reposición de material odontológico",
                    "Egreso registrado por bodega"
            ));

            egreso.setInsumoId(faker.number().numberBetween(1, 3));
            egreso.setDestinoId(faker.number().numberBetween(1, 3));

            egresoRepository.save(egreso);
        }

        System.out.println("Se insertaron 10 egresos falsos con DataFaker.");
    }
}