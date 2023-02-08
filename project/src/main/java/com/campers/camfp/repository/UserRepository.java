package com.campers.camfp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campers.camfp.entity.User;

public interface UserRepository extends JpaRepository<User, String>{
	
}
