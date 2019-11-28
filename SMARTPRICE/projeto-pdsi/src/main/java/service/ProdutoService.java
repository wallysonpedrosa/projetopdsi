package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import constantes.FlagStatusProduto;
import filter.ProdutoFilter;
import model.Produto;
import repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtos;
	
	public void salvar(Produto produto) {
		try {
			produtos.save(produto);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data inválido");
		}
	}

	public void excluir(Long codigo) {
		produtos.deleteById(codigo);
	}

	public String receber(Long codigo) {
		Produto produto = produtos.findByCodigo(codigo);
		produto.setStatusProduto(FlagStatusProduto.RESERVADO);
		produtos.save(produto);
		
		return FlagStatusProduto.RESERVADO.getDescricao();
	}
	
	public List<Produto> filtrar(ProdutoFilter filtro) {
		String descricao = filtro.getDescricao() == null ? "%" : filtro.getDescricao();
		return produtos.findByDescricaoContaining(descricao);
	}
}
