package com.example.bankcards.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.bankcards.entity.user.Role;
import com.example.bankcards.entity.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Modifying
    @Query(value = """
            INSERT INTO user_roles (user_id, role)
            VALUES (:user_id, :role)
            """, nativeQuery = true)
    void insertUserRole(@Param("user_id") Long userId, @Param("role") Role role);

    @Modifying
    @Query(value = """
            DELETE FROM user_roles
            WHERE user_id = :user_id
            """, nativeQuery = true)
    void deleteByUserId(@Param("user_id") Long userId);

    @Query("""
            SELECT exists(
                SELECT 1
                FROM Account a
                WHERE a.user = :user
                AND a.id = :account_id
            )
            """)
    boolean isAccountOwner(@Param("user") User user, @Param("account_id") Long accountId);
}
