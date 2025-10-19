package io.github.eduardoconceicao90.icompras.faturamento.bucket;

import org.springframework.http.MediaType;

import java.io.InputStream;

public record BucketFile(
        String name,
        InputStream inputStream,
        MediaType mediaType,
        long size
) {
}
