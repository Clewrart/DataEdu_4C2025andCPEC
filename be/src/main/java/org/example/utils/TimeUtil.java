package org.example.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

  public static String getCurrentTime() {
    // 获取当前时间
    LocalDateTime currentTime = LocalDateTime.now();

    // 格式化当前时间为指定格式
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    return currentTime.format(formatter);
  }

  public static void main(String[] args) {
    System.out.println("当前时间：" + getCurrentTime());
  }
}
