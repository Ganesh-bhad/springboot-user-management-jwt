
package com.example.usermanagement.security;

import io.jsonwebtoken.*;
import java.util.Date;

public class JwtUtil {
 private static final String SECRET = "secretkey";

 public static String generateToken(String username) {
  return Jwts.builder()
    .setSubject(username)
    .setIssuedAt(new Date())
    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
    .signWith(SignatureAlgorithm.HS256, SECRET)
    .compact();
 }

 public static String extractUsername(String token) {
  return Jwts.parser().setSigningKey(SECRET)
    .parseClaimsJws(token).getBody().getSubject();
 }
}
