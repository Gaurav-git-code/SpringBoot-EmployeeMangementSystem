package com.darknightcoder.ems.controller;

import com.darknightcoder.ems.model.EmsUserDto;
import com.darknightcoder.ems.model.JwtResponse;
import com.darknightcoder.ems.model.LoginDto;
import com.darknightcoder.ems.security.JwtUtils;
import com.darknightcoder.ems.service.EmsUserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    AuthenticationManager authenticationManager;
    JwtUtils jwtUtils;
    EmsUserService emsUserService;


    @PostMapping(value = "/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(
                       loginDto.getUsername(),
                       loginDto.getPassword()
               )
        );
        JwtResponse response = new JwtResponse();
        response.setToken(jwtUtils.generateToken(authentication.getName()));

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<String> signUp(@RequestBody EmsUserDto emsUserDto){
        emsUserService.createUser(emsUserDto);
        return new ResponseEntity<>("Sign up successful",HttpStatus.OK);
    }


}
