package com.decentralizer.spreadr.apigateway.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class RoleGatewayDTO {
    private final Set<ControllerGatewayDTO> controller = new HashSet<>();
    private final Set<UserGatewayDTO> user = new HashSet<>();
    private int id;
    private String name;
    private boolean active;
    private LocalDateTime created;
    private LocalDateTime edited;
    private String createdBy;
    private String editedBy;
    private UUID version;

    @Override
    public String toString() {
        return "RoleGatewayDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", active=" + active +
                ", created=" + created +
                ", edited=" + edited +
                ", createdBy='" + createdBy + '\'' +
                ", editedBy='" + editedBy + '\'' +
                ", version=" + version +
                '}';
    }
}

