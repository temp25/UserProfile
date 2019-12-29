package com.paddyseedexpert.utils;

import com.github.gotham25.turbosmtp.TurboMailer;

import static com.paddyseedexpert.userprofile.constant.AppConstants.TURBO_SMTP_AUTH_USERNAME;
import static com.paddyseedexpert.userprofile.constant.AppConstants.TURBO_SMTP_AUTH_PASSWORD;
import static com.paddyseedexpert.userprofile.constant.AppConstants.FROM_ADDRESS;
import static com.paddyseedexpert.utils.DataUtils.getTimestamp;
import static com.paddyseedexpert.utils.DataUtils.prepareEmailBody;

public class MailUtils {

	public static String sendResetPasswordMail(String emailAddress, String resetPassword) {
		
		String emailBody =  prepareEmailBody("Hello "+emailAddress+",<br/><br/><br/>You have requested for password reset around, "+getTimestamp()+". Please find below details,<br/><br/>New Password: <i>"+resetPassword+"</i>");
		String subject = "PaddySeedExpert Password Reset";
		String mailResponse = sendMail(emailAddress, subject, emailBody);
		
		return mailResponse;
		
	}

	public static String sendForgotUsernameMail(String emailAddress, String userName) {

		String emailBody =  prepareEmailBody("Hello, "+emailAddress+"<br/><br/><br/>You have requested for username to sign in around, "+getTimestamp()+". Please find below details,<br/><br/>Username: <i>"+userName+"</i>");
		String subject = "PaddySeedExpert Forgot Username";
		String mailResponse = sendMail(emailAddress, subject, emailBody);
		
		return mailResponse;
		
		
	}

	public static String sendForgotPasswordMail(String emailAddress, String userName, String password) {

		String emailBody =  prepareEmailBody("Hello, "+userName+"<br/><br/><br/>You have requested for password to sign in around, "+getTimestamp()+". Please find below details,<br/><br/>Username: <i>+"+userName+"</i><br/>Password: <i>"+password+"</i>");
		String subject = "PaddySeedExpert Forgot Password";
		String mailResponse = sendMail(emailAddress, subject, emailBody);
		
		return mailResponse;
		
	}
	
	private static String sendMail(String emailAddress, String subject, String emailBody) {
		
		TurboMailer mailer = new TurboMailer.Builder()
				.AuthUser(TURBO_SMTP_AUTH_USERNAME)
				.AuthPass(TURBO_SMTP_AUTH_PASSWORD)
				.build();
		String mailResponse = mailer.sendMail(
					FROM_ADDRESS, //From
					emailAddress, //To
					subject, //Subject
					null,
					null,
					null,
					emailBody, //html_content
					null,
					null
				);
		
		return mailResponse;
		
	}

}
