package com.lcwd.user.service.UserService.service;

import com.lcwd.user.service.UserService.Entity.User;
import com.lcwd.user.service.UserService.exception.ResourceNotFoundException;
import com.lcwd.user.service.UserService.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    User saveUser(User user){
      return   userRepo.save(user);
    }

    List<User> getAllUser(String name){
       return userRepo.findAll();
    }

    User findById(String id){
        return userRepo.findById(id).orElseThrow(ResourceNotFoundException::new);
    }
    User deleteById(String id){
        return userRepo.findById(id).orElse(null);
    }

    User updateUser(User user){
        if (userRepo.findById(user.getId()).orElse(null) != null) {
            userRepo.delete(user);
        }
        return userRepo.save(user);
    }
}
