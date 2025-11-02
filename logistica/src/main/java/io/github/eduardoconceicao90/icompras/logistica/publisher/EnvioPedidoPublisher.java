package io.github.eduardoconceicao90.icompras.logistica.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.eduardoconceicao90.icompras.logistica.model.AtualizacaoEnvioPedido;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class EnvioPedidoPublisher {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${icompras.config.kafka.topics.pedidos-enviados}")
    private String topicoEnviados;

    public void enviar(AtualizacaoEnvioPedido atualizacaoEnvioPedido){
        log.info("Publicando pedido enviado {} ", atualizacaoEnvioPedido.codigo());

        try {
            var json = objectMapper.writeValueAsString(atualizacaoEnvioPedido);
            kafkaTemplate.send(topicoEnviados, "dados", json);
            log.info("Pedido enviado publicado com sucesso: {}", atualizacaoEnvioPedido.codigo());
        } catch (JsonProcessingException e) {
            log.error("Erro ao converter o evento de pedido enviado para JSON: {}", atualizacaoEnvioPedido.codigo());
        } catch (RuntimeException e){
            log.error("Erro ao publicar envio do pedodio", e);
        }
    }

}
