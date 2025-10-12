package io.github.eduardoconceicao90.icompras.clientes.service;

import io.github.eduardoconceicao90.icompras.clientes.model.Cliente;
import io.github.eduardoconceicao90.icompras.clientes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ClienteService {

    private final ClienteRepository repository;

    public Cliente salvar(Cliente cliente) {
        return repository.save(cliente);
    }

    public Optional<Cliente> obterPorCodigo(Long codigo) {
        return repository.findById(codigo);
    }

}
