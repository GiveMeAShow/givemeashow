package giveme.test.services;

import static org.assertj.core.api.Assertions.assertThat;
import giveme.services.EncryptionServices;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations =
//{ "classpath:gmas-db-cfg-test.xml", "file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml" })
public class EncryptionServicesTest
{
	public static Logger	LOGGER	= Logger.getLogger(EncryptionServicesTest.class.getName());

	EncryptionServices		encryptionServices;

	@Before
	public void init()
	{
		encryptionServices = new EncryptionServices();
	}

	@Test
	public void encryptPasswordTest()
	{
		LOGGER.info("Encryption password test");
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encrypted = encryptionServices.encryptPassword("password_01");

		assertThat(
				encryptionServices.match("password_01", "$2a$10$TMIWXPDi2QuDflyFxu.unekNsql7u8nVoAWizqC1xx59jXD7ZeqMG"))
				.isTrue();
		LOGGER.info(encrypted);
	}

	@Test
	public void generateInviteCodeTest()
	{
		LOGGER.info("Generate invite code test");
		String inviteCode = encryptionServices.getInvideCode();
		assertThat(inviteCode).isNotNull();
		assertThat(inviteCode).isNotEmpty();
	}
}
