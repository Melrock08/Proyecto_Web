package com.melrock.demo.dto;

import lombok.Data;
import java.util.List;

@Data
public class DiagramDTO {
    private List<ActivityDTO> activities;
    private List<GatewayDTO> gateways;
    private List<EdgeDTO> edges;
}
