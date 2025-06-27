package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.entity.Experiment;

import org.example.entity.dto.ExperimentStatsDTO;
import org.example.entity.dto.ScoreDistributionDTO;
import org.example.entity.dto.SubmissionTrendDTO;


public interface ExperimentMapper extends BaseMapper<Experiment> {

  @Select("SELECT "
      + "e.id as id, "
      + "e.name as name, "
      +"e.description as description "
      + "FROM experiment e "
      + "JOIN experiment_student es ON es.experiment_id = e.id "
      + "WHERE es.student_id = #{studentId} "
      + "AND ("
      + "    e.name LIKE CONCAT('%', #{keyword}, '%') OR "
      + "    e.description LIKE CONCAT('%', #{keyword}, '%')"
      + ");")
  List<HashMap<String, Object>> getExperimentsByStudentId(
      @Param("studentId") int studentId,
      @Param("keyword") String keyword
  );

  @Select("SELECT " +
          "COUNT(DISTINCT es.student_id) AS enrolledCount, " +
          "SUM(CASE WHEN es.score = 0 THEN 1 ELSE 0 END) AS submittedCount, " +
          "AVG(CASE WHEN es.score != 0 THEN es.score ELSE NULL END) AS averageScore " +
          "FROM experiment_student es " +
          "WHERE es.experiment_id = #{experimentId}")
  ExperimentStatsDTO selectStatsByExperimentId(Integer experimentId);

  @Select("SELECT " +
          "COUNT(CASE WHEN es.score >= 90 THEN 1 END) AS excellent, " +
          "COUNT(CASE WHEN es.score >= 80 AND es.score < 90 THEN 1 END) AS good, " +
          "COUNT(CASE WHEN es.score >= 70 AND es.score < 80 THEN 1 END) AS medium, " +
          "COUNT(CASE WHEN es.score >= 60 AND es.score < 70 THEN 1 END) AS pass, " +
          "COUNT(CASE WHEN es.score < 60 THEN 1 END) AS fail " +
          "FROM experiment_student es " +
          "WHERE es.experiment_id = #{experimentId}")
  ScoreDistributionDTO selectScoreDistribution(Integer experimentId);

  @Select("SELECT DATE(created_time) AS date, COUNT(*) AS count " +
          "FROM experiment_student " +
          "WHERE experiment_id = #{experimentId} " +
          "GROUP BY DATE(created_time) " +
          "ORDER BY date")
  List<SubmissionTrendDTO> selectSubmissionTrend(Integer experimentId);
}
