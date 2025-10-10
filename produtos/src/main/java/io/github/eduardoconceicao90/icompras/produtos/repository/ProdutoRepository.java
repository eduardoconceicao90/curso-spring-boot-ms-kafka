package io.github.eduardoconceicao90.icompras.produtos.repository;

import io.github.eduardoconceicao90.icompras.produtos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
