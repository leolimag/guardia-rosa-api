package br.com.tcc.guardia.rosa.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
           	.allowedOrigins("*") //mudar para capacitor://localhost ou *
            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS",  "HEAD", "TRACE", "CONNECT");
    }
    
}