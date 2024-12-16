package org.example.entity;

import lombok.Data;

@Data
public class ExperimentStudent {

  private int experimentId;
  private int studentId;
  private int uploadCount;
  private float score;
  private String createdTime;
}
