package io.github.eduardoconceicao90.icompras.logistica.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.eduardoconceicao90.icompras.logistica.service.EnvioPedidoService;
import io.github.eduardoconceicao90.icompras.logistica.subscriber.representation.AtualizacaoFaturamentoRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class FaturamentoSubscriber {

    private final ObjectMapper objectMapper;
    private final EnvioPedidoService service;

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${icompras.config.kafka.topics.pedidos-faturados}"
    )
    public void listener(String json){
        try {
            log.info("Recebendo pedido para envio: {}", json);
            var representation = objectMapper.readValue(json, AtualizacaoFaturamentoRepresentation.class);
            service.enviar(representation.codigo());
            log.info("Pedido processado com sucesso! Código: {}", representation.codigo());
        } catch (Exception e) {
            log.error("Erro ao preparar pedido para envio", e);
        }
    }

}
