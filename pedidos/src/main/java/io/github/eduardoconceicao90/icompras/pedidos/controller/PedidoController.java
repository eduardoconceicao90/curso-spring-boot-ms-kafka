package io.github.eduardoconceicao90.icompras.pedidos.controller;

import io.github.eduardoconceicao90.icompras.pedidos.controller.dto.AdicaoNovoPagamentoDTO;
import io.github.eduardoconceicao90.icompras.pedidos.controller.dto.NovoPedidoDTO;
import io.github.eduardoconceicao90.icompras.pedidos.controller.mapper.PedidoMapper;
import io.github.eduardoconceicao90.icompras.pedidos.model.exception.ErrorResponse;
import io.github.eduardoconceicao90.icompras.pedidos.model.exception.ValidationException;
import io.github.eduardoconceicao90.icompras.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("pedidos")
public class PedidoController {

    private final PedidoService service;
    private final PedidoMapper mapper;

    @PostMapping
    public ResponseEntity<Object> criar(@RequestBody NovoPedidoDTO novoPedidoDto) {
        try{
            var pedido = service.criar(mapper.toPedido(novoPedidoDto));
            return ResponseEntity.ok(pedido.getCodigo());
        } catch (ValidationException e) {
            var errorResponse = new ErrorResponse("Erro de validação", e.getField(), e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PostMapping("/pagamentos")
    public ResponseEntity<Object> adicionarNovoPagamento(@RequestBody AdicaoNovoPagamentoDTO adicaoNovoPagamentoDTO) {
        try{
            service.adicionarNovoPagamento(
                    adicaoNovoPagamentoDTO.codigoPedido(),
                    adicaoNovoPagamentoDTO.dados(),
                    adicaoNovoPagamentoDTO.tipoPagamento()
            );
            return ResponseEntity.noContent().build();
        } catch (Exception e) {

        }
    }

}
