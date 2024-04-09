package com.example.myroom.Repository;

import com.example.myroom.Entities.Juntion;
import com.example.myroom.Entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JuntionRepository extends JpaRepository<Juntion, Integer> {
    public List<Juntion> findAllByUserId(int userId);
}
