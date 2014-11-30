package giveme.test.services;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptionServicesTest
{
	public static Logger	LOGGER	= Logger.getLogger(EncryptionServicesTest.class.getName());

	@Test
	public void encryptPasswordTest()
	{
		LOGGER.info("Encryption password test");
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encrypted = passwordEncoder.encode("password_01");
		LOGGER.info(passwordEncoder.matches("password_01",
				"$2a$10$TMIWXPDi2QuDflyFxu.unekNsql7u8nVoAWizqC1xx59jXD7ZeqMG"));
		LOGGER.info(encrypted);
	}
}
