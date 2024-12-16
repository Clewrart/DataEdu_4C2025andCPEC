package org.example.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

  public static String hashPassword(String password) {
    try {
      // 使用 SHA-256 算法创建 MessageDigest 实例
      MessageDigest digest = MessageDigest.getInstance("SHA-256");

      // 将密码转换为字节数组，并计算哈希值
      byte[] hashBytes = digest.digest(password.getBytes());

      // 将字节数组转换为十六进制字符串
      StringBuilder hexString = new StringBuilder();
      for (byte hashByte : hashBytes) {
        String hex = Integer.toHexString(0xff & hashByte);
        if (hex.length() == 1) {
          hexString.append('0');
        }
        hexString.append(hex);
      }

      // 返回十六进制表示的哈希密码
      return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
      // 捕获 NoSuchAlgorithmException 异常
      e.printStackTrace();
      // 返回空字符串或者抛出异常，具体取决于你的需求
      return "";
    }
  }

  public static void main(String[] args) {
    // 测试哈希密码函数
    String password = "admin";
    String hashedPassword = hashPassword(password);
    System.out.println("Hashed Password: " + hashedPassword);
  }
}
