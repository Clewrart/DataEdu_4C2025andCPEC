package org.example.utils;

import com.google.gson.Gson;
import java.util.HashMap;

public class JsonUtil {
  private static final Gson gson= new Gson();
  public static String toJson(Object object){
    return gson.toJson(object);
  }

  public static void main(String[] args) {
    // 示例用法
    HashMap<Object, Object> hashMap = new HashMap<>();
    hashMap.put("name","genbesys");
    System.out.println(toJson(hashMap));
  }
}

