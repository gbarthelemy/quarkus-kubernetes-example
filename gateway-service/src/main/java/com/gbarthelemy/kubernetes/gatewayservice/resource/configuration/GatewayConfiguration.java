package com.gbarthelemy.kubernetes.gatewayservice.resource.configuration;

import io.quarkus.arc.config.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ConfigProperties(prefix = "gateway")
public interface GatewayConfiguration {

    @ConfigProperty(name = "message")
    String getMessage();
}
