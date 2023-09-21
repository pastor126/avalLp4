package com.eduardo.avalLp4.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardo.avalLp4.modelo.Proprietario;
import com.eduardo.avalLp4.repositorio.ProprietarioRepositorio;

@Service
public class ProprietarioServico {

		@Autowired
		private ProprietarioRepositorio proprietarioRepositorio;
		
		public Proprietario criarProprietario(Proprietario moto) {
			return proprietarioRepositorio.save(moto);
		}
		
		public Proprietario alterarProprietario(Proprietario moto) {
			return proprietarioRepositorio.save(moto);
		}
	}
