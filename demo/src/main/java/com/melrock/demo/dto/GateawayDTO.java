package com.melrock.demo.dto;
import com.melrock.demo.models.Gateaway.GateawayType;
import lombok.Data;
@Data
public class GateawayDTO {
    private Long id;
    private String name;
    private GateawayType type;
    private String conditions;
}
