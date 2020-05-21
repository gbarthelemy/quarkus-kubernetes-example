package com.gbarthelemy.kubernetes.gatewayservice.resource.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ServiceDto {

    @JsonProperty("service-name")
    String serviceName;

    @JsonProperty("instances")
    List<InstanceDto> instanceDtoList;
}
