package com.usermanagement.repository;

import com.usermanagement.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    @Query("SELECT u FROM UserEntity u WHERE u.username=:username")
    Optional<UserEntity> findByUserName(@Param("username") String username);

    @Modifying
    @Query("UPDATE UserEntity u SET u.password=:password WHERE u.username=:username")
    int updatePassword(@Param("username") String username, @Param("password") String password);
}
