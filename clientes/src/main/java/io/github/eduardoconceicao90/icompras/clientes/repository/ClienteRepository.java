package io.github.eduardoconceicao90.icompras.clientes.repository;

import io.github.eduardoconceicao90.icompras.clientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
