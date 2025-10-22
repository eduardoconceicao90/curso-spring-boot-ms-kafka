package io.github.eduardoconceicao90.icompras.faturamento.service;

import io.github.eduardoconceicao90.icompras.faturamento.model.Pedido;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GeradorNotaFiscalService {

    public void gerarNotaFiscal(Pedido pedido) {
        // Lógica para gerar a nota fiscal
        log.info("Gerando nota fiscal para o pedido de código: {}", pedido.codigo());
    }

}
