package com.example.mbk_backend.repository;


import com.example.mbk_backend.entities.Monthyincome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MonthlyservicesRepository extends JpaRepository<Monthyincome, Integer> {

//    @Query("SELECT i FROM Monthyincome i WHERE i.userid.userid = :userid AND FUNCTION('DATE_TRUNC', 'second', i.datetime) = :datetime")
@Query("SELECT i FROM Monthyincome i WHERE i.userid.userid = :userid AND i.datetime = :datetime")
 Optional<Monthyincome> monthlyincome(@Param("userid") Integer userid , @Param("datetime") ZonedDateTime datetime);

@Query("SELECT i FROM Monthyincome i WHERE i.userid.userid = :userid")
List<Monthyincome> montlyincomeusers(@Param("userid") Integer userid);


}
