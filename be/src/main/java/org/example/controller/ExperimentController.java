package org.example.controller;


import io.swagger.annotations.Api;
import org.example.entity.Experiment;
import org.example.service.ExperimentService;
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
@Api(tags = "实验接口")
@RequestMapping("/api/experiment")
public class ExperimentController {

  @Autowired
  ExperimentService experimentService;

  @GetMapping("/list")
  public String list(
      @RequestParam(defaultValue = "", required = false) String keyword,
      @RequestParam(defaultValue = "1", required = false) Integer page,
      @RequestParam(defaultValue = "10", required = false) Integer size
  ) {
    return ResponseUtil.success(experimentService.getList(keyword, page, size));
  }
  @GetMapping("/listByTeacherId")
  public String listByTeacherId(
      int teacherId,
      @RequestParam(defaultValue = "", required = false) String keyword,
      @RequestParam(defaultValue = "1", required = false) Integer page,
      @RequestParam(defaultValue = "10", required = false) Integer size
  ) {
    return ResponseUtil.success(experimentService.getListByTeacherId(teacherId,keyword, page, size));
  }

  @GetMapping("/listByStudentId")
  public String listByStudentId(
      int studentId,
      @RequestParam(defaultValue = "", required = false) String keyword
  ) {
    return ResponseUtil.success(experimentService.getListByStudentId(studentId,keyword));
  }

  @PostMapping("")
  public  String add(Experiment experiment){
    experimentService.addItem(experiment);
    return ResponseUtil.success("添加成功");
  }

  @GetMapping("")
  public String get(int id) {
    Experiment experiment = experimentService.getItemById(id);
    return ResponseUtil.success(experiment);
  }

  @PatchMapping("")
  public String update(Experiment experiment) {
    experimentService.updateItem(experiment);
    return ResponseUtil.success("修改成功");
  }

  @DeleteMapping("")
  public String delete(int id) {
    experimentService.deleteItem(id);
    return ResponseUtil.success("删除成功");
  }

}
