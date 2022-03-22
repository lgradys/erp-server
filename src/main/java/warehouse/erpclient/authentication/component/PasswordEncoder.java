package warehouse.erpclient.authentication.component;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Component
public class PasswordEncoder {

    private final int SALT_LENGTH = 15;
    private final String HASHING_ALGORITHM = "SHA-512";

    private MessageDigest instance;

    public PasswordEncoder() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        try {
            this.instance = MessageDigest.getInstance(HASHING_ALGORITHM);
        } catch (NoSuchAlgorithmException exception) {
            new BeanCreationException(exception.getMessage());
        }
    }

    public byte[] encode(byte[] password) {
        return instance.digest(password);
    }

}

