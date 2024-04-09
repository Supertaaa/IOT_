package com.example.myroom.Repository;

import com.example.myroom.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    Boolean existsUserByUserName(String username);
    User findByUserName(String username);
}
