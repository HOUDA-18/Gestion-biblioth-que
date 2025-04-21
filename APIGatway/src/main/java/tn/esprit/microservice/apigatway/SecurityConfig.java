package tn.esprit.microservice.apigatway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                // désactive CSRF (stateless API)
               // .csrf(ServerHttpSecurity.CsrfSpec::disable)

                // active la validation des JWT via Keycloak
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
                )

                // règles d’autorisation
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/eureka/**", "/actuator/**").permitAll()
                        .anyExchange().authenticated()
                );

        return http.build();
    }
}


