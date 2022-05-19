package com.usuarios.api.config;

import org.springframework.data.repository.CrudRepository;

import com.usuarios.api.dto.Usuario;

public interface UsuarioRepst extends CrudRepository<Usuario, Long>{

}
