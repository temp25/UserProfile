package com.paddyseedexpert.userprofile.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "USER_PROFILE", uniqueConstraints = { @UniqueConstraint(columnNames = "USER_NAME", name="UQ_USER_PROFILE_USER_NAME"), @UniqueConstraint(columnNames = "EMAIL_ADDRESS", name = "UQ_USER_PROFILE_EMAIL_ADDRESS") })
@NamedStoredProcedureQuery(
		name = "GenerateUniqueValue",
		procedureName = "GenerateUniqueValue",
		parameters = {
				@StoredProcedureParameter(name = "tableName", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "columnName", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "output", mode = ParameterMode.OUT, type = String.class)
})
public class User implements Serializable {

	private static final long serialVersionUID = 3648621734249230252L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy="org.hibernate.id.UUIDGenerator")
	@Column(name = "ID")
	@Type(type = "uuid-char")
	private UUID id;
	
	@Column(name = "USER_NAME")
	private String userName;
	
	@Column(name = "EMAIL_ADDRESS")
	private String emailAddress;
	
	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;
	
	@Column(name = "ADDRESS")
	private String address;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "CONFIRM_PASSWORD")
	private String confirmPassword;
	
	@Column(name = "RESET_PASSWORD")
	private String resetPassword;

	@Column(name = "SECURE_QUESTION")
	private String secureQuestion;

	@Column(name = "SECURE_ANSWER")
	@NotBlank(message = "Security answer should not be empty")
	private String secureAnswer;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_TIMESTAMP")
	private Date createdTimestamp;
	
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATED_TIMESTAMP")
	private Date lastUpdatedTimestamp;

	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(UUID id) {
		this.id = id;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/**
	 * @return the resetPassword
	 */
	public String getResetPassword() {
		return resetPassword;
	}

	/**
	 * @param resetPassword the resetPassword to set
	 */
	public void setResetPassword(String resetPassword) {
		this.resetPassword = resetPassword;
	}

	/**
	 * @return the secureQuestion
	 */
	public String getSecureQuestion() {
		return secureQuestion;
	}

	/**
	 * @param secureQuestion the secureQuestion to set
	 */
	public void setSecureQuestion(String secureQuestion) {
		this.secureQuestion = secureQuestion;
	}

	/**
	 * @return the secureAnswer
	 */
	public String getSecureAnswer() {
		return secureAnswer;
	}

	/**
	 * @param secureAnswer the secureAnswer to set
	 */
	public void setSecureAnswer(String secureAnswer) {
		this.secureAnswer = secureAnswer;
	}

	/**
	 * @return the createdTimestamp
	 */
	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	/**
	 * @param createdTimestamp the createdTimestamp to set
	 */
	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	/**
	 * @return the lastUpdatedTimestamp
	 */
	public Date getLastUpdatedTimestamp() {
		return lastUpdatedTimestamp;
	}

	/**
	 * @param lastUpdatedTimestamp the lastUpdatedTimestamp to set
	 */
	public void setLastUpdatedTimestamp(Date lastUpdatedTimestamp) {
		this.lastUpdatedTimestamp = lastUpdatedTimestamp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result
				+ ((confirmPassword == null) ? 0 : confirmPassword.hashCode());
		result = prime
				* result
				+ ((createdTimestamp == null) ? 0 : createdTimestamp.hashCode());
		result = prime * result
				+ ((emailAddress == null) ? 0 : emailAddress.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((lastUpdatedTimestamp == null) ? 0 : lastUpdatedTimestamp
						.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result
				+ ((resetPassword == null) ? 0 : resetPassword.hashCode());
		result = prime * result
				+ ((secureAnswer == null) ? 0 : secureAnswer.hashCode());
		result = prime * result
				+ ((secureQuestion == null) ? 0 : secureQuestion.hashCode());
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (confirmPassword == null) {
			if (other.confirmPassword != null)
				return false;
		} else if (!confirmPassword.equals(other.confirmPassword))
			return false;
		if (createdTimestamp == null) {
			if (other.createdTimestamp != null)
				return false;
		} else if (!createdTimestamp.equals(other.createdTimestamp))
			return false;
		if (emailAddress == null) {
			if (other.emailAddress != null)
				return false;
		} else if (!emailAddress.equals(other.emailAddress))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastUpdatedTimestamp == null) {
			if (other.lastUpdatedTimestamp != null)
				return false;
		} else if (!lastUpdatedTimestamp.equals(other.lastUpdatedTimestamp))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		if (resetPassword == null) {
			if (other.resetPassword != null)
				return false;
		} else if (!resetPassword.equals(other.resetPassword))
			return false;
		if (secureAnswer == null) {
			if (other.secureAnswer != null)
				return false;
		} else if (!secureAnswer.equals(other.secureAnswer))
			return false;
		if (secureQuestion == null) {
			if (other.secureQuestion != null)
				return false;
		} else if (!secureQuestion.equals(other.secureQuestion))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", emailAddress="
				+ emailAddress + ", phoneNumber=" + phoneNumber + ", address="
				+ address + ", password=" + password + ", confirmPassword="
				+ confirmPassword + ", resetPassword=" + resetPassword
				+ ", secureQuestion=" + secureQuestion + ", secureAnswer="
				+ secureAnswer + ", createdTimestamp=" + createdTimestamp
				+ ", lastUpdatedTimestamp=" + lastUpdatedTimestamp + "]";
	}
	
}