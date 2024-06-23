package engineer.dima.manager.crypto;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class KeyConverter {
    public static SecretKey convertStringToSecretKey(String keyString, String algorithm) {
        byte[] decodedKey = Base64.getDecoder().decode(keyString);

        return new SecretKeySpec(decodedKey, 0, decodedKey.length, algorithm);
    }
}
