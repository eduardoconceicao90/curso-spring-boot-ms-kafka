package io.github.eduardoconceicao90.icompras.pedidos.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = {"io.github.eduardoconceicao90.icompras.pedidos.client"})
@Configuration
public class ClientsConfig {

}
