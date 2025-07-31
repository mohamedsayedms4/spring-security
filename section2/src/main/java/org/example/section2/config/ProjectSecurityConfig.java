package org.example.section2.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

//        http.authorizeHttpRequests((authorizeRequests) ->
//                authorizeRequests.anyRequest().denyAll());

        http.authorizeHttpRequests(authorizeRequests ->{
            authorizeRequests.requestMatchers
                    ("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated()
                    .requestMatchers("/notices", "/contact", "/error").permitAll();
        });
        http.formLogin(form -> form.disable());
        http.httpBasic(Customizer.withDefaults());



        return http.build();
    }
}
