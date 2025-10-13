package io.github.eduardoconceicao90.icompras.pedidos.client;

import io.github.eduardoconceicao90.icompras.pedidos.model.Pedido;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class ServiceBancarioClient {

    public String solicatarPagamento(Pedido pedido) {
        log.info("Simulando solicitação de pagamento para o pedido de código: {}", pedido.getCodigo());
        return UUID.randomUUID().toString();
    }

}
