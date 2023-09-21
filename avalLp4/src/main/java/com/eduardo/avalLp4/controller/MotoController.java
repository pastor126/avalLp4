package com.eduardo.avalLp4.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eduardo.avalLp4.excecao.MotoNotFoundException;
import com.eduardo.avalLp4.modelo.Moto;
import com.eduardo.avalLp4.servico.MotoServico;

import jakarta.validation.Valid;

@Controller
public class MotoController {

	@Autowired
	private MotoServico motoservico;

	@GetMapping("/")
	public String listarMotos(Model model) {
		List<Moto> motos = motoservico.buscarTodasMotos();
		model.addAttribute("motos", motos);
		return "/listaMoto";
	}

	@GetMapping("/cadastrar")
	public String nova(Model model) {
		Moto moto = new Moto();
		model.addAttribute("novaMoto", moto);
		return "/formMoto";
	}

	@PostMapping("/gravar")
// Pega o objeto "novaMoto" e transfere para o objeto Moto moto
	public String salvarMoto(@ModelAttribute("novaMoto") @Valid Moto moto, BindingResult erro,
			RedirectAttributes atributos) {// Redireciona atributos vindos do BD ao salvar
		if (erro.hasErrors()) {
			return "/formMoto";
		}
		motoservico.novaMoto(moto);
		atributos.addFlashAttribute("mensage", "Moto salva com sucesso");
		return "redirect:/cadastrar"; // redireciono para o metodo nova ("/cadastrar")
	}

	@GetMapping("/apagar/{id}")
	public String apagar(@PathVariable("id")Long id, RedirectAttributes atributos) {
		try {
			motoservico.apagarMoto(id);
		} catch (MotoNotFoundException e) {
			atributos.addFlashAttribute("mensageErro", e.getMessage());
		}
		return "redirect:/";
	}
	
	@GetMapping("/editar/{id}")
	public String editar(@PathVariable("id")Long id, RedirectAttributes atributos, Model model) {
		try {
			Moto moto = motoservico.buscaPorId(id);
			System.out.println(moto.getModelo());
			model.addAttribute("objMoto", moto);
			return "/formEditaMoto";
		} catch (MotoNotFoundException e) {
			atributos.addFlashAttribute("mensageErro", e.getMessage());
		}
		return "redirect:/";
	}
	
	@PostMapping("/editar/{id}")
		public String editarMoto(@PathVariable("id")Long id, @ModelAttribute("objMoto") @Valid Moto moto,
				BindingResult erro) {// Redireciona atributos vindos do BD ao salvar
			if (erro.hasErrors()) {
				return "/formMoto";
			}
			motoservico.editarMoto(moto);
			return "redirect:/";
	}
	
	@PostMapping("/buscar")
	public String buscarMoto(@Param("modelo")String modelo, Model model) {
		if(modelo == null) {
			return "redirect: /";
		}
		List<Moto> motos = motoservico.buscaMotosPorModelo(modelo);
		model.addAttribute("motos", motos);
		return "/listaMoto";
	}

}
