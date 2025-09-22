package com.melrock.demo.dto;
import com.melrock.demo.models.Gateway.GateawayType;
import lombok.Data;
@Data
public class GatewayDTO {
    private Long id;
    private String name;
    private GateawayType type;
    private String conditions;
}
