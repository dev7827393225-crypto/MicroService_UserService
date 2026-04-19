package com.lcwd.user.service.UserService.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="micro_users")
public class User {
    @Id
    @Column(nullable = false)
    private String id;

    String name;
    String email;
    String about;

}
