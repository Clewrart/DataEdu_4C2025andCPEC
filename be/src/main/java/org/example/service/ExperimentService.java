package org.example.service;

import java.util.HashMap;
import org.example.entity.Experiment;
import org.springframework.stereotype.Service;

@Service
public interface ExperimentService {

  boolean addItem(Experiment experiment);

  Experiment getItemById(int id);

  HashMap<String, Object> getList(String keyword, int page, int size);

  boolean updateItem(Experiment experiment);

  boolean deleteItem(int id);

  HashMap<String, Object> getListByTeacherId(int teacherId, String keyword, int page, int size);
  HashMap<String, Object> getListByStudentId(int studentId, String keyword);


}
