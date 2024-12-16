package org.example.utils;

import java.util.HashMap;

public class ResponseUtil {



  // Static methods for common responses
  public static String success(Object data) {
    HashMap<Object, Object> res = new HashMap<>();
    res.put("code",200);
    res.put("msg","success");
    res.put("data",data);
    return JsonUtil.toJson(res);
  }
  public static String success(String msg) {
    HashMap<Object, Object> res = new HashMap<>();
    res.put("code",200);
    res.put("msg",msg);
    return JsonUtil.toJson(res);
  }
  public static String success() {
    HashMap<Object, Object> res = new HashMap<>();
    res.put("code",200);
    res.put("msg","成功");
    return JsonUtil.toJson(res);
  }

  public static String error(String msg) {
    HashMap<Object, Object> res = new HashMap<>();
    res.put("code",500);
    res.put("msg",msg);
    return  JsonUtil.toJson(res);

  }
  public static String error() {
    HashMap<Object, Object> res = new HashMap<>();
    res.put("code",500);
    res.put("msg","error");
    return  JsonUtil.toJson(res);

  }

  public static void main(String[] args) {
    System.out.println(success(null));

  }
}
