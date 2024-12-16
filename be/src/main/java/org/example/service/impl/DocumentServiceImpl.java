package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.List;
import java.util.UUID;
import org.example.entity.Document;
import org.example.mapper.DocumentMapper;
import org.example.service.DocumentService;
import org.example.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentServiceImpl implements DocumentService {

  @Autowired
  DocumentMapper documentMapper;
  @Value("${file.dir}")
  String fileDir;

  @Override
  public List<Document> getDocuments(int experimentId, int userId, String role) {
    LambdaQueryWrapper<Document> documentLambdaQueryWrapper = new LambdaQueryWrapper<>();
    documentLambdaQueryWrapper
        .eq(Document::getExperimentId, experimentId)
        .eq(Document::getUserId, userId)
        .eq(Document::getUserRole, role);
    return documentMapper.selectList(documentLambdaQueryWrapper);
  }

  @Override
  public boolean uploadExperimentDocument(
      int experimentId,
      int userId,
      String role,
      MultipartFile file) {
    try {
      // 获取原始文件名
      String originalName = file.getOriginalFilename();

      // 确保原始文件名不为空
      if (originalName == null || originalName.isEmpty()) {
        throw new IllegalArgumentException("File name is empty");
      }

      // 获取文件扩展名
      String extension = "";
      int dotIndex = originalName.lastIndexOf('.');
      if (dotIndex > 0) {
        extension = originalName.substring(dotIndex); // 包含点符号
      }

      // 生成唯一文件名
      String uuid = UUID.randomUUID().toString();
      String saveName = role + "-" + uuid + extension;

      // 保存文件
      FileUtil.saveFile(file, fileDir, saveName);

      // 创建并保存 Document 对象的逻辑（需要根据你的实际需求实现）
      Document document = new Document();

      document.setExperimentId(experimentId);
      document.setUserId(userId);
      document.setUserRole(role);
      document.setOriginName(originalName);
      document.setSaveName(saveName);
      document.setFilePath(fileDir + "/" + saveName);
      // 保存 document 到数据库的逻辑 (例如：documentRepository.save(document))
      documentMapper.insert(document);

      return true; // 上传成功
    } catch (Exception e) {
      e.printStackTrace();
      return false; // 上传失败
    }
  }

  @Override
  public boolean deleteDocumentById(int id) {
    Document document = documentMapper.selectById(id);
    String filePath = document.getFilePath();
    try {

      FileUtil.deleteFile(filePath);
      documentMapper.deleteById(id);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public Document getDocumentById(int id) {
    return documentMapper.selectById(id);
  }
}
