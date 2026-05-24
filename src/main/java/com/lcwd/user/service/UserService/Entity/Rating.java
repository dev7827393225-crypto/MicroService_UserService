package com.lcwd.user.service.UserService.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Rating {

 private String ratingId;
    private String userId;
    private String hotelId;
    private int rating;
    private String feedback;

    @Transient
    private Hotel hotel;
}