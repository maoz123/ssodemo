package com.example.ssodemo.utils;

import com.alibaba.fastjson.JSON;
import com.example.ssodemo.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.sf.jsqlparser.expression.JsonAggregateFunction;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtq4IbIIpY5RQnk6AlndLZAIJN1uQ2BsUwOXBvI1w0+CtozM4dBMjHaLLv3dVbHw6wC5CmfQylhe8/KmcOTgarVbTathB2bs6JK/83fQjqIwIYZz3LlUEoXRU7Zopn990yLV4tLdS8x0Md0D4gTgVCO3//ySsnUk+1Sx6E9v2JTYPnIgG1fbvqLVXdErQrw5VOhy+rxT7hujDSsV/NpB8qeWu/gWPce+F8IYVp+ymSNwyBGqWjzlW1hWdbxk7jz0GujxNHDQtnzjN2YRPiopDC+1Xr6sg6KJcAPvEXKK17bUW6qFJKz/9y1wI/IThSsuYPvxFbM22mFvQJrIgU61TTQIDAQAB";
    private static String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC2rghsgiljlFCeToCWd0tkAgk3W5DYGxTA5cG8jXDT4K2jMzh0EyMdosu/d1VsfDrALkKZ9DKWF7z8qZw5OBqtVtNq2EHZuzokr/zd9COojAhhnPcuVQShdFTtmimf33TItXi0t1LzHQx3QPiBOBUI7f//JKydST7VLHoT2/YlNg+ciAbV9u+otVd0StCvDlU6HL6vFPuG6MNKxX82kHyp5a7+BY9x74XwhhWn7KZI3DIEapaPOVbWFZ1vGTuPPQa6PE0cNC2fOM3ZhE+KikML7VevqyDoolwA+8RcorXttRbqoUkrP/3LXAj8hOFKy5g+/EVszbaYW9AmsiBTrVNNAgMBAAECggEAA1LeDyWSAVIzjRQG5pJhL36vGXch+K9EHpDMCGqXDG9iV5n5BwefN8XDjHgW+IRF5LUg+9zgKhO170TovHdVPkGTNtMqj/6irVltP/sA4tzjmXuBIHlmTFXxAr7L62oCVnGhB957H32TpRjsPlUvRh/RiSp5Q/gA6e8482L+T1P6/jVoZs0rJjlBAuRl/nTk+SuRKbcYAELtqUUv4KFlPMNtR9gVcwInunKUJ3TDsgE+d4KnmTJAQ3SS2QEaG0avch1JwC2A4r3y42O6ocKnzUL49X7vH5Cdiy4mNU5UrVZo4/aXdT5+kk2JTDWIOi2REJpp0CeaWRf4w1bPWDK5UQKBgQDdYihWdH/M2OZAR7SIrghtcV4XRkyiQgHlhbTA0HMKQhTZvJfbrC5VgzC0JZBa75r7GFdSD5z3VulP9811NI0JVE7K7GOnSEIi+JP7ZTsaRdDkofrVmLij8W8BI4qKcpb10xdmOhCrWMUomf9m6IHA5G9YZZLVFa8CFZb7nhrqNQKBgQDTPpfzgvFYBQbA4hJKx0g5tlT570sorwk10tzBfdP0Qw5DG/5Lx9fobzlFEQr503P++aueOa0p5eFF71hezU7H3+6D+7VXHR1qdQeC2ypx61BSjs2QA8nA+oo1zuhL5keTeukVuppW5HM0H3QsSQXEO7XtQI9Hgg7FvwqDR9wnuQKBgEJeDde1PybrE0PL+0xKWA5vQRTbJ1gxT54+UizHkOVPYC7SQfhCCvXpyW0IIdmRITYLWwon0he4P2OQ11A+u9VDKAffjBR3LhSLztk7xNcgfPo9sRPdn+TOGUgPHpZYufbiHI6x85NanmiImi+Ann10PgkTEky9HmUSkdvGzyzNAoGAUoGnHbAuDV/hNGLYHCbbudbSN9BTva8n3MLgAB7iMwwhvOppFzYVyS3v4171t3/0VUk005bZYHzt0L/b9yMfOwfmg9xDBqRTgen1phFt8ZKkC6rE9RPVsC7q3ntS1Zo6qIqgESGR+JA/wsLqQAXBX9SmiOTnUVml5Wxpcb8WaNkCgYEAtYxL3V61LeuX9n/49XNb4CFUVIiD5gHGtDJ24ZD21LSJWmYdYx1p/DjImllTIIRclXfPUMdKioqQHzist0vx4Vw1ys7oATrxzqEBppZ120PT+UjxCktAwOINgriTr7CWeUKpesnb9tDnB7JJgEJVa7TYsU1ya4GkZ1BHaNR6cHw=";

    private static final String ALGORITHM = "RSA";

    public static void main(String[] args) throws Exception {
        //generatePrivateKeySecurity();
        //generatePublicKeySecurity();
        User user = new User();
        user.setEmail("test@citrix.com");
        user.setId(10);
        user.setNickName("cat");
        Map<String, Object> map = new HashMap<>();
        map.put("userName", user.getUserName());
        String token = createJwt(map);
        System.out.println(decodeJwt(token));
    }

    public static String createJwt(Map<String, Object> claims){
        try {
            Instant instant = new Date().toInstant();
            Duration duration = Duration.ofMinutes( 30 );
            Instant hoursLater = instant.plus( duration );

            Date laterDate = Date.from( hoursLater );
            return Jwts.builder()
                    .setClaims(claims)
                    .signWith(SignatureAlgorithm.RS256, generatePrivateKeySecurity())
                    .setExpiration(laterDate)
                    .compact();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Object> decodeJwt(String token){
        try {
            Jwt jwt = Jwts.parser().setSigningKey(generatePublicKeySecurity()).parse(token);
            return (Claims)jwt.getBody();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static PrivateKey generatePrivateKeySecurity() throws NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
        return KeyFactory.getInstance(ALGORITHM).generatePrivate(pkcs8EncodedKeySpec);
    }

    public static PublicKey generatePublicKeySecurity() throws NoSuchAlgorithmException, InvalidKeySpecException {
        //PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(publicKey));
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));

        PublicKey key = KeyFactory.getInstance(ALGORITHM).generatePublic(x509EncodedKeySpec);
        //System.out.println(Base64.getEncoder().encodeToString(key.getEncoded()).equals(publicKey));
        return key;
    }
}
