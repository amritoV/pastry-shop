package it.amrito.pastryshopbe.config.security;

import it.amrito.pastryshopbe.exception.InvalidJwtAuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider tokenProvider;

    public JwtTokenFilter(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    //-----

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String token = tokenProvider.resolveToken((HttpServletRequest) servletRequest);
        try {
            if (token != null && tokenProvider.validateToken(token)) {
                Authentication authentication = tokenProvider.getAuthenticationFromToken(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (InvalidJwtAuthenticationException e) {
            e.printStackTrace();
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
