package org.example.service;

import java.util.HashMap;
import org.example.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
  User select(int id);
  User select(String username,String password);
  User select(String username);

  Boolean addItem(User user);

  Boolean updateItem(User user);

  Boolean deleteItemById(int id);

  HashMap<String,Object> listItem(String keyword,int page,int size);

  HashMap<String,Object> getUserInfo(String token);
}
