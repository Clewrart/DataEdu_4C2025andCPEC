package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.entity.Document;
import org.apache.ibatis.annotations.Select;

public interface DocumentMapper extends BaseMapper<Document> {

    @Select("SELECT COUNT(*) FROM document " +
            "WHERE experiment_id = #{experimentId} AND user_role = 'student'")
    Integer countStudentDocuments(Integer experimentId);
}