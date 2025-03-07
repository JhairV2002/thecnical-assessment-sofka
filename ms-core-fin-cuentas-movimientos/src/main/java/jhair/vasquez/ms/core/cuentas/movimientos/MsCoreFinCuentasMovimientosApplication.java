package jhair.vasquez.ms.core.cuentas.movimientos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class MsCoreFinCuentasMovimientosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsCoreFinCuentasMovimientosApplication.class, args);
    }

}
