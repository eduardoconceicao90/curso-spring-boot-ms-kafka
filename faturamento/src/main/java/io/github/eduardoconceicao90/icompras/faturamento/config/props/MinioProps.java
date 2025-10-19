package io.github.eduardoconceicao90.icompras.faturamento.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "minio")
@Configuration
public class MinioProps {

    private String url;
    private String accessKey;
    private String secretKey;
    private String bucketName;

}
