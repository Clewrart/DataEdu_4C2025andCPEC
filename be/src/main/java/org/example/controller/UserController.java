package org.example.controller;

import io.swagger.annotations.Api;
import java.util.HashMap;
import org.example.entity.User;
import org.example.service.UserService;
import org.example.utils.ResponseUtil;
import org.example.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户接口")
@RestController
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  UserService userService;

  @PostMapping("/login")
  public String login(String username, String password) {
    User user = userService.select(username, password);
    HashMap<Object, Object> res = new HashMap<>();
    if (user != null) {
      HashMap<String, Object> payload = new HashMap<>();
      payload.put("username", user.getUsername());
      payload.put("role", user.getRole());
      String token = TokenUtil.getToken(payload);
      res.put("token", token);
      return ResponseUtil.success(res);
    } else {
      return ResponseUtil.error("用户名或密码错误");
    }
  }

  @PostMapping("/register")
  public String register(User user) {
    if (userService.addItem(user)) {
      return ResponseUtil.success();
    } else {
      return ResponseUtil.error();
    }
  }

  @PostMapping("")
  public String addItem(User user) {
    if (userService.addItem(user)) {
      return ResponseUtil.success();
    } else {
      return ResponseUtil.error();
    }
  }

  @PatchMapping("")
  public String updateItem(@RequestBody  User user) {
    if (userService.updateItem(user)) {
      return ResponseUtil.success();
    } else {
      return ResponseUtil.error();
    }
  }

  @DeleteMapping("")
  public String deleteItemById(int id) {
    if (userService.deleteItemById(id)) {
      return ResponseUtil.success();
    } else {
      return ResponseUtil.error();
    }
  }

  @GetMapping("")
  public String getItem(int id){
    return ResponseUtil.success(userService.select(id));
  }
  @GetMapping("/list")
  public String listItem(
      @RequestParam(defaultValue = "") String keyword,
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int size
  ) {
    return  ResponseUtil.success(userService.listItem(keyword, page, size));
  }

  @GetMapping("/info")
  public String info(String token){
    HashMap<String, Object> userInfo = userService.getUserInfo(token);
    return  ResponseUtil.success(userInfo);
  }
}
