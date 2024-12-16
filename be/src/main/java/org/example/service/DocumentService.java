package org.example.service;

import java.util.List;
import org.example.entity.Document;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface DocumentService {
   List<Document> getDocuments(int experimentId, int userId,String role);
   boolean uploadExperimentDocument(int experimentId, int userId,String role,MultipartFile uploadFile);
   boolean deleteDocumentById(int id);
   Document getDocumentById(int id);
}
