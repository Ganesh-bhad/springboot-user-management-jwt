
package com.example.usermanagement.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.entity.User;
import com.example.usermanagement.dto.*;
import com.example.usermanagement.security.JwtUtil;

@RestController
public class AuthController {

 @Autowired
 private UserRepository repo;

 private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

 @PostMapping("/register")
 public String register(@RequestBody RegisterRequest req) {
  User u = new User();
  u.setName(req.name);
  u.setEmail(req.email);
  u.setPassword(encoder.encode(req.password));
  u.setRole(req.role == null ? "ROLE_USER" : req.role);
  repo.save(u);
  return "User registered";
 }

 @PostMapping("/login")
 public String login(@RequestBody LoginRequest req) {
  User user = repo.findByEmail(req.email)
    .orElseThrow(() -> new RuntimeException("Invalid credentials"));

  if (!encoder.matches(req.password, user.getPassword())) {
    throw new RuntimeException("Invalid credentials");
  }
  return JwtUtil.generateToken(user.getEmail());
 }
 
 
 @GetMapping("/{id}")
 public User getUserById(@PathVariable Long id) {
     return repo.findById(id)
             .orElseThrow(() -> new RuntimeException("User not found"));
 }
}
