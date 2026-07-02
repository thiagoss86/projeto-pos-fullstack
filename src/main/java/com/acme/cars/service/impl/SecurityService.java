package com.acme.cars.service.impl;

import com.acme.cars.dto.AuthUserDTO;
import com.acme.cars.exception.AuthenticationException;
import com.acme.cars.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final UsuarioServiceImpl usuarioServiceImpl;
    private final TokenServiceImpl tokenServiceImpl;
    private final PasswordEncoder passwordEncoder;

    public String authenticate(AuthUserDTO authUserDTO) throws AuthenticationException {
        Optional<Usuario> byEmail = usuarioServiceImpl.buscarUsuarioPorEmail(authUserDTO.email());

        if (byEmail.isEmpty()) throw new AuthenticationException("Usuário ou senha incorretos");

        Usuario usuario = byEmail.get();

        if (passwordEncoder.matches(authUserDTO.password(), usuario.getPassword())) {
            return tokenServiceImpl.gerarToken(usuario);
        } else {
            throw new AuthenticationException("Usuário ou senha incorretos");
        }
    }
}
