package controller;

import java.util.Arrays;
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

import constantes.FlagStatusProduto;
import filter.ProdutoFilter;
import model.Produto;
import service.ProdutoService;

@Controller
@RequestMapping("produtos")
public class ProdutoController {
	
	private static final String CADASTRO_VIEW = "CadastroProduto";

	@Autowired
	private ProdutoService produtoService;
	
	@RequestMapping("/novaTela")
	public String novaTela() {
		return "CadastroProduto";
	}
	
	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Produto());
		return mv;
	}
	
	@RequestMapping(method= RequestMethod.POST)
	public String salvar(@Validated Produto produto, Errors errors, RedirectAttributes attributes) {
		if (errors.hasErrors()) {
			return CADASTRO_VIEW;
		}
		
		try {
			produtoService.salvar(produto);
			attributes.addFlashAttribute("mensagem", "Produto salvo com sucesso!");
			return "redirect:/produtos/novo";
		} catch (IllegalArgumentException e) {
			errors.rejectValue("dataVencimento", null, e.getMessage());
			return CADASTRO_VIEW;
		}
	}
	@RequestMapping
	public ModelAndView pesquisar(@ModelAttribute("filtro") ProdutoFilter filtro) {
		List<Produto> todosProdutos = produtoService.filtrar(filtro);
		
		ModelAndView mv = new ModelAndView("PesquisaProdutos");
		mv.addObject("produtos", todosProdutos);
		return mv;
	}
	
	@RequestMapping("{codigo}")
	public ModelAndView edicao(@PathVariable("codigo") Produto produto) {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW); 
		mv.addObject(produto);
		return mv;
	}
	
	@RequestMapping(value="{codigo}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long codigo, RedirectAttributes attributes) {
		produtoService.excluir(codigo);
		
		attributes.addFlashAttribute("mensagem", "Produto excluído com sucesso!");
		return "redirect:/produtos";
	}
	
	@RequestMapping(value = "/{codigo}/receber", method = RequestMethod.PUT)
	public @ResponseBody String receber(@PathVariable Long codigo) {
		return produtoService.receber(codigo);
	}
	
	@ModelAttribute("todosStatusProduto")
	public List<FlagStatusProduto> todosStatusProduto() {
		return Arrays.asList(FlagStatusProduto.values());
	}
	
	
}
