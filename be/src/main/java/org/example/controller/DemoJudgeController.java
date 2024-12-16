package org.example.controller;

import io.swagger.annotations.Api;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "评判的测试接口")
@RestController
@CrossOrigin("*")
@RequestMapping("/api/judge")
public class DemoJudgeController {
  @PostMapping("")
  public HashMap<String,Object> judge(@RequestBody List<MultipartFile> files){
    HashMap<String, Object> res = new HashMap<>();
    // 产生一个随机分数
    Random random = new Random();
    int score =50+ random.nextInt(51);
    res.put("score",score);
    return res;

  }
}
