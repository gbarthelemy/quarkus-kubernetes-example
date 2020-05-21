package com.gbarthelemy.kubernetes;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Path("/")
public class DummyResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get() throws UnknownHostException {
        final String backLine = "<br/>";

        return "Host: " + InetAddress.getLocalHost().getHostName() + backLine +
                "IP: " + InetAddress.getLocalHost().getHostAddress() + backLine +
                "Type: " + "Dummy service" + backLine;
    }
}
