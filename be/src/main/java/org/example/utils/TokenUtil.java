package org.example.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtil {

  private static final String SECRET_KEY = "genbesys";

  public static String getToken(Map<String, Object> payload) {
    Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
    return JWT.create()
        .withPayload(payload)
        .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 *  60 * 24))
        .sign(algorithm);
  }

  public static Map<String, Claim> parseToken(String token) {
    token = token.replace("Bearer ", "");
    Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
    JWTVerifier verifier = JWT.require(algorithm).build();
    DecodedJWT jwt = verifier.verify(token);
    return jwt.getClaims();
  }

  public static boolean validateToken(String token) {
    // 如果token为空返回false
    if (token == null || token.isEmpty()){
      return false;
    }

    token = token.replace("Bearer ", "");
    try {
      Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
      JWTVerifier verifier = JWT.require(algorithm).build();
      DecodedJWT jwt = verifier.verify(token);
      return true;
    } catch (Exception e) {
      return false;
    }

  }

  public static void main(String[] args) {
    HashMap<String, Object> payload = new HashMap<>();
    payload.put("username", "genbesys");
    payload.put("role", "admin");

    String token = getToken(payload);
    System.out.println("Token: " + token);

    Map<String, Claim> origin = parseToken(token);
    System.out.println("Parsed Token: " + origin);

    boolean isValid = validateToken(token);
    System.out.println("Is Valid: " + isValid);
  }
}
