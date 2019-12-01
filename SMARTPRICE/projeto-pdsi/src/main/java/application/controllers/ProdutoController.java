package application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import application.filters.ProdutoFilter;
import application.models.Produto;
import application.services.ProdutoService;

@Controller
@RequestMapping("produtos")
public class ProdutoController {
	
	private static final String CADASTRO_VIEW = "produtos/cadastroProduto";
	private static final String PESQUISA_VIEW = "produtos/pesquisaProduto";

	@Autowired
	private ProdutoService produtoService;

	@RequestMapping
	public ModelAndView pesquisar(@ModelAttribute("filtro") ProdutoFilter filtro) {
		List<Produto> produtos = produtoService.filtrar(filtro);
		
		ModelAndView mv = new ModelAndView(PESQUISA_VIEW);
		mv.addObject("produtos", produtos);
		return mv;
	}
	
	@RequestMapping("/cadastro")
	public ModelAndView cadastrar() {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Produto());
		return mv;
	}
	
	@RequestMapping(value = "/inserir", method = RequestMethod.POST)
	public String salvar(@Validated Produto produto, Errors errors, RedirectAttributes attributes) {
		if (errors.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Erro ao salvar o produto!");
			return CADASTRO_VIEW;
		}
		
		try {
			produtoService.salvar(produto);
			attributes.addFlashAttribute("mensagem", "Produto salvo com sucesso!");
			return "redirect:/produtos";
		}
		catch (IllegalArgumentException e) {
			errors.rejectValue("dataVencimento", null, e.getMessage());
			return CADASTRO_VIEW;
		}
	}
	
	@RequestMapping("{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Produto produto) {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW); 
		mv.addObject(produto);
		return mv;
	}
	
	@RequestMapping(value = "{codigo}/excluir")
	public String excluir(@PathVariable Long codigo, RedirectAttributes attributes) {
		produtoService.excluir(codigo);
		attributes.addFlashAttribute("mensagem", "Produto excluído com sucesso!");
		return "redirect:/produtos";
	}
	
	@RequestMapping(value = "/{codigo}/receber", method = RequestMethod.PUT)
	public @ResponseBody String receber(@PathVariable Long codigo) {
		return produtoService.receber(codigo);
	}

}
