package com.usermanagement.repository;

import com.usermanagement.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unused")
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    @Query("SELECT u FROM UserEntity u WHERE u.id = :id")
    UserEntity getUserById(@Param("id") Long id);

    @Modifying
    @Query(value = "INSERT INTO users (id, name) VALUES (:id, :name)", nativeQuery = true)
    void addUser(@Param("id") Long id, @Param("name") String name);

    @Modifying
    @Query(value = "UPDATE users SET name=:name WHERE id=:id", nativeQuery = true)
    void updateUser(@Param("id") Long id, @Param("name") String name);
}
