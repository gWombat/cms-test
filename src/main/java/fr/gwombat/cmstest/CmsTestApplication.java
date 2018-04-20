package fr.gwombat.cmstest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("fr.gwombat.cmstest.domain")
public class CmsTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsTestApplication.class, args);
    }
}
