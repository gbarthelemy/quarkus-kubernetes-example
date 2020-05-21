package com.gbarthelemy.kubernetes.gatewayservice.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "gateway.secret")
public class GatewaySecretProperties {

    private String name;
    private String password;

}
