package lviv.java.club.nimbus.harness;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTClaimsSetTransformer;
import lombok.SneakyThrows;

public class UserTransformer implements JWTClaimsSetTransformer<User> {

    static final UserTransformer USER_TRANSFORMER = new UserTransformer();

    @SneakyThrows
    @Override
    public User transform(JWTClaimsSet claimsSet) {
        return User.builder()
                .firstName(claimsSet.getStringClaim("firstName"))
                .lastName(claimsSet.getStringClaim("lastName"))
                .role(claimsSet.getStringClaim("role"))
                .build();
    }
}
