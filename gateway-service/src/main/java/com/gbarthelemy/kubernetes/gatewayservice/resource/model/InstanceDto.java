package com.gbarthelemy.kubernetes.gatewayservice.resource.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class InstanceDto {

    @JsonProperty("uri")
    String uri;

    @JsonProperty("host")
    String host;

    @JsonProperty("port")
    int port;

    @JsonProperty("scheme")
    String scheme;

    @JsonProperty("instance-id")
    String instanceId;

    @JsonProperty("metadata")
    Map<String,String> metadata;
}
