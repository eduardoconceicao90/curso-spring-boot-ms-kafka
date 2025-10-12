package io.github.eduardoconceicao90.icompras.pedidos.model;

import io.github.eduardoconceicao90.icompras.pedidos.model.enums.TipoPagamento;
import lombok.Data;

@Data
public class DadosPagamento {

    private String dados;
    private TipoPagamento tipoPagamento;

}
