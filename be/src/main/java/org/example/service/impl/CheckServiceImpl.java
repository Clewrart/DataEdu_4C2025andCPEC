package org.example.service.impl;

import org.example.entity.dto.*;
import org.example.mapper.DocumentMapper;
import org.example.mapper.ExperimentMapper;
import org.example.service.CheckService;
import org.example.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CheckServiceImpl implements CheckService {

    @Autowired
    private ExperimentMapper experimentMapper;

    @Autowired
    private DocumentMapper documentMapper;

    @Override
    public ResultUtil<ExperimentStatsDTO> getExperimentStats(Integer experimentId) {
        ExperimentStatsDTO stats = experimentMapper.selectStatsByExperimentId(experimentId);
        stats.setDocumentCount(documentMapper.countStudentDocuments(experimentId));
        return ResultUtil.success(stats);
    }

    @Override
    public ResultUtil<ScoreDistributionDTO> getScoreDistribution(Integer experimentId) {
        return ResultUtil.success(experimentMapper.selectScoreDistribution(experimentId));
    }

    @Override
    public ResultUtil<SubmissionTrendDTO> getSubmissionTrend(Integer experimentId) {
        SubmissionTrendDTO trend = new SubmissionTrendDTO();
        // 这里需要转换数据格式
        return ResultUtil.success(trend);
    }

    @Override
    public ResultUtil<Integer> getStudentDocumentCount(Integer experimentId) {
        return ResultUtil.success(documentMapper.countStudentDocuments(experimentId));
    }
}