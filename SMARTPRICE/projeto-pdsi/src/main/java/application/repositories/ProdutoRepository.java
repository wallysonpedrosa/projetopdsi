package application.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import application.models.Produto;

@Repository
@Transactional
public interface ProdutoRepository extends CrudRepository<Produto, Long> {

	public List<Produto> findByDescricaoContaining(String descricao);

	public Produto findByCodigo(Long codigo);

}
