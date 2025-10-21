package io.github.eduardoconceicao90.icompras.faturamento.model;

import java.math.BigDecimal;
import java.util.List;

public record Pedido(
        Long codigo,
        Cliente cliente,
        String data,
        List<ItemPedido> itens,
        BigDecimal total
) {
}
