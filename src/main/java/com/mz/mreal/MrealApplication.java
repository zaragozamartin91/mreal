package com.mz.mreal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MrealApplication extends SpringBootServletInitializer {
    /* Sobrecargar 'configure' es necesario para que la app funcione como un jar ejecutable y un war deployable */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MrealApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(MrealApplication.class, args);
    }
}
