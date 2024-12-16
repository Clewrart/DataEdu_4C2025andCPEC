package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.HashMap;
import java.util.List;
import org.example.entity.Teacher;
import org.example.entity.User;
import org.example.mapper.TeacherMapper;
import org.example.service.TeacherService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {

  @Autowired
  UserService userService;
  @Autowired
  TeacherMapper teacherMapper;

  @Override
  public boolean addItem(Teacher teacher) {
    try {
      String username = teacher.getPhone(); // 用户名为手机号
      String password = username.substring(username.length() - 6); // 密码为手机号号后六位
      String role = "teacher";
      User user = new User();
      user.setUsername(username);
      user.setPassword(password);
      user.setRole(role);
      userService.addItem(user);
      return teacherMapper.insert(teacher) > 0;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean updateItem(Teacher teacher) {
    try {
      Teacher oldTeacher = teacherMapper.selectById(teacher.getId());
      String username = teacher.getPhone(); // 用户名为学号
      String password = username.substring(username.length() - 6); // 密码为身份证号后六位
      String role = "teacher";
      User user = userService.select(oldTeacher.getPhone());
      user.setUsername(username);
      user.setPassword(password);
      user.setRole(role);
      userService.updateItem(user);
      return teacherMapper.updateById(teacher) > 0;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean deleteItem(int id) {
    Teacher teacher = getItemById(id);
    String username = teacher.getPhone();
    User user = userService.select(username);
    userService.deleteItemById(user.getId());
    return teacherMapper.deleteById(id) > 0;

  }


  @Override
  public Teacher getItemById(int id) {
    return teacherMapper.selectById(id);
  }

  @Override
  public HashMap<String, Object> getList() {
    List<Teacher> list = teacherMapper.selectList(null);
    int total = list.size();
    HashMap<String, Object> result = new HashMap<>();
    result.put("total", total);
    result.put("list", list);
    return result;
  }

  @Override
  public HashMap<String, Object> getList(String keyword, int page, int size) {
    LambdaQueryWrapper<Teacher> teacherLambdaQueryWrapper = new LambdaQueryWrapper<>();
    teacherLambdaQueryWrapper.like(Teacher::getName, keyword).or().like(Teacher::getPhone, keyword);



    long total = teacherMapper.selectCount(teacherLambdaQueryWrapper);
    teacherLambdaQueryWrapper.last("limit " + (page - 1) * size + "," + size);
    List<Teacher> list = teacherMapper.selectList(teacherLambdaQueryWrapper);

    HashMap<String, Object> result = new HashMap<>();
    result.put("total", total);
    result.put("list", list);
    return result;
  }


}
