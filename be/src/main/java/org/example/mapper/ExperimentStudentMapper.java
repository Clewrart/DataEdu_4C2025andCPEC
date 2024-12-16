package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.entity.ExperimentStudent;

public interface ExperimentStudentMapper extends BaseMapper<ExperimentStudent> {
  @Select("SELECT es.score AS score, s.name as name\n"
      + "FROM experiment_student es \n"
      + "JOIN student s ON es.student_id = s.id \n"
      + "WHERE es.experiment_id  = #{experimentId} \n"
      + "ORDER BY es.score DESC;")
   List<HashMap<String,Object>> getRankList(
      @Param("experimentId") int experimentId);
}
