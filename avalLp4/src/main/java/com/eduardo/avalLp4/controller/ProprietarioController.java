package com.eduardo.avalLp4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eduardo.avalLp4.excecao.MotoNotFoundException;
import com.eduardo.avalLp4.modelo.Moto;
import com.eduardo.avalLp4.modelo.Proprietario;
import com.eduardo.avalLp4.servico.MotoServico;
import com.eduardo.avalLp4.servico.ProprietarioServico;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/proprietario")
public class ProprietarioController {
	
		@Autowired
		private MotoServico motoServico;
		
		@Autowired
		private ProprietarioServico proprietarioServico;
		
		@GetMapping("/buscarProprietario/{id}")
		public String novoProprietario(@PathVariable("id") long id, Model model) {
			String pagina = ""; 
			try {
				Moto moto = motoServico.buscaPorId(id);
				if (moto.getProprietario() == null) {
					Proprietario proprietario = new Proprietario();
					proprietario.setMoto(moto);
					model.addAttribute("item", proprietario);
					pagina = "/novoProprietario";
				} else {
					model.addAttribute("item", moto.getProprietario());
					pagina = "/alterarProprietario";
				}

			} catch (MotoNotFoundException e) {
				System.out.println(e.getMessage());
				return "redirect:/";
			}
			return pagina;
		}
		
		@PostMapping("/gravarProprietario/{idMoto}")
		public String gravarProprietario(@PathVariable("idMoto") long idMoto,
				@ModelAttribute("item") @Valid Proprietario proprietario, BindingResult result, RedirectAttributes attributes) {
			try {
				Moto moto = motoServico.buscaPorId(idMoto);
				proprietario.setMoto(moto);
			} catch (MotoNotFoundException e) {
				e.printStackTrace();
			}

			if (result.hasErrors()) {
				return "/novoProprietario";
			}
			proprietarioServico.criarProprietario(proprietario);
			attributes.addFlashAttribute("mensagem", "Gravado com sucesso");
			return "redirect:/";
		}
		
		@PostMapping("/alterarProprietario/{idMoto}/{idProprietario}")
		public String alterarProprietario(@PathVariable("idMoto") long idMoto,
				@PathVariable("idProprietario") long idProprietario, @ModelAttribute("item") @Valid Proprietario proprietario,
				BindingResult result, RedirectAttributes attributes) {
			try {
				Moto moto = motoServico.buscaPorId(idMoto);
				proprietario.setMoto(moto);
				proprietario.setId(idProprietario);			
			} catch (MotoNotFoundException e) {
				e.printStackTrace();
			}

			if (result.hasErrors()) {			
				return "/alterarProprietario";
			}
			proprietarioServico.alterarProprietario(proprietario);
			attributes.addFlashAttribute("mensagem", "Gravado com sucesso");
			return "redirect:/";
		}
		

	}
