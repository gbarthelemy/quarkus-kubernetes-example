package com.gbarthelemy.kubernetes.gatewayservice.resource.mapper;

import com.gbarthelemy.kubernetes.gatewayservice.resource.model.InstanceDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GatewayMapper {

    public static InstanceDto toInstanceDto(final ServiceInstance serviceInstance) {
        return InstanceDto.builder()
                .host(serviceInstance.getHost())
                .port(serviceInstance.getPort())
                .uri(serviceInstance.getUri().toString())
                .scheme(serviceInstance.getScheme())
                .instanceId(serviceInstance.getInstanceId())
                .metadata(serviceInstance.getMetadata())
                .build();
    }
}
