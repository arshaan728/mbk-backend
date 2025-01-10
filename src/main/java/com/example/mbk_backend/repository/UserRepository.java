package com.example.mbk_backend.repository;

import com.example.mbk_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

//        @Query("SELECT u FROM User u WHERE u.userid = :userid")
//        Optional<User> findByUserid(@Param("userid") Integer userid);

        Optional<User> findByUserid(Integer userid);




//    User findByRole(Role role);a


}
