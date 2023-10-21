package com.legends.taskFlow.repositorty;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.legends.taskFlow.model.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByRefreshToken(String token);
    boolean existsUserByEmail(String email);
    boolean existsUserByTelephone(String phoneNumber);
}
