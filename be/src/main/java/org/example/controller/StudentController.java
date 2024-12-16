package org.example.controller;


import io.swagger.annotations.Api;
import java.util.List;
import org.example.entity.Student;
import org.example.service.StudentService;
import org.example.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api(tags = "学生接口")
@RequestMapping("/api/student")
public class StudentController {

  @Autowired
  StudentService studentService;

  @GetMapping("/list")
  public String list(
      @RequestParam(defaultValue = "", required = false) String keyword,
      @RequestParam(defaultValue = "1", required = false) Integer page,
      @RequestParam(defaultValue = "10", required = false) Integer size
  ) {
    return ResponseUtil.success(studentService.getList(keyword, page, size));
  }

  @PostMapping("")
  public String add(Student student) {
    studentService.addItem(student);
    return ResponseUtil.success("添加成功");
  }


  @GetMapping("")
  public String get(int id) {
    Student student = studentService.getItemById(id);
    return ResponseUtil.success(student);
  }

  @PostMapping("/upload")
  public String upload(MultipartFile excel) {
    if (studentService.upload(excel)) {

      return ResponseUtil.success("导入成功");
    } else {
      return ResponseUtil.error("导入失败,可能有重复数据");
    }
  }

  @PatchMapping("")
  public String update(Student student) {
    studentService.updateItem(student);
    return ResponseUtil.success("修改成功");
  }

  @DeleteMapping("")
  public String delete(int id) {
   if (studentService.deleteItem(id)){;
    return ResponseUtil.success("删除成功");
   }else {
    return ResponseUtil.error("删除失败");
   }
  }

  @DeleteMapping("/delete/batch")
  public String delete(@RequestBody List<Integer> ids) {
    studentService.deleteBatch(ids);
    return ResponseUtil.success("删除成功");
  }

  @PostMapping("/test")
  public  String test( int id){
    return String.valueOf(id);
  }

}
