package org.example.entity.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ExperimentStatsDTO {
    private Integer enrolledCount;       // 参与学生数
    private Integer submittedCount;      // 已提交学生数
    private Double averageScore;         // 平均分
    private Integer documentCount;       // 学生文档总数
    private String completionRate;       // 完成率
    private LocalDateTime deadline;      // 截止时间
}