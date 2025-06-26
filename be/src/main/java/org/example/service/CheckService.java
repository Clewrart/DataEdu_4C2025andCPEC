package org.example.service;

import org.example.entity.dto.*;
import org.example.utils.ResultUtil;

public interface CheckService {
    ResultUtil<ExperimentStatsDTO> getExperimentStats(Integer experimentId);
    ResultUtil<ScoreDistributionDTO> getScoreDistribution(Integer experimentId);
    ResultUtil<SubmissionTrendDTO> getSubmissionTrend(Integer experimentId);
    ResultUtil<Integer> getStudentDocumentCount(Integer experimentId);
}