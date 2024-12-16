package org.example.service;

import java.util.HashMap;
import java.util.List;
import org.example.entity.Student;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface StudentService {

  boolean addItem(Student student);

  Student getItemById(int id);

  HashMap<String, Object> getList(String keyword, int page, int size);

  boolean updateItem(Student student);

  boolean deleteItem(int id);

  void deleteBatch(List<Integer> ids);

  boolean upload(MultipartFile excel);


}
