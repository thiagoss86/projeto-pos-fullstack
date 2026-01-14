package com.acme.cars.controller;

import com.acme.cars.dto.AuthUserDTO;
import com.acme.cars.model.Usuario;
import com.acme.cars.payload.AuthPayload;
import com.acme.cars.service.RevokedTokenService;
import com.acme.cars.service.SecurityService;
import com.acme.cars.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final SecurityService securityService;
    private final RevokedTokenService revokedTokenService;

    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuario() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthPayload> autenticate(@RequestBody AuthUserDTO authUserDTO){
        String token = securityService.authenticate(authUserDTO);
        return ResponseEntity.ok(new AuthPayload(token));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authorization) {
        revokedTokenService.revoke(authorization);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my-profile")
    public ResponseEntity<Usuario> getMyProfile(Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        Optional<Usuario> byId = usuarioService.findById(Long.valueOf(userId));
        return byId.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
