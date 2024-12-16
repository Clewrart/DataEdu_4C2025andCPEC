package org.example.service;

import java.util.HashMap;
import java.util.List;
import org.example.entity.ExperimentStudent;
import org.springframework.stereotype.Service;

@Service
public interface ExperimentStudentService {
  boolean addItem(ExperimentStudent experimentStudent);
  boolean addBatch(List<ExperimentStudent> experimentStudents);
  ExperimentStudent getItemById(int id);

  HashMap<String, Object> getList(String keyword, int page, int size);

  boolean updateItem(ExperimentStudent experimentStudent);

  boolean deleteItem(int id);

  int getRank( int experimentId,int studentId);

  boolean addItem(int experimentId, int studentId);

  ExperimentStudent getItemByExperimentIdAndStudentId(int experimentId, int studentId);

  List<HashMap<String,Object>> rankList(int experimentId);

}
