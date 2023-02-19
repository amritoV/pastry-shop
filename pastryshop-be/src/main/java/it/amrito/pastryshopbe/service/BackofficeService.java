package it.amrito.pastryshopbe.service;

import it.amrito.pastryshopbe.config.security.JwtTokenProvider;
import it.amrito.pastryshopbe.dto.UserDto;
import it.amrito.pastryshopbe.model.Backoffice;
import it.amrito.pastryshopbe.repository.BackofficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BackofficeService {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    BackofficeRepository backofficeRepository;

    public String login(UserDto userDto){
        String nickname = userDto.getNickname();
        String password = userDto.getPassword();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(nickname, password));

        Backoffice backoffice = backofficeRepository.findById(nickname).orElseThrow(()-> new UsernameNotFoundException("Backoffice not found"));
        return tokenProvider.createToken(
                backoffice.getNickname(),
                backoffice.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()
        );
    }
}
