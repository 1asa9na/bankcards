package com.example.bankcards.repository;

import java.util.Optional;
import java.util.UUID;

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

    @Query("""
        SELECT exists(
                SELECT 1
                FROM Card c
                WHERE c.id = :card_id
                AND c.account.user = :user
        )
        """)
    boolean isCardOwner(@Param("user") User user, @Param("card_id") Long cardId);

    @Query("""
        SELECT exists (
                SELECT 1
                FROM Transfer t
                WHERE t.id = :transfer_id
                AND (
                        t.srcCard.account.user = :user
                        OR t.destCard.account.user = :user
                )
        )
        """)
    boolean isTranferParticipant(@Param("user") User user, @Param("transfer_id") UUID transferId);
}
