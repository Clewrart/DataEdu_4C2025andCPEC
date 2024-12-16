package org.example.service.impl;

import com.auth0.jwt.interfaces.Claim;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.example.entity.Student;
import org.example.entity.Teacher;
import org.example.entity.User;
import org.example.mapper.StudentMapper;
import org.example.mapper.TeacherMapper;
import org.example.mapper.UserMapper;
import org.example.service.UserService;
import org.example.utils.PasswordUtil;
import org.example.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  UserMapper userMapper;
  @Autowired
  StudentMapper studentMapper;
  @Autowired
  TeacherMapper teacherMapper;

  @Override
  public User select(int id) {
    return userMapper.selectById(id);
  }

  @Override
  public User select(String username, String password) {
    String hashedPassword = PasswordUtil.hashPassword(password);
    LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
    userLambdaQueryWrapper
        .eq(User::getUsername, username)
        .eq(User::getPassword, hashedPassword);
    return userMapper.selectOne(userLambdaQueryWrapper);
  }

  @Override
  public User select(String username) {
    LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
    userLambdaQueryWrapper
        .eq(User::getUsername, username);
    return userMapper.selectOne(userLambdaQueryWrapper);
  }

  @Override
  public Boolean addItem(User user) {
    user.setPassword(PasswordUtil.hashPassword(user.getPassword()));
    return userMapper.insert(user) > 0;
  }

  @Override
  public Boolean updateItem(User user) {
    System.out.println(user);
    User originalUser = userMapper.selectById(user.getId());
    originalUser.setUsername(user.getUsername());
    if (user.getPassword() != null && !user.getPassword().equals("")) {
      originalUser.setPassword(PasswordUtil.hashPassword(user.getPassword()));
    }
    originalUser.setRole(user.getRole());
    return userMapper.updateById(originalUser) > 0;
  }


  @Override
  public Boolean deleteItemById(int id) {
    return userMapper.deleteById(id) > 0;
  }

  @Override
  public HashMap<String, Object> listItem(String keyword, int page, int size) {
    LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
    userLambdaQueryWrapper
        .like(User::getUsername, keyword).or()
        .like(User::getRole, keyword);
    Long total = userMapper.selectCount(userLambdaQueryWrapper);
    userLambdaQueryWrapper.last("limit " + (page - 1) * size + "," + size);
    List<User> list = userMapper.selectList(userLambdaQueryWrapper);
    HashMap<String, Object> hashMap = new HashMap<>();
    hashMap.put("list", list);
    hashMap.put("total", total);
    return hashMap;
  }

  @Override
  public HashMap<String, Object> getUserInfo(String token) {
    Map<String, Claim> payload = TokenUtil.parseToken(token);
    String username = payload.get("username").asString();
    String role = payload.get("role").asString();
    HashMap<String, Object> info = new HashMap<>();
    info.put("role", role);
    info.put("username", username);
    if (role.equals("student")) {
      LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
      studentLambdaQueryWrapper.eq(Student::getNumber, username);
      Student student = studentMapper.selectOne(studentLambdaQueryWrapper);
      info.put("id",student.getId());
      info.put("name", student.getName());
    } else if (role.equals("teacher")) {
      LambdaQueryWrapper<Teacher> teacherLambdaQueryWrapper = new LambdaQueryWrapper<>();
      teacherLambdaQueryWrapper.eq(Teacher::getPhone, username);
      Teacher teacher = teacherMapper.selectOne(teacherLambdaQueryWrapper);
      info.put("id",teacher.getId());
      info.put("name", teacher.getName());
    } else if (role.equals("admin")) {
      info.put("name", "admin");
    }
    return info;
  }
}
