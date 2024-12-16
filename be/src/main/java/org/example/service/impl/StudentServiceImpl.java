package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.HashMap;
import java.util.List;
import org.example.entity.Student;
import org.example.entity.User;
import org.example.mapper.StudentMapper;
import org.example.service.StudentService;
import org.example.service.UserService;
import org.example.utils.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StudentServiceImpl implements StudentService {

  @Autowired
  UserService userService;
  @Autowired
  StudentMapper studentMapper;

  @Override
  public boolean addItem(Student student) {
    try {
      String username = student.getNumber(); // 用户名为学号
      String password = username.substring(username.length() - 6); // 密码为身份证号后六位
      String role = "student";
      User user = new User();
      user.setUsername(username);
      user.setPassword(password);
      user.setRole(role);
      userService.addItem(user);
      return studentMapper.insert(student) > 0;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean updateItem(Student student) {
    try {
      Student oldStudent = studentMapper.selectById(student.getId());
      String username = student.getNumber(); // 用户名为学号
      String password = username.substring(username.length() - 6); // 密码为学号后六位
      String role = "student";
      User user = userService.select(oldStudent.getNumber());
      System.out.println(oldStudent.getNumber());
      System.out.println(user);
      user.setUsername(username);
      user.setPassword(password);
      user.setRole(role);
      System.out.println(user);
      userService.updateItem(user);
      return studentMapper.updateById(student) > 0;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean deleteItem(int id) {
    Student student = getItemById(id);
    String username = student.getNumber();
    User user = userService.select(username);
    try{
      if (user != null) {
        userService.deleteItemById(user.getId());
      }
      return studentMapper.deleteById(id) > 0;
    }
    catch (Exception e){
      e.printStackTrace();
      return false;
    }


  }

  @Override
  public void deleteBatch(List<Integer> ids) {
    for (Integer id : ids) {
      deleteItem(id);
    }
  }

  @Override
  public boolean upload(MultipartFile excel) {
    List<Student> students = ExcelUtil.readExcel(excel, Student.class);
    for (Student student : students) {
      student.setId(0);
      if (!addItem(student)) {
        return false;
      }
      ;
    }
    return true;
  }

  @Override
  public Student getItemById(int id) {
    return studentMapper.selectById(id);
  }

  @Override
  public HashMap<String, Object> getList(String keyword, int page, int size) {
    LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
    studentLambdaQueryWrapper.like(Student::getName, keyword).or()
        .like(Student::getNumber, keyword);

    long total = studentMapper.selectCount(studentLambdaQueryWrapper);
    studentLambdaQueryWrapper.last("limit " + (page - 1) * size + "," + size);
    List<Student> list = studentMapper.selectList(studentLambdaQueryWrapper);

    HashMap<String, Object> result = new HashMap<>();
    result.put("total", total);
    result.put("list", list);
    return result;
  }


}
