package giveme.services;

import giveme.common.beans.User;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailServices
{
	@Autowired
	private MailSender	mailSender;

	@Autowired
	EncryptionServices	encryptionServices;

	public String sendInvite(String userName, String email)
	{
		String inviteCode = encryptionServices.getInvideCode();
		String message = "Hey,\r\rGiveMeAShow is a streaming website. It is only available by receiving an invitations, and "
				+ userName
				+ " sent you one !\r\rClick on the link below : \r"
				+ "http://127.0.0.1:8080/register/"
				+ inviteCode;

		SimpleMailMessage simpleMail = new SimpleMailMessage();
		simpleMail.setTo(email);
		simpleMail.setSubject("[GiveMeAShow]" + userName + " sent you an invite .");
		simpleMail.setText(message);
		simpleMail.setSentDate(new Date());
		mailSender.send(simpleMail);
		return inviteCode;
	}

	public void sendWelcome(User user)
	{
		String message = "Hey " + user.getLogin() + ",\r\r" + "Thank you for registering.";

		SimpleMailMessage simpleMail = new SimpleMailMessage();
		simpleMail.setTo(user.getEmail());
		simpleMail.setSubject("[GiveMeAShow] Thank you for registering");
		simpleMail.setText(message);
		simpleMail.setSentDate(new Date());
		mailSender.send(simpleMail);
	}
}
