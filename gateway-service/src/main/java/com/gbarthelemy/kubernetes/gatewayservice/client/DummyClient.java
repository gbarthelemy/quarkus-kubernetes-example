package com.gbarthelemy.kubernetes.gatewayservice.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
@RegisterRestClient(configKey = "dummy-api")
public interface DummyClient {

    @GET
    @Produces("text/plain")
    String get();
}
