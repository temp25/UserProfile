package com.paddyseedexpert.userprofile.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.paddyseedexpert.userprofile.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
	
	Long countByEmailAddress(String emailAddress);
	Long countByUserName(String userName);
	Optional<User> findByUserName(String userName);
	Optional<User> findByIdAndUserNameAndPassword(UUID id, String userName, String password);
	Optional<User> findByUserNameAndPassword(String userName, String password);
	Optional<User> findByUserNameAndResetPassword(String userName, String resetPassword);
	Optional<User> findByEmailAddress(String emailAddress);
	Optional<User> findById(UUID id);
	
	@Procedure
	String GenerateUniqueValue(@Param("tableName")String tableName, @Param("columnName") String columnName);
	
}
