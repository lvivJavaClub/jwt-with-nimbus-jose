package lviv.java.club.nimbus.harness;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultJwtBuilder;
import io.jsonwebtoken.security.Keys;
import lombok.SneakyThrows;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 1)
@Measurement(iterations = 1)
@Fork(value = 1)
public class NimbusVsJjwt {

    private static ObjectMapper objectMapper = new ObjectMapper();


    @SneakyThrows
    @Benchmark
    public void createJwtNimbus(Blackhole bh) {
        var now = new Date();
        var claimsSet = new JWTClaimsSet.Builder()
                .issuer("javaclub")
                .subject("john@doe")
                .audience("service1")
                .notBeforeTime(now)
                .issueTime(now)
                .jwtID(UUID.randomUUID().toString())
                .claim("firstName", "John")
                .claim("lastName", "Doe")
                .claim("role", "admin")
                .build();

        var jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
        var macSigner = new MACSigner(CryptoUtils.SHARED_SECRET_KEY);

        var jwsObject = new JWSObject(jwsHeader, claimsSet.toPayload());
        jwsObject.sign(macSigner);
        var jwsToken = jwsObject.serialize();
        bh.consume(jwsToken);
    }

    @Benchmark
    public void createJwtJJWT(Blackhole bh) {
        var now = new Date();
        var jwtString = new DefaultJwtBuilder()
                .setIssuer("javaclub")
                .setSubject("john@doe")
                .setAudience("service1")
                .setNotBefore(now)
                .setIssuedAt(now)
                .setId(UUID.randomUUID().toString())
                .claim("firstName", "John")
                .claim("lastName", "Doe")
                .claim("role", "admin")
                .signWith(Keys.hmacShaKeyFor(CryptoUtils.SHARED_SECRET_KEY))
                .compact();

        bh.consume(jwtString);
    }



    @SneakyThrows
    @Benchmark
    public void parseJwtNimbus(Blackhole bh) {
        var now = new Date();
        var claimsSet = new JWTClaimsSet.Builder()
                .issuer("javaclub")
                .subject("john@doe")
                .audience("service1")
                .notBeforeTime(now)
                .issueTime(now)
                .jwtID(UUID.randomUUID().toString())
                .claim("firstName", "John")
                .claim("lastName", "Doe")
                .claim("role", "admin")
                .build();

        var jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
        var macSigner = new MACSigner(CryptoUtils.SHARED_SECRET_KEY);

        var jwsObject = new JWSObject(jwsHeader, claimsSet.toPayload());
        jwsObject.sign(macSigner);
        var jwsToken = jwsObject.serialize();
        bh.consume(jwsToken);
    }

    @Benchmark
    public void createJwtJJWT(Blackhole bh) {
        var now = new Date();
        var jwtString = new DefaultJwtBuilder()
                .setIssuer("javaclub")
                .setSubject("john@doe")
                .setAudience("service1")
                .setNotBefore(now)
                .setIssuedAt(now)
                .setId(UUID.randomUUID().toString())
                .claim("firstName", "John")
                .claim("lastName", "Doe")
                .claim("role", "admin")
                .signWith(Keys.hmacShaKeyFor(CryptoUtils.SHARED_SECRET_KEY))
                .compact();

        bh.consume(jwtString);
    }


//
//    @Benchmark
//    public void jjwtTest(Blackhole bh) {
//        var now = new Date();
//        var jwtString = new DefaultJwtBuilder()
//                .setIssuer("javaclub")
//                .setSubject("john@doe")
//                .setAudience("service1")
//                .setNotBefore(now)
//                .setIssuedAt(now)
//                .setId(UUID.randomUUID().toString())
//                .claim("firstName", "John")
//                .claim("lastName", "Doe")
//                .claim("role", "admin")
//                .signWith(Keys.hmacShaKeyFor(CryptoUtils.SHARED_SECRET_KEY))
//                .compact();
//
//        var claimsJws = Jwts.parserBuilder()
//                .setSigningKey(Keys.hmacShaKeyFor(CryptoUtils.SHARED_SECRET_KEY))
//                .build()
//                .parseClaimsJws(jwtString);
//
////        var user = objectMapper.convertValue(claimsJws.getBody(), User.class);
//        var body = claimsJws.getBody();
//        var user = User.builder()
//                .firstName(body.get("firstName", String.class))
//                .lastName(body.get("lastName", String.class))
//                .role(body.get("role", String.class))
//                .build();
//
//        bh.consume(user);
//    }
//
//    @Benchmark
//    public void nimbusTest(Blackhole bh) throws JOSEException, ParseException {
//        var now = new Date();
//        var claimsSet = new JWTClaimsSet.Builder()
//                .issuer("javaclub")
//                .subject("john@doe")
//                .audience("service1")
//                .notBeforeTime(now)
//                .issueTime(now)
//                .jwtID(UUID.randomUUID().toString())
//                .claim("firstName", "John")
//                .claim("lastName", "Doe")
//                .claim("role", "admin")
//                .build();
//
//        var jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
//        var macSigner = new MACSigner(CryptoUtils.SHARED_SECRET_KEY);
//
//        var jwsObject = new JWSObject(jwsHeader, claimsSet.toPayload());
//        jwsObject.sign(macSigner);
//        var jwsToken = jwsObject.serialize();
//
//        var parsedObject = JWSObject.parse(jwsToken);
//        var macVerifier = new MACVerifier(CryptoUtils.SHARED_SECRET_KEY);
//
//        var parsedClaims = JWTClaimsSet.parse(parsedObject.getPayload().toJSONObject());
//        var user = parsedClaims.toType(UserTransformer.USER_TRANSFORMER);
//        bh.consume(user);
//    }

}
