package application.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import application.enums.FlagStatusProduto;
import application.filters.ProdutoFilter;
import application.models.Produto;
import application.repositories.ProdutoRepository;

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
		Produto produto = produtos.findById(codigo).get();
		produto.setStatus(FlagStatusProduto.RESERVADO);
		produtos.save(produto);

		return FlagStatusProduto.RESERVADO.getLabel();
	}

	public List<Produto> filtrar(ProdutoFilter filtro) {
		List<Produto> result = new ArrayList<Produto>();
		
		if(filtro == null || filtro.getDescricao() == null || filtro.getDescricao().isEmpty()) {
			Iterator<Produto> i = produtos.findAll().iterator();
			
			while(i.hasNext()) {
				result.add(i.next());
			}
		}
		else {
			result = produtos.findByDescricaoContainingIgnoreCase(filtro.getDescricao());
		}
		
		return result;
	}
}
