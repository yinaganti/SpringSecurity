package com.example.security.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.security.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{
	
	@Query("select u from User u where u.userName=:userName")
	Optional<User> finByUserName(String userName);
}
