package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.HashMap;
import java.util.List;
import org.example.entity.Experiment;
import org.example.mapper.ExperimentMapper;
import org.example.service.ExperimentService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExperimentServiceImpl implements ExperimentService {

  @Autowired
  UserService userService;
  @Autowired
  ExperimentMapper experimentMapper;

  @Override
  public boolean addItem(Experiment experiment) {
    try {
      return experimentMapper.insert(experiment) > 0;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean updateItem(Experiment experiment) {
    try {

      return experimentMapper.updateById(experiment) > 0;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean deleteItem(int id) {

    return experimentMapper.deleteById(id) > 0;

  }

  @Override
  public HashMap<String, Object> getListByTeacherId(int teacherId, String keyword, int page,
      int size) {
    LambdaQueryWrapper<Experiment> experimentLambdaQueryWrapper = new LambdaQueryWrapper<>();
    experimentLambdaQueryWrapper
        .eq(Experiment::getTeacherId, teacherId)
        .like(Experiment::getName, keyword).or()
        .like(Experiment::getDescription, keyword);

    long total = experimentMapper.selectCount(experimentLambdaQueryWrapper);
    experimentLambdaQueryWrapper.last("limit " + (page - 1) * size + "," + size);
    List<Experiment> list = experimentMapper.selectList(experimentLambdaQueryWrapper);
    HashMap<String, Object> result = new HashMap<>();
    result.put("total", total);
    result.put("list", list);
    return result;
  }



  @Override
  public HashMap<String, Object> getListByStudentId(int studentId, String keyword) {
    System.out.println("@@@@@@@@@"+keyword);
    List<HashMap<String, Object>> experiments = experimentMapper.getExperimentsByStudentId(
        studentId, keyword);
    long total =experiments.size();
    HashMap<String, Object> result = new HashMap<>();
    result.put("list",experiments);
    result.put("total",total);
    return result;
  }


  @Override
  public Experiment getItemById(int id) {
    return experimentMapper.selectById(id);
  }

  @Override
  public HashMap<String, Object> getList(String keyword, int page, int size) {
    LambdaQueryWrapper<Experiment> experimentLambdaQueryWrapper = new LambdaQueryWrapper<>();
    experimentLambdaQueryWrapper.like(Experiment::getName, keyword).or()
        .like(Experiment::getDescription, keyword);

    long total = experimentMapper.selectCount(experimentLambdaQueryWrapper);
    experimentLambdaQueryWrapper.last("limit " + (page - 1) * size + "," + size);
    List<Experiment> list = experimentMapper.selectList(experimentLambdaQueryWrapper);
    HashMap<String, Object> result = new HashMap<>();
    result.put("total", total);
    result.put("list", list);
    return result;
  }


}
