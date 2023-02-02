package com.emptysky.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emptysky.project.entity.User;

public interface UserRepository extends JpaRepository<User, String>{
	
}
