package it.amrito.pastryshopbe.config.security;

import it.amrito.pastryshopbe.repository.BackofficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private BackofficeRepository backofficeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return backofficeRepository.findByNickname(username).orElseThrow(() -> new UsernameNotFoundException("User: " + username + " not found."));
    }
}
