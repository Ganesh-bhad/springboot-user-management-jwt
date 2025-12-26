package com.example.usermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.usermanagement.entity.User;
import com.example.usermanagement.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository repo;

	// GET ALL USERS
	@GetMapping
	public List<User> getAllUsers() {
		return repo.findAll();
	}

	// GET USER BY ID
	@GetMapping("/{id}")
	public User getUserById(@PathVariable Long id) {
		return repo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
	}

	// DELETE USER (ADMIN ONLY)
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public String deleteUser(@PathVariable Long id) {
	    if (!repo.existsById(id)) {
	        throw new RuntimeException("User not found");
	    }
	    repo.deleteById(id);
	    return "User deleted successfully";
	}

}
