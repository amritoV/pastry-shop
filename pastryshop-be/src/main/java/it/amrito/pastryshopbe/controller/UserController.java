package it.amrito.pastryshopbe.controller;

import it.amrito.pastryshopbe.config.security.JwtTokenProvider;
import it.amrito.pastryshopbe.dto.UserDto;
import it.amrito.pastryshopbe.model.Backoffice;
import it.amrito.pastryshopbe.repository.BackofficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    BackofficeRepository backofficeRepository;

    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> login(@Valid @RequestBody UserDto userDto){
        String nickname = userDto.getNickname();
        String password = userDto.getPassword();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(nickname, password));

        Backoffice backoffice = backofficeRepository.findByNickname(nickname).orElseThrow(()-> new UsernameNotFoundException("Backoffice not found"));
        String token = tokenProvider.createToken(
                backoffice.getNickname(),
                List.of("ROLE_ADMIN")
        );
        Map<String, Object> model = new HashMap<>();
        model.put("username", nickname);
        model.put("token", token);
        return ResponseEntity.ok(model);
    }
}
