package com.paddyseedexpert.userprofile.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paddyseedexpert.userprofile.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
	Long countByEmailAddress(String emailAddress);
	Long countByUserName(String userName);
	Optional<User> findByUserName(String userName);
	Optional<User> findByEmailAddress(String emailAddress);
	Optional<User> findById(UUID id);
}
