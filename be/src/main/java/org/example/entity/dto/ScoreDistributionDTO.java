package org.example.entity.dto;

import lombok.Data;

@Data
public class ScoreDistributionDTO {
    private Integer excellent;  // 90-100
    private Integer good;       // 80-89
    private Integer medium;     // 70-79
    private Integer pass;       // 60-69
    private Integer fail;       // <60
}