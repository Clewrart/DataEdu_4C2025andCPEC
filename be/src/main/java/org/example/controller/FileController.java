package org.example.controller;

import io.swagger.annotations.Api;
import org.example.utils.FileUtil;
import org.example.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@Api(tags = "文件接口")
@RequestMapping("/api/files")
public class FileController {

  @Value("${file.dir}")
  String fileDir;

  @GetMapping("")
  public ResponseEntity<InputStreamResource> getFile(@RequestParam String fileName) {
    try {
      File file = FileUtil.getFile(fileDir, fileName);
      if (file == null || !file.exists()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
      }

      FileInputStream fileInputStream = new FileInputStream(file);
      InputStreamResource resource = new InputStreamResource(fileInputStream);

      HttpHeaders headers = new HttpHeaders();
      headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

      return ResponseEntity.ok()
          .headers(headers)
          .contentLength(file.length())
          .contentType(MediaType.APPLICATION_OCTET_STREAM)
          .body(resource);

    } catch (FileNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }
}
