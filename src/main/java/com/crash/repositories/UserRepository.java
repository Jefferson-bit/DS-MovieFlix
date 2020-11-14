package com.crash.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crash.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
