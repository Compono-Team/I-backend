package com.compono.ibackend.user.repository;

import com.compono.ibackend.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByOauthProviderUniqueKey(String oauthProviderUniqueKey);

    Optional<User> findByEmail(String email);
}
