package org.miit.edf.repos;

import org.miit.edf.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    boolean existsByLogin(String username);
    User findByLogin(String username);
}
