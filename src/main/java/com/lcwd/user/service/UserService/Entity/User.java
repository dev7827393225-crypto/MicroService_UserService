package com.lcwd.user.service.UserService.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="micro_users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @Column(nullable = false)
    private String id;

   private String name;
   private String email;
   private String about;

   @Transient
   private List<Rating> ratings=new ArrayList<>();

}
