package com.gbarthelemy.kubernetes.gatewayservice.resource.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ServiceDto {

    @JsonProperty("service_name")
    String serviceName;

    @JsonProperty("instance_dto_list")
    List<InstanceDto> instanceDtoList;
}
