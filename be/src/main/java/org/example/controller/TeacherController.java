package org.example.controller;


import io.swagger.annotations.Api;
import org.example.entity.Teacher;
import org.example.service.TeacherService;
import org.example.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "教师接口")
@RequestMapping("/api/teacher")
public class TeacherController {

  @Autowired
  TeacherService teacherService;

  @GetMapping("/list")
  public String list(
      @RequestParam(defaultValue = "", required = false) String keyword,
      @RequestParam(defaultValue = "1", required = false) Integer page,
      @RequestParam(defaultValue = "10", required = false) Integer pageSize
  ) {
    return ResponseUtil.success(teacherService.getList(keyword, page, pageSize));
  }

  @PostMapping("")
  public  String add(Teacher teacher){
    teacherService.addItem(teacher);
    return ResponseUtil.success("添加成功");
  }

  @GetMapping("")
  public String get(int id) {
    Teacher teacher = teacherService.getItemById(id);
    return ResponseUtil.success(teacher);
  }

  @PatchMapping("")
  public String update(Teacher teacher) {
    teacherService.updateItem(teacher);
    return ResponseUtil.success("修改成功");
  }

  @DeleteMapping("")
  public String delete(int id) {
    teacherService.deleteItem(id);
    return ResponseUtil.success("删除成功");
  }

  @GetMapping("/all")
  public  String all(){
    return ResponseUtil.success(teacherService.getList());
  }

}
