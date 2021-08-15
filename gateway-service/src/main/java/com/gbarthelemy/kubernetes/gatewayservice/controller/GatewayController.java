package com.gbarthelemy.kubernetes.gatewayservice.controller;

import com.gbarthelemy.kubernetes.gatewayservice.client.DummyClient;
import com.gbarthelemy.kubernetes.gatewayservice.configuration.GatewayConfiguration;
import com.gbarthelemy.kubernetes.gatewayservice.configuration.SecretConfiguration;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
public class GatewayController {

    public static final String NAMESPACE = "default";

    @Inject
    @RestClient
    DummyClient dummyClient;

    private final KubernetesClient kubernetesClient;
    private final GatewayConfiguration gatewayConfiguration;
    private final SecretConfiguration secretConfiguration;

    public GatewayController(
            final KubernetesClient kubernetesClient,
            final GatewayConfiguration gatewayConfiguration,
            final SecretConfiguration secretConfiguration
    ) {
        this.kubernetesClient = kubernetesClient;
        this.gatewayConfiguration = gatewayConfiguration;
        this.secretConfiguration = secretConfiguration;
    }

    @GetMapping
    public List<Pod> getServicesDetails() {
        return kubernetesClient.pods().inNamespace(NAMESPACE).list().getItems();
    }

    @GetMapping(path = "/dummy")
    public String getDummyServiceDetails() {
        return dummyClient.get();
    }

    @GetMapping(path = "/configmap")
    public String getConfigMap() {
        return gatewayConfiguration.getMessage();
    }

    @GetMapping(path = "/secret")
    public String getSecrets() {
        return "username=" + secretConfiguration.getUsername() +
                "&password=" + secretConfiguration.getPassword();
    }
}
