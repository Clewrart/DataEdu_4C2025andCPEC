package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.entity.dto.*;
import org.example.service.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.example.utils.ResultUtil;

@RestController
@RequestMapping("/api/check")
@Api(tags = "教学检查接口")
public class CheckController {

    @Autowired
    private CheckService checkService;

    @GetMapping("/stats")
    @ApiOperation("获取实验统计数据")
    public ResultUtil<ExperimentStatsDTO> getStats(@RequestParam Integer experimentId) {
        return checkService.getExperimentStats(experimentId);
    }

    @GetMapping("/scoreDistribution")
    @ApiOperation("获取分数分布")
    public ResultUtil<ScoreDistributionDTO> getScoreDistribution(@RequestParam Integer experimentId) {
        return checkService.getScoreDistribution(experimentId);
    }

    @GetMapping("/submissionTrend")
    @ApiOperation("获取提交趋势")
    public ResultUtil<SubmissionTrendDTO> getSubmissionTrend(@RequestParam Integer experimentId) {
        return checkService.getSubmissionTrend(experimentId);
    }

    @GetMapping("/documentCount")
    @ApiOperation("获取学生文档数")
    public ResultUtil<Integer> getDocumentCount(@RequestParam Integer experimentId) {
        return checkService.getStudentDocumentCount(experimentId);
    }
}