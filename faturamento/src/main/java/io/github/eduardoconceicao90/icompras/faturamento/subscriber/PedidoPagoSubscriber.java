package io.github.eduardoconceicao90.icompras.faturamento.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.eduardoconceicao90.icompras.faturamento.model.Pedido;
import io.github.eduardoconceicao90.icompras.faturamento.service.GeradorNotaFiscalService;
import io.github.eduardoconceicao90.icompras.faturamento.subscriber.mapper.PedidoMapper;
import io.github.eduardoconceicao90.icompras.faturamento.subscriber.representation.DetalhePedidoRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PedidoPagoSubscriber {

    private final PedidoMapper mapper;
    private final ObjectMapper objectMapper;
    private final GeradorNotaFiscalService service;

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${icompras.config.kafka.topics.pedidos-pagos}"
    )
    public void listener(String json){
        try {
            log.info("Recebendo pedido para faturamento: {}", json);
            var representation = objectMapper.readValue(json, DetalhePedidoRepresentation.class);
            Pedido pedido = mapper.map(representation);
            service.gerarNotaFiscal(pedido);
        } catch (Exception e) {
           log.error("Erro ao processar o evento de pedido pago", e);
        }
    }

}
