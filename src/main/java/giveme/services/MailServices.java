package giveme.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
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
				+ "http://givemeashow.net/register?invite=" + inviteCode;

		SimpleMailMessage simpleMail = new SimpleMailMessage();
		simpleMail.setTo(email);
		simpleMail.setSubject(userName + " sent you a GiveMeAShow invite url.");
		simpleMail.setText(message);
		simpleMail.setSentDate(new Date());
		mailSender.send(simpleMail);
		return inviteCode;
	}
}
