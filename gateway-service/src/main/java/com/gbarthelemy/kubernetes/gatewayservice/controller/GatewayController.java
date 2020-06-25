package com.gbarthelemy.kubernetes.gatewayservice.controller;

import com.gbarthelemy.kubernetes.gatewayservice.client.DummyClient;
import com.gbarthelemy.kubernetes.gatewayservice.configuration.GatewayConfiguration;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.reactivestreams.Publisher;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
public class GatewayController {

    public static final String NAMESPACE = "default";

    @Inject
    @RestClient
    DummyClient dummyClient;

    @Inject
    @Channel("consumer")
    Publisher<Double> prices;

    private final KubernetesClient kubernetesClient;
    private final GatewayConfiguration gatewayConfiguration;

    public GatewayController(final KubernetesClient kubernetesClient, final GatewayConfiguration gatewayConfiguration) {
        this.kubernetesClient = kubernetesClient;
        this.gatewayConfiguration = gatewayConfiguration;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pod> getServicesDetails() {
        return kubernetesClient.pods().inNamespace(NAMESPACE).list().getItems();
    }

    @GET
    @Path("/dummy")
    @Produces(MediaType.TEXT_PLAIN)
    public String getDummyServiceDetails() {
        return dummyClient.get();
    }

    @GET
    @Path("/configmap")
    @Produces(MediaType.TEXT_PLAIN)
    public String getConfigMap() {
        return gatewayConfiguration.getMessage();
    }

    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Publisher<Double> stream() {
        return prices;
    }

    //TODO
    @GET
    @Path("/secret")
    public String getSecrets() {
        return null;
    }
}
