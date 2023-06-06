package com.tuananhdo.repository;

import com.tuananhdo.entity.User;
import com.tuananhdo.util.AuthenticationType;
import com.tuananhdo.util.DeleteStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Query("UPDATE User c SET c.authenticationType = :type WHERE c.id = :id")
    @Modifying
    void updateAuthenticationType(Long id,AuthenticationType type);

    @Query("SELECT u FROM User u WHERE  u.resetPasswordToken = :token")
    User findByResetPasswordToken(String token);

    @Transactional
    @Query("UPDATE User u SET u.enabled = true WHERE u.id = :id")
    @Modifying
    void enableUser(Long id);

    @Query("SELECT c FROM User c WHERE c.verificationCode = :token")
    User findByVerificationCode(String token);

    @Query("UPDATE User u SET u.deleteStatus = :deleteStatus WHERE u.id = :userId")
    @Modifying
    void markUserAsDeleted(Long userId, DeleteStatus deleteStatus);

    @Query("SELECT u FROM User u WHERE u.deleteStatus = :status")
    List<User> findAllActiveUsers(DeleteStatus status);
}
