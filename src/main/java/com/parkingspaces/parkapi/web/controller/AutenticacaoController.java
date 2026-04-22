package com.parkingspaces.parkapi.web.controller;


import com.parkingspaces.parkapi.jwt.JwtToken;
import com.parkingspaces.parkapi.jwt.JwtUserDetailsService;
import com.parkingspaces.parkapi.web.dto.UsuarioLoginDto;
import com.parkingspaces.parkapi.web.exception.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class AutenticacaoController {

    private final JwtUserDetailsService jwtUserDetailsService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> autenticar(@RequestBody @Valid UsuarioLoginDto dto, HttpServletRequest request) {
        log.info("Processo de autenticação iniciado para o usuário: {}", dto.getUsername());

        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
            authenticationManager.authenticate(authToken);
            JwtToken token = jwtUserDetailsService.getTokenAuthentication(dto.getUsername());
            log.info("Autenticação bem-sucedida para o usuário: {}. Token gerado com sucesso.", dto.getUsername());
            return ResponseEntity.ok(token);

        }catch (AuthenticationException e){
            log.warn("Falha na autenticação para o usuário: {}. Detalhes: {}", dto.getUsername(), e.getMessage());
        }
        return ResponseEntity.badRequest().body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Credenciais invválidas"));
    }
}
