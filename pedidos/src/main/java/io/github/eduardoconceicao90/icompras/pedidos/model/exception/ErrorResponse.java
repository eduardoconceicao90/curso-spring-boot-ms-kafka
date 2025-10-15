package io.github.eduardoconceicao90.icompras.pedidos.model.exception;

public record ErrorResponse(String message, String field, String error) {
}
