package com.example.PharmacyApp.repository;

import com.example.PharmacyApp.model.Persistance.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token,Integer> {
    @Query("""
            select t from Token t inner join User u on t.user.userID=u.userID where t.user.userID=:userID and t.is_logged_out=false
            """)
    List<Token> findAllTokenByUSer(Integer userID);
    Optional<Token> findByToken(String token);

}
