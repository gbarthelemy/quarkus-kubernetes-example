package com.gbarthelemy.kubernetes.gatewayservice.configuration;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SecretConfiguration {

    @ConfigProperty(name = "username")
    String username;

    @ConfigProperty(name = "password")
    String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
