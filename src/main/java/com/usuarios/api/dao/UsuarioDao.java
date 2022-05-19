package com.usuarios.api.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.usuarios.api.config.UsuarioRepst;
import com.usuarios.api.dto.Usuario;

@Service
public class UsuarioDao {
	
	@Autowired
	private UsuarioRepst usuarioRepository;	
	
	public Usuario addUsuario(Usuario user) {
		usuarioRepository.save(user);
		return user;
	}
	
	public List<Usuario> listUsuario(){		
		return (List<Usuario>) usuarioRepository.findAll();
		
	}
	
	public Optional<Usuario> BuscarUsuarioId(Long id) {
		return usuarioRepository.findById(id);
	}
	
	public void BorrarUsuarioId(Long id) {
		usuarioRepository.deleteById(id);
	}

}
