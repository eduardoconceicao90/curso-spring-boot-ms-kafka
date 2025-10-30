package io.github.eduardoconceicao90.icompras.logistica.subscriber.representation;

import io.github.eduardoconceicao90.icompras.logistica.model.enums.StatusPedido;

public record AtualizacaoFaturamentoRepresentation(
        Long codigo,
        StatusPedido status,
        String urlNotaFiscal
) {
}
