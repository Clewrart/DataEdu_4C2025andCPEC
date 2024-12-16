package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.HashMap;
import java.util.List;
import org.example.entity.ExperimentStudent;
import org.example.mapper.ExperimentStudentMapper;
import org.example.service.ExperimentStudentService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExperimentStudentServiceImpl implements ExperimentStudentService {

  @Autowired
  UserService userService;
  @Autowired
  ExperimentStudentMapper experimentStudentMapper;

  @Override
  public boolean addItem(ExperimentStudent experimentStudent) {
    try {
      return experimentStudentMapper.insert(experimentStudent) > 0;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean addBatch(List<ExperimentStudent> experimentStudents) {
    for (ExperimentStudent experimentStudent : experimentStudents) {
      if (!addItem(experimentStudent)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean updateItem(ExperimentStudent experimentStudent) {
    try {
      LambdaQueryWrapper<ExperimentStudent> experimentStudentLambdaQueryWrapper = new LambdaQueryWrapper<>();
      experimentStudentLambdaQueryWrapper
          .eq(ExperimentStudent::getExperimentId, experimentStudent.getExperimentId())
          .eq(ExperimentStudent::getStudentId, experimentStudent.getStudentId());
      return experimentStudentMapper.update(experimentStudent, experimentStudentLambdaQueryWrapper)
          > 0;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean deleteItem(int id) {

    return experimentStudentMapper.deleteById(id) > 0;

  }

  @Override
  public int getRank( int experimentId,int studentId) {
    LambdaQueryWrapper<ExperimentStudent> experimentStudentLambdaQueryWrapper = new LambdaQueryWrapper<>();
    experimentStudentLambdaQueryWrapper
        .eq(ExperimentStudent::getExperimentId, experimentId)
        .orderByDesc(ExperimentStudent::getScore);
    List<ExperimentStudent> experimentStudents = experimentStudentMapper.selectList(
        experimentStudentLambdaQueryWrapper);
    for (int i = 0; i < experimentStudents.size(); i++) {
      System.out.println(experimentStudents.get(i).getStudentId());
      System.out.println(studentId);
      if (experimentStudents.get(i).getStudentId() == studentId) {
        return i + 1;
      }
    }
    return -1;
  }

  @Override
  public boolean addItem(int experimentId, int studentId) {
    ExperimentStudent experimentStudent = new ExperimentStudent();
    experimentStudent.setExperimentId(experimentId);
    experimentStudent.setStudentId(studentId);
    return experimentStudentMapper.insert(experimentStudent) > 0;
  }

  @Override
  public ExperimentStudent getItemByExperimentIdAndStudentId(int experimentId, int studentId) {
    LambdaQueryWrapper<ExperimentStudent> experimentStudentLambdaQueryWrapper = new LambdaQueryWrapper<>();
    experimentStudentLambdaQueryWrapper
        .eq(ExperimentStudent::getExperimentId, experimentId)
        .eq(ExperimentStudent::getStudentId, studentId);
    return experimentStudentMapper.selectOne(experimentStudentLambdaQueryWrapper);
  }

  @Override
  public  List<HashMap<String,Object>>rankList(int experimentId) {

    return experimentStudentMapper.getRankList(experimentId);
  }


  @Override
  public ExperimentStudent getItemById(int id) {
    return experimentStudentMapper.selectById(id);
  }


  @Override
  public HashMap<String, Object> getList(String keyword, int page, int size) {
    LambdaQueryWrapper<ExperimentStudent> experimentStudentLambdaQueryWrapper = new LambdaQueryWrapper<>();
    experimentStudentLambdaQueryWrapper.like(ExperimentStudent::getExperimentId, keyword);

    long total = experimentStudentMapper.selectCount(experimentStudentLambdaQueryWrapper);
    experimentStudentLambdaQueryWrapper.last("limit " + (page - 1) * size + "," + size);
    List<ExperimentStudent> list = experimentStudentMapper.selectList(
        experimentStudentLambdaQueryWrapper);

    HashMap<String, Object> result = new HashMap<>();
    result.put("total", total);
    result.put("list", list);
    return result;
  }


}
