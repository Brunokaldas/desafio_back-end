package com.townsq.test.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.townsq.test.domain.entity.Usuarios;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, Long>{
	
	
	List<Usuarios> listar();
	Usuarios buscar(Long id);
	Usuarios adicionar(Usuarios usuario);
	void remover(Usuarios usuario);	
	

}
