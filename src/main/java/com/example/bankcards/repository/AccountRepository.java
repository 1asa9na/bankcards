package com.example.bankcards.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import com.example.bankcards.entity.account.Account;
import com.example.bankcards.entity.user.User;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @org.springframework.data.jpa.repository.Query("SELECT a FROM Account a WHERE a.user.id = :userId")
    List<Account> findAllByUserId(Long userId);

    @Modifying
    @Query("""
            UPDATE Account a
            SET a.user = :user
            WHERE a.id = :id
            """)
    void assignToUserById(@Param("id") Long id, @Param("user") User user);
}
