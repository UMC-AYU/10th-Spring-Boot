package org.example.swaggerpr.global.config;

import org.h2.server.web.JakartaWebServlet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnProperty(prefix = "spring.h2.console", name = "enabled", havingValue = "true")
public class H2ConsoleConfig implements WebMvcConfigurer {

    @Bean
    public ServletRegistrationBean<JakartaWebServlet> h2ConsoleServletRegistration() {
        ServletRegistrationBean<JakartaWebServlet> registration =
                new ServletRegistrationBean<>(new JakartaWebServlet(), "/h2-console/*");
        registration.setName("H2Console");
        return registration;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/h2-console", "/h2-console/");
    }
}
