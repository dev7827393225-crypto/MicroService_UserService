package com.lcwd.user.service.UserService.repository;

import com.lcwd.user.service.UserService.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,String> {

}
