package com.eduardo.avalLp4.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardo.avalLp4.excecao.MotoNotFoundException;
import com.eduardo.avalLp4.modelo.Moto;
import com.eduardo.avalLp4.repositorio.MotoRepositorio;

@Service
public class MotoServico {
	@Autowired
	private MotoRepositorio motoRepositorio;
	
	public Moto novaMoto(Moto moto) {
		return motoRepositorio.save(moto);
	}
	
	public List<Moto> buscarTodasMotos() {
		return motoRepositorio.findAll();
	}
	
	public Moto buscaPorId(Long id) throws MotoNotFoundException {
		Optional<Moto> opt = motoRepositorio.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		}else {
			throw new MotoNotFoundException("A moto informada não existe!");
		}
	}
	
	public void apagarMoto(Long id) throws MotoNotFoundException {
		Moto moto = buscaPorId(id);
		motoRepositorio.delete(moto);
	}
	
	public Moto editarMoto(Moto moto) {
		return motoRepositorio.save(moto);
	}
	
	public List<Moto> buscaMotosPorModelo(String modelo) {
		return motoRepositorio.findByModeloContainingIgnoreCase(modelo);
	}

}
