package org.miit.edf.repos;

import org.miit.edf.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    boolean existsByLogin(String username);
    User findByLogin(String username);

    @Query(value = """
    select u.* from usr u
    where regexp_like(concat(u.last_name, ' ', u.first_name, ' ', u.patronymic), :name, 'i')
    """, nativeQuery = true)
    List<User> findByName(@Param("name") String name);
}
