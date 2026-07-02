package com.acme.cars.service;

import com.acme.cars.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuario> buscarTodos();

    Optional<Usuario> buscarUsuarioPorId(Long id);

    Optional<Usuario> buscarUsuarioPorEmail(String email);
}
