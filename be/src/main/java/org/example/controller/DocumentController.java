package org.example.controller;


import io.swagger.annotations.Api;
import java.util.List;
import org.example.entity.Document;
import org.example.service.DocumentService;
import org.example.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("*")
@Api(tags = "文档接口")
@RequestMapping("/api/document")
public class DocumentController {

  @Autowired
  DocumentService documentService;

  @GetMapping("/list")
  public String getDocuments(int experimentId, int userId, String role) {
    List<Document> documents = documentService.getDocuments(experimentId, userId, role);
    return ResponseUtil.success(documents);
  }

  @GetMapping("")
  public String getDocumentById(int id) {
    Document document = documentService.getDocumentById(id);
    return ResponseUtil.success(document);
  }


  @PostMapping("")
  public String uploadDocument(
      @RequestParam("experimentId") int experimentId,
      @RequestParam("userId") int userId,
      @RequestParam("role") String role,
      @RequestParam("files") List<MultipartFile> files
  ) {
    for (MultipartFile file : files) {
      if (!documentService.uploadExperimentDocument(experimentId, userId, role, file)) {
        return ResponseUtil.error("上传失败");
      }
    }
    return ResponseUtil.success("上传成功");

  }

  @DeleteMapping("")
  public String deleteDocument(int id) {
    if (documentService.deleteDocumentById(id)) {
      return ResponseUtil.success("删除成功");
    } else {
      return ResponseUtil.error("删除失败");
    }
  }
}
