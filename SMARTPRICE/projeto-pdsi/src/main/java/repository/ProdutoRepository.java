package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	public List<Produto> findByDescricaoContaining(String descricao);

	public Produto findByCodigo(Long codigo);
}
