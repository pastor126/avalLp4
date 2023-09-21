package com.eduardo.avalLp4.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eduardo.avalLp4.modelo.Moto;

public interface MotoRepositorio extends JpaRepository<Moto, Long> {
	List<Moto> findByModeloContainingIgnoreCase(String modelo);
}
