package org.example.entity.dto;

import lombok.Data;
import java.util.List;

@Data
public class SubmissionTrendDTO {
    private List<String> dates;   // 日期列表
    private List<Integer> counts; // 每日提交数
}