package ru.university.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.university.portal.config.RedisConfig;

@SpringBootApplication
@Import(RedisConfig.class)
public class UniversityPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(UniversityPortalApplication.class, args);
    }

}
