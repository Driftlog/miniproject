package sg.edu.nus.iss.miniproject.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserAuthProvider userAuthProvider;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(new JwtAuthFilter(userAuthProvider), BasicAuthenticationFilter.class)
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers(HttpMethod.POST, "/login", "/api/login", "/api/register", "/user/sendMail", "/user/postEvent/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/assets/**", "/", "/*", "/user/getEvent/**", "user/getEvents/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/user/deleteEvent").permitAll()
                .anyRequest().authenticated());
        return http.build();
    }
}