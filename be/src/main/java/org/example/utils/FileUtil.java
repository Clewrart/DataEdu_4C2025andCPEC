package org.example.utils;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

  public static void saveFile(MultipartFile file, String directory, String filename) throws IOException {
    File dir = new File(directory);
    if (!dir.exists()) {
      dir.mkdirs();
    }
    String filePath = directory + "/" + filename;
    File dest = new File(filePath);
    file.transferTo(dest);
  }

  public static void deleteFile(String directory, String filename) {
    String filePath = directory + "/" + filename;
    File file = new File(filePath);
    if (file.exists()) {
      file.delete();
    }
  }
  public static void deleteFile( String filePath) {
    File file = new File(filePath);
    if (file.exists()) {
      file.delete();
    }
  }

  public static File getFile(String directory, String filename) {
    String filePath = directory + "/" + filename;
    return new File(filePath);
  }
}
