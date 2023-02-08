package com.campers.camfp.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.campers.camfp.entity.User;
import com.campers.camfp.repository.UserRepository;

@SpringBootTest
public class UserRepositoryTests {

	@Autowired
	UserRepository userRepository;
	
	@Test
	public void insertUserDummys() {
		IntStream.rangeClosed(1, 100).forEach(i -> {
			User user = User.builder()
							.id("tester"+i)
							.pass("1234")
							.nickname("tester"+i)
							.gender(i%2==0?true:false)
							.age(i%30)
							.introduce("나는 tester"+i+"입니다.")
							.build();
			userRepository.save(user);
		});
	}
	
	@Test
	public void selectUserDummy() {
		String userId = "tester1";
		
		System.out.println(userRepository.findById(userId));
	}
	
	@Test
	public void updateUserDummy() {
		User user = User.builder()
						.id("tester1")
						.age(11111)
						.nickname("관리자1")
						.grade(true)
						.phone("01010101010")
						.name("관리자")
						.introduce("관리자로 승격했습니다!")
						.pass("0000")
						.build();
		System.out.println(userRepository.save(user));
	}
	
	@Test
	public void deleteUserDummy() {
		String userId = "tester100";
		userRepository.deleteById(userId);
	}
	
}
