package com.decentralizer.spreadr.apiGateway.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class RoleGatewayDTO {
    private int id;
    private String name;
    private Set<ControllerGatewayDTO> controllerGatewayDTOS = new HashSet<>();
    private Set<UserGatewayDTO> userGatewayDTOS = new HashSet<>();
    private boolean active;
    private LocalDateTime created;
    private LocalDateTime edited;
    private String createdBy;
    private String editedBy;
    private UUID version;

}

