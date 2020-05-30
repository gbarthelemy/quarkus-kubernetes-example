package com.gbarthelemy.kubernetes.gatewayservice.resource.resource;

import com.gbarthelemy.kubernetes.gatewayservice.resource.configuration.GatewayConfiguration;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GatewayController {

    private static final String DUMMY_SERVICE_URL = "http://quarkus-k8s-dummy-service:8080";
    public static final String NAMESPACE = "default";

    private final KubernetesClient kubernetesClient;
    private final GatewayConfiguration gatewayConfiguration;

    public GatewayController(final KubernetesClient kubernetesClient, final GatewayConfiguration gatewayConfiguration) {
        this.kubernetesClient = kubernetesClient;
        this.gatewayConfiguration = gatewayConfiguration;
    }

    @GetMapping
    public List<Pod> getServicesDetails() {
        return kubernetesClient.pods().inNamespace(NAMESPACE).list().getItems();
    }

    //TODO
    @GetMapping(path = "/dummy")
    public String getDummyServiceDetails() {
        return null;
    }

    @GetMapping(path = "/configmap")
    public String getConfigMap() {
        return gatewayConfiguration.getMessage();
    }

    //TODO
    @GetMapping(path = "/secret")
    public String getSecrets() {
        return null;
    }
}
