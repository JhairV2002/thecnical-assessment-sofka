package ms.core.persona.cliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"ms.core.persona.cliente", "db.repositorio.financiero"})
@EnableJpaRepositories(basePackages = {"db.repositorio.financiero.repository"})
@EntityScan(basePackages = {"db.repositorio.financiero.entity"})
public class MsCorePersonaClienteApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsCorePersonaClienteApplication.class, args);
    }

}
