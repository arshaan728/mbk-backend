package com.example.mbk_backend.repository;

import com.example.mbk_backend.entities.ExpenseType;
import com.example.mbk_backend.entities.User;
import com.example.mbk_backend.entities.Userspending;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserspendingRespository extends JpaRepository<Userspending , Integer> {

    @Query("SELECT s FROM Userspending s WHERE s.repeats = true AND s.paidstatus = false")
    List <Userspending> getallrepeatingitems();

//    @Query("SELECT s from Userspending s WHERE s.email = :email AND s.repeats = true AND s.paidstatus = false")
//    List<Userspending> getreoeatingitemsbyemail(@Param("email")String email);

    @Query("SELECT s FROM Userspending s WHERE s.userid = :user AND s.repeats=true AND s.paidstatus = false")
    List<Userspending> getrepeatingitemsfromeamail(@Param("user") User user);

    Optional<Userspending> findById(Integer experienceid);

    void deleteById(Integer experienceid);

    @Transactional
    @Modifying
    @Query("UPDATE Userspending s SET s.paidstatus=true WHERE s.userid = :user AND s.purchaseid = :purchaseid")
    int updatepaidstatus(@Param("user") User user , @Param("purchaseid") Integer purchaseid);

     Optional<Userspending> findByPurchaseid(Integer purchaseid);

    @Modifying
    @Transactional
    @Query("DELETE FROM Userspending u WHERE u.purchaseid = :purchaseid")
    void deleteByPurchaseid(@Param("purchaseid") Integer purchaseid);

    @Query("SELECT s FROM Userspending s WHERE s.dateTime = :datetime AND s.userid.userid = :userid")
    List<Userspending> getexpenses(@Param("datetime")ZonedDateTime dateTime , @Param("userid")Integer userid);

    @Query("SELECT s FROM Userspending s WHERE s.userid.userid = :userid")
    List<Userspending> gettimeMonth(@Param("userid")Integer userid);

    @Query("SELECT s FROM Userspending s WHERE s.userid.userid = :userid AND s.dateTime BETWEEN :startDate AND :endDate")
    List<Userspending> getexpensesmonth(@Param("userid") Integer userid , @Param("startDate") ZonedDateTime startdate , @Param("endDate")ZonedDateTime endDate );

    @Query("SELECT SUM(amount) FROM Userspending s WHERE s.expenseType = :expensetype AND s.userid.userid = :userid AND s.dateTime BETWEEN :startDate AND :endDate")
    Double getexpenesmonthforeachcategory(@Param("expensetype") ExpenseType expensetype , @Param("userid") Integer userid , @Param("startDate") ZonedDateTime startdate , @Param("endDate")ZonedDateTime endDate );

    @Query("SELECT s FROM Userspending s WHERE s.userid.userid = :userid")
    List<Userspending> getuserspending(@Param("userid") Integer userid);

    @Query("SELECT s FROM Userspending s WHERE s.userid.userid = :userid ORDER BY dateTime ASC LIMIT 1")
    Optional<Userspending> getfirstrecord(@Param("userid")Integer userid);

    @Query("SELECT s FROM Userspending s WHERE s.userid.userid = :userid ORDER BY dateTime DESC LIMIT 1")
    Optional<Userspending> gelastrecord(@Param("userid")Integer userid);

    @Query("SELECT SUM(amount) FROM Userspending s WHERE s.userid.userid = :userid AND s.expenseType = :expensetype")
    int gettotalofcategories(@Param("userid")Integer userid , @Param("expensetype")ExpenseType expenseType);





}
