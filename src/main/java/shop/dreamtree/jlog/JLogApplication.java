package shop.dreamtree.jlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(JLogApplication.class, args);
    }

}
