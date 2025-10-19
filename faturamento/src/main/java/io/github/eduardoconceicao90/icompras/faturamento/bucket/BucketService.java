package io.github.eduardoconceicao90.icompras.faturamento.bucket;

import io.github.eduardoconceicao90.icompras.faturamento.config.props.MinioProps;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BucketService {

    private final MinioClient client;
    private final MinioProps props;

    public void upload(BucketFile file){

    }

    public String getUrl(String fileName){
        return "";
    }

}
