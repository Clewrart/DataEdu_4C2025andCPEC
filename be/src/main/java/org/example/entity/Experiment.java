package org.example.entity;

import lombok.Data;

@Data
public class Experiment {

  private int id;
  private String name;
  private String description;
  private int teacherId;
  private String uploadFileType;
  private String judgeUrl;
  private int totalUploadCount;
  private String deadlineTime;
  private String createdTime;
}
