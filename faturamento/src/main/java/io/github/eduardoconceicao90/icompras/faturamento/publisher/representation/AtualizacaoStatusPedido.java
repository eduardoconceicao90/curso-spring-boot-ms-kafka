package io.github.eduardoconceicao90.icompras.faturamento.publisher.representation;

import io.github.eduardoconceicao90.icompras.faturamento.model.enums.StatusPedido;

public record AtualizacaoStatusPedido(
        Long codigo,
        StatusPedido status,
        String urlNotaFiscal
) {
}
