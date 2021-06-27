package lviv.java.club.nimbus.harness;

import java.security.SecureRandom;

public class CryptoUtils {

    static final byte[] SHARED_SECRET_KEY = new byte[32];

    static {
        new SecureRandom().nextBytes(SHARED_SECRET_KEY);
    }


}
