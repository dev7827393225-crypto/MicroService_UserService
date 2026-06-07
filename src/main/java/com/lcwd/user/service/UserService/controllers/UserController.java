package com.lcwd.user.service.UserService.controllers;

import com.lcwd.user.service.UserService.Entity.User;
import com.lcwd.user.service.UserService.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {



    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User user1=userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }
    @GetMapping("/{userId}")
    //@CircuitBreaker(name="ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")
  //  @Retry(name = "ratingHotelService",fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name="userRateLimiter",fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId) {
        log.info("Retry count: {}", retryCount);
        retryCount++;
        return ResponseEntity.ok(userService.findById(userId));
    }

    int retryCount=1;

    //fallback method for circuitbreaker
    public ResponseEntity<User> ratingHotelFallback(String userId,Exception ex) {
      //  log.info("Fallback is executed becouse server is down :"+ex.getMessage());

        User user = User.builder()
                .id(userId)
                .email("dummy@gmail.com")
                .name("Dummy")
                .about("this is a dummy user create due to service down")
                .id("")
                .build();
        return new ResponseEntity<>(user,HttpStatus.OK);
    }






    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUser());
    }


}
