package it.amrito.pastryshopbe.controller;

import it.amrito.pastryshopbe.dto.UserDto;
import it.amrito.pastryshopbe.service.BackofficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class BackofficeController {

    @Autowired
    private BackofficeService backofficeService;

    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> login(@Valid @RequestBody UserDto userDto){
        String token = backofficeService.login(userDto);
        Map<String, Object> model = new HashMap<>();
        model.put("username", userDto.getNickname());
        model.put("token", token);
        return ResponseEntity.ok(model);
    }
}
