package giveme.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

@Service
public class EncryptionServices
{
	private final BCryptPasswordEncoder	encoder;

	public EncryptionServices()
	{
		encoder = new BCryptPasswordEncoder();
		// TODO Auto-generated constructor stub
	}

	public String encryptPassword(String password)
	{
		return encoder.encode(password);
	}

	public Boolean match(String rawPassword, String encoded)
	{
		return encoder.matches(rawPassword, encoded);
	}

	public String getInvideCode()
	{
		return KeyGenerators.string().generateKey();
	}

}
