package io.github.eduardoconceicao90.icompras.pedidos.repository;

import io.github.eduardoconceicao90.icompras.pedidos.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
