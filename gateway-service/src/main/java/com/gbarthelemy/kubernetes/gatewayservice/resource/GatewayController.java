package com.gbarthelemy.kubernetes.gatewayservice.resource;

import com.gbarthelemy.kubernetes.gatewayservice.configuration.GatewayProperties;
import com.gbarthelemy.kubernetes.gatewayservice.configuration.GatewaySecretProperties;
import com.gbarthelemy.kubernetes.gatewayservice.resource.mapper.GatewayMapper;
import com.gbarthelemy.kubernetes.gatewayservice.resource.model.ServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GatewayController {

    private static final String DUMMY_SERVICE_URL = "http://quarkus-k8s-dummy-service:8080";

    private final DiscoveryClient discoveryClient;
    private final GatewayProperties gatewayProperties;
    private final GatewaySecretProperties gatewaySecretProperties;

    @GetMapping
    public List<ServiceDto> getServicesDetails() {

        return this.discoveryClient.getServices().stream()
                .map(serviceName -> ServiceDto.builder()
                        .serviceName(serviceName)
                        .instanceDtoList(
                                this.discoveryClient.getInstances(serviceName).stream()
                                        .map(GatewayMapper::toInstanceDto)
                                        .collect(Collectors.toList())
                        ).build())
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/dummy")
    public String getDummyServiceDetails() {
        final RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<String> response = restTemplate.getForEntity(DUMMY_SERVICE_URL, String.class);
        return response.getBody();
    }

    @GetMapping(path = "/configmap")
    public String getConfigMap() {
        return String.format("message: %s", gatewayProperties.getMessage());
    }

    @GetMapping(path = "/secret")
    public String getSecrets() {
        return String.format("name: %s%npassword: %s", gatewaySecretProperties.getName(), gatewaySecretProperties
                .getPassword());
    }
}
