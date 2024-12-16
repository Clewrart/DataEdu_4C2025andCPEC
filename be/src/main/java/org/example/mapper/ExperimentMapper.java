package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.entity.Experiment;

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
}
