package org.example.utils;

import lombok.Data;

/**
 * 统一API响应封装工具类
 * @param <T> 数据类型
 */
@Data
public class ResultUtil<T> {
  private int code;    // 状态码 (200表示成功)
  private String msg;  // 返回消息
  private T data;      // 返回数据

  // 私有构造方法
  private ResultUtil() {}

  // 成功响应（带数据）
  public static <T> ResultUtil<T> success(T data) {
    ResultUtil<T> result = new ResultUtil<>();
    result.setCode(200);
    result.setMsg("success");
    result.setData(data);
    return result;
  }

  // 成功响应（无数据）
  public static <T> ResultUtil<T> success() {
    return success(null);
  }

  // 错误响应
  public static <T> ResultUtil<T> error(String message) {
    return error(500, message);
  }

  // 错误响应（自定义状态码）
  public static <T> ResultUtil<T> error(int code, String message) {
    ResultUtil<T> result = new ResultUtil<>();
    result.setCode(code);
    result.setMsg(message);
    return result;
  }
}