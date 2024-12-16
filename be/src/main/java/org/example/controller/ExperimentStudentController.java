package org.example.controller;


import io.swagger.annotations.Api;
import java.util.List;
import org.example.entity.ExperimentStudent;
import org.example.service.ExperimentStudentService;
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

@RestController
@Api(tags = "学生实验接口")
@RequestMapping("/api/experimentStudent")
public class ExperimentStudentController {

  @Autowired
  ExperimentStudentService experimentStudentService;

  @GetMapping("/list")
  public String list(
      @RequestParam(defaultValue = "", required = false) String keyword,
      @RequestParam(defaultValue = "1", required = false) Integer page,
      @RequestParam(defaultValue = "10", required = false) Integer pageSize
  ) {
    return ResponseUtil.success(experimentStudentService.getList(keyword, page, pageSize));
  }

  @PostMapping("")
  public String add(int experimentId,int studentId) {
    if (experimentStudentService.addItem(experimentId,studentId)){
      return ResponseUtil.success("添加成功");
    }else {
      return ResponseUtil.error("添加失败");
    }

  }

  @PostMapping("/addBatch")
  public String addBatch(@RequestBody List<ExperimentStudent> experimentStudents) {
    if (experimentStudentService.addBatch(experimentStudents)) {
      return ResponseUtil.success("添加成功");
    }
    ;
    return ResponseUtil.success("添加失败");
  }

  @GetMapping("")
  public String get(int id) {
    ExperimentStudent experimentStudent = experimentStudentService.getItemById(id);
    return ResponseUtil.success(experimentStudent);
  }

  @PatchMapping("")
  public String update(ExperimentStudent experimentStudent) {
    experimentStudentService.updateItem(experimentStudent);
    return ResponseUtil.success("修改成功");
  }

  @DeleteMapping("/delete")
  public String delete(int id) {
    experimentStudentService.deleteItem(id);
    return ResponseUtil.success("删除成功");
  }

  @GetMapping("/getItemByExperimentIdAndStudentId")
  public String getItemByExperimentIdAndStudentId(int experimentId, int studentId) {
    return  ResponseUtil.success(experimentStudentService.getItemByExperimentIdAndStudentId(experimentId, studentId));
  }

  @GetMapping("/getRank")
  public String getRank(int experimentId, int studentId) {
    return  ResponseUtil.success(experimentStudentService.getRank(experimentId, studentId));
  }

  @GetMapping("/rankList")
  public String getRank(int experimentId) {
    return  ResponseUtil.success(experimentStudentService.rankList(experimentId));
  }
}
