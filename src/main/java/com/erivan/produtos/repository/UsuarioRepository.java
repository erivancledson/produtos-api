package com.erivan.produtos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erivan.produtos.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

		Optional<Usuario> findByEmail(String email);
}
