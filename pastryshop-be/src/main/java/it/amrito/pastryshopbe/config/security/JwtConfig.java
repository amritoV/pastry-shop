package it.amrito.pastryshopbe.config.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>{

    private JwtTokenProvider tokenProvider;

    public JwtConfig(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    //-----

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        JwtTokenFilter customFilter = new JwtTokenFilter(tokenProvider);
        builder.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
