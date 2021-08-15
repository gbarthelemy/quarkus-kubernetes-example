package com.gbarthelemy.kubernetes.gatewayservice.configuration;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

@ConfigMapping(prefix = "gateway")
public interface GatewayConfiguration {

    @WithName("message")
    String getMessage();
}
