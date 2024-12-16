package org.example.service;

import java.util.HashMap;
import org.example.entity.Teacher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface TeacherService {
  boolean addItem(Teacher teacher);

  Teacher getItemById(int id);
  HashMap<String, Object> getList();
  HashMap<String, Object> getList(String keyword, int page, int size);

  boolean updateItem(Teacher teacher);

  boolean deleteItem(int id);



}
