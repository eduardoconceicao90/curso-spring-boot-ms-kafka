package io.github.eduardoconceicao90.icompras.produtos.service;

import io.github.eduardoconceicao90.icompras.produtos.model.Produto;
import io.github.eduardoconceicao90.icompras.produtos.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public Produto salvar(Produto produto) {
        return repository.save(produto);
    }

    public Optional<Produto> obterPorCodigo(Long codigo) {
        return repository.findById(codigo);
    }

}
