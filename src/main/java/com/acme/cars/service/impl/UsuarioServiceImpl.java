package com.acme.cars.service.impl;

import com.acme.cars.model.Usuario;
import com.acme.cars.repository.UsuarioRepository;
import com.acme.cars.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> buscarTodos() {
        List<Usuario> list = usuarioRepository.findAll();
        list.forEach(u -> u.setPassword(null));
        return list;
    }

    @Override
    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Optional<Usuario> buscarUsuarioPorEmail(String  email) {
        return usuarioRepository.findByEmail(email);
    }

}
