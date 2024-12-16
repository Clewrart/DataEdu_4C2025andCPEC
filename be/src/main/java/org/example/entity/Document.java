package org.example.entity;

import lombok.Data;

@Data
public class Document {
  private  int id;
  private int experimentId;
  private int userId;
  private String userRole;
  private String saveName;
  private  String originName;
  private String filePath;
  private String createdTime;
}
