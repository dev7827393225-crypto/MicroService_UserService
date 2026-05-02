package com.lcwd.user.service.UserService.service;

import com.lcwd.user.service.UserService.Entity.Rating;
import com.lcwd.user.service.UserService.Entity.User;
import com.lcwd.user.service.UserService.exception.ResourceNotFoundException;
import com.lcwd.user.service.UserService.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
//import java.util.logging.Logger;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    private Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private RestTemplate restTemplate;

  public   User saveUser(User user){
     String s = UUID.randomUUID().toString();
     user.setId(s);
      return   userRepo.save(user);
    }

   public List<User> getAllUser(){
       return userRepo.findAll();
    }

   public User findById(String id){

        User user=userRepo.findById(id).orElseThrow(ResourceNotFoundException::new);

       ArrayList<Rating> ratingsOfUsers = restTemplate.getForObject("http://localhost:8083/ratings/users/"+user.getId(), ArrayList.class);
       logger.info("{}",ratingsOfUsers);

       user.setRatings(ratingsOfUsers);
        return user;
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
