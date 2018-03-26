package com.siwoo.application.repository;

import com.siwoo.application.domain.Document;
import com.siwoo.application.domain.User;
import com.siwoo.application.domain.views.UserSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);


    //mapping all users to UserSummary
    @Query("select new com.siwoo.application.domain.views.UserSummary(" +
            "u.nickname, u.email, u.point, u.status) from User u ")
    List<UserSummary> findAllUserSummary();

    //mapping user who is filtered by id to UserSummary
    @Query("select new com.siwoo.application.domain.views.UserSummary(" +
            "u.nickname, u.email, u.point, u.status) from User u " +
            "where u.id = :id")
    UserSummary findUserSummaryById(@Param("id") long id);

    //find user who has the most highest point
    @Query("select u from User u where u.point = " +
            "(select max(u2.point) from User u2 )")
    User findTopPointUser();

    //find the most recent joined user
    List<User> findTop5ByJoinDateEqualsOrderByIdDesc(LocalDate joinDate);

    //find the number of user by specified status
    @Query("select count(u.status) from User u group by u.status having u.status = :status")
    long findCountGroupByStatusByStatus(@Param("status") User.Status status);


    //find user with the association
    @Query("select distinct u from User u left join fetch u.documents ")
    List<User> findUserWithDocument();


    @Query("select u from User u where 1 < " +
            "(select count(d) from Document d where d.user.id = u.id)")
    List<User> findNoDocumentUser();

}
