package it.amrito.pastryshopbe.config.security;

import it.amrito.pastryshopbe.model.Backoffice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Objects;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic().disable()
                .csrf().disable()
                .cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/login").permitAll()
                .antMatchers("/dashboard/get").permitAll()
                .antMatchers(HttpMethod.GET, "/typological/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/typological/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/typological/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/typological/**").hasRole("ADMIN")
                .and()
                .apply(new JwtConfig(tokenProvider));
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
            }
        };
    }
}

@Component
class BackofficeSecurity {
    public boolean hasUserID(Authentication authentication, String nickane) {
        System.out.println("\n\nAuthenticating teacher...");

        if (!(authentication.getPrincipal() instanceof Backoffice)) { //getAuthorities().contains(new SimpleGrantedAuthority("ROLE_TEACHER"))
            System.out.println("Rejected.");
            return false;
        }
        Backoffice principal = (Backoffice) authentication.getPrincipal();
        System.out.println("OK.\n--ID: "+principal.getNickname());
        return Objects.equals(principal.getNickname(), nickane);
    }
}