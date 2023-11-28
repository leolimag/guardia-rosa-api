package br.com.tcc.guardia.rosa.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
           	.allowedOrigins("http://localhost:8100", "http://localhost:4200", "http://localhost:8080", "capacitor://localhost", "http://localhost", "ionic://localhost") //mudar para capacitor://localhost ou *
            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS",  "HEAD", "TRACE", "CONNECT");
    }
    
}