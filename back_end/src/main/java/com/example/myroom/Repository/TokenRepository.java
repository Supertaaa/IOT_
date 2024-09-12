package com.example.myroom.Repository;

import com.example.myroom.Entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value = "SELECT * FROM TOKEN as t WHERE t.user_id = ?1 and  t.value = ?2 and t.type = ?3 and t.expired_time > NOW()", nativeQuery = true)
    Token findByUserIdAndValueAndType(Integer userId, String value, Integer type);

    @Query(value = "SELECT * FROM TOKEN as t WHERE t.user_id = ?1 and t.type = ?2 and t.expired_time > NOW()", nativeQuery = true)
    Token findByUserIdAndType(Integer userId, Integer type);
}
