package com.mohamedsamir1495.eventbookingsystem.repository;

import com.mohamedsamir1495.eventbookingsystem.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	@Query("select user from UserEntity user where user.email =:email")
	Optional<UserEntity> findUserByEmail(String email);

}
