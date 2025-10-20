package io.github.eduardoconceicao90.icompras.faturamento.bucket;

import io.github.eduardoconceicao90.icompras.faturamento.config.props.MinioProps;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class BucketService {

    private final MinioClient client;
    private final MinioProps props;

    public void upload(BucketFile file){
        try {
            var object = PutObjectArgs.builder()
                    .bucket(props.getBucketName())
                    .object(file.name())
                    .stream(file.inputStream(), file.size(), -1)
                    .contentType(file.mediaType().toString())
                .build();

            client.putObject(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getUrl(String fileName){
        try {
            var objectUrl = GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(props.getBucketName())
                        .object(fileName)
                        .expiry(1, TimeUnit.HOURS)
                    .build();

            return client.getPresignedObjectUrl(objectUrl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
