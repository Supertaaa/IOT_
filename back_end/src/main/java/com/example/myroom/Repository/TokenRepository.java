package com.example.myroom.Repository;

import com.example.myroom.Entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value = "SELECT * FROM TOKEN as t WHERE t.value = ?1 and t.type = ?2 and t.expired_time > NOW()", nativeQuery = true)
    Token findByValueAndType(String value, Integer type);
}
