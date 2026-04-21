package com.lcwd.user.service.UserService.service;

import com.lcwd.user.service.UserService.Entity.User;
import com.lcwd.user.service.UserService.exception.ResourceNotFoundException;
import com.lcwd.user.service.UserService.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

  public   User saveUser(User user){
     String s = UUID.randomUUID().toString();
     user.setId(s);
      return   userRepo.save(user);
    }

   public List<User> getAllUser(){
       return userRepo.findAll();
    }

   public User findById(String id){
        return userRepo.findById(id).orElseThrow(ResourceNotFoundException::new);
    }
   public User deleteById(String id){
        return userRepo.findById(id).orElse(null);
    }

   public User updateUser(User user){
        if (userRepo.findById(user.getId()).orElse(null) != null) {
            userRepo.delete(user);
        }
        return userRepo.save(user);
    }

    public User getUser(String userId) {
      return userRepo.findById(userId).orElseThrow(ResourceNotFoundException::new);
    }
}
