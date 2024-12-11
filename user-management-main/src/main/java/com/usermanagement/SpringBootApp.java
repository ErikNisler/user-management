package com.usermanagement;

import com.usermanagement.ui.App;
import com.usermanagement.ui.Login;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.usermanagement")
@EnableJpaRepositories("com.usermanagement")
@ComponentScan(basePackages = "com.usermanagement")
public class SpringBootApp implements CommandLineRunner {

    private final App app;

    public SpringBootApp(App app) {
        this.app = app;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        app.start();
        System.exit(0);
    }
}
