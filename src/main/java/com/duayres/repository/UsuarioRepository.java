package com.duayres.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duayres.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

}
