package com.lcwd.user.service.UserService.service;

//import com.lcwd.hotel.HotelService.entity.Hotel;
import com.lcwd.user.service.UserService.Entity.Rating;
import com.lcwd.user.service.UserService.Entity.User;
import com.lcwd.user.service.UserService.exception.ResourceNotFoundException;
import com.lcwd.user.service.UserService.external.services.HotelService;
import com.lcwd.user.service.UserService.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    private final Logger logger = LoggerFactory.getLogger(UserService.class);


    public User saveUser(User user) {
        String id = UUID.randomUUID().toString();
        user.setId(id);
        return userRepo.save(user);
    }


    public List<User> getAllUser() {
        return userRepo.findAll();
    }


    public User findById(String id) {
        // 1. Fetch user or throw 404
        User user = userRepo.findById(id)
                .orElseThrow(ResourceNotFoundException::new);


        logger.info("Fetching ratings for userId: {}", user.getId());
        Rating[] ratingsOfUsers = restTemplate.getForObject(
                "http://RATING-SERVICE/ratings/users/" + user.getId(),
                Rating[].class
        );


        if (ratingsOfUsers == null) {
            logger.warn("No ratings returned for userId: {}", user.getId());
            user.setRatings(new ArrayList<>());
            return user;
        }

        logger.info("Ratings fetched: {}", ratingsOfUsers.length);


        List<Rating> ratingList = Arrays.stream(ratingsOfUsers)
                .map(rating -> {

                    logger.info("Fetching hotel for hotelId: {}", rating.getHotelId());
//                    ResponseEntity<Hotel> forEntity = restTemplate.getForEntity(
//                            "http://HOTELSERVICE/hotels/" + rating.getHotelId(),
//                            Hotel.class
//                    );

                    com.lcwd.user.service.UserService.Entity.Hotel hotel = hotelService.getHotel(rating.getHotelId());
                    rating.setHotel(hotel);
                    return rating;
                })
                .collect(Collectors.toList());

        user.setRatings(ratingList);
        return user;
    }


    public User deleteById(String id) {
        User user = userRepo.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        userRepo.deleteById(id);
        return user;
    }


    public User updateUser(User user) {
        // Ensure the user exists before updating
        userRepo.findById(user.getId())
                .orElseThrow(ResourceNotFoundException::new);
        return userRepo.save(user);
    }

    public User getUserBasic(String userId) {
        return userRepo.findById(userId)
                .orElseThrow(ResourceNotFoundException::new);
    }
}