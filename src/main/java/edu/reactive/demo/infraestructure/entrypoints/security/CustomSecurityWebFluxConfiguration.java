package edu.reactive.demo.infraestructure.entrypoints.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class CustomSecurityWebFluxConfiguration {

    private final ReactiveJwtManager reactiveJwtManagerDecoder;

    @Bean
    public SecurityWebFilterChain springSecurityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity.authorizeExchange(authorize -> authorize
                        .pathMatchers("/admin/**").hasRole("ADMIN")
                        .pathMatchers("/auth/**").permitAll()
                        .pathMatchers("/student/**").hasRole("USER"))
                .httpBasic(Customizer.withDefaults())
                .csrf(ServerHttpSecurity.CsrfSpec::disable);

        serverHttpSecurity.authorizeExchange(authorize -> authorize
                        .pathMatchers("/studentV2/**").authenticated())
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwtConfiguration -> jwtConfiguration.jwtDecoder(reactiveJwtManagerDecoder)))
                .httpBasic(Customizer.withDefaults())
                .csrf(ServerHttpSecurity.CsrfSpec::disable);


        return serverHttpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("lkX3423")
                .roles("USER", "ADMIN")
                .build();

        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("kgflgj3")
                .roles("USER")
                .build();

        return new MapReactiveUserDetailsService(admin, user);
    }

}
