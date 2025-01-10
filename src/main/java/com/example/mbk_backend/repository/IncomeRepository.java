package com.example.mbk_backend.repository;

import com.example.mbk_backend.entities.Income;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Integer> {

    Optional<Income> findByIncomeid(Integer incomeid);


    @Query("SELECT i FROM Income i WHERE i.userid.userid = :userid")
    Optional<Income> findByUserid(@Param("userid")Integer userid);

    @Transactional
    @Modifying
    @Query("UPDATE Income i SET i.income = :income WHERE i.userid.userid = :userid ")
    int updateincome (@Param("income")Double income , @Param("userid")Integer userid);

    @Query("SELECT i from Income i")
    List<Income> findAll();

}
