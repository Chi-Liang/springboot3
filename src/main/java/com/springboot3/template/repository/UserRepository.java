package com.springboot3.template.repository;

import com.springboot3.template.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findOneByMidIgnoreCase(String mid);
}
