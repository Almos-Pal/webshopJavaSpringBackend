package hu.wv.webshopbackend.config;


import hu.wv.webshopbackend.auth.JwtAuthorizationFilter;
import hu.wv.webshopbackend.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {


    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    public SecurityConfiguration(JwtAuthorizationFilter jwtAuthorizationFilter) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    CustomCorsConfiguration customCorsConfiguration;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers("users/login", "users/register").permitAll().anyRequest().authenticated()).addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(c -> c.configurationSource(customCorsConfiguration));;
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, UserService userService)
            throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
        return authenticationManagerBuilder.build();
    }

}
