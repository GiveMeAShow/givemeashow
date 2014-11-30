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

	public void sendInvite(String email)
	{
		SimpleMailMessage simpleMail = new SimpleMailMessage();
		simpleMail.setTo(email);
		simpleMail.setSubject("Ogdabou sent you a GiveMeAShow invite url.");
		String message = "Hey,\r GiveMeAShow is only available thourgh invitations, here is the link : "
				+ encryptionServices.getInvideCode() + ".";
		simpleMail.setText(message);
		simpleMail.setSentDate(new Date(11, 11, 11));
		mailSender.send(simpleMail);
	}
}
