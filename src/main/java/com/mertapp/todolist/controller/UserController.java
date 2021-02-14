package com.mertapp.todolist.controller;

import com.mertapp.todolist.model.User;
import com.mertapp.todolist.payload.request.LoginRequest;
import com.mertapp.todolist.payload.request.SignupRequest;
import com.mertapp.todolist.payload.response.JwtResponse;
import com.mertapp.todolist.payload.response.MessageResponse;
import com.mertapp.todolist.repository.UserRepository;
import com.mertapp.todolist.security.jwt.JwtUtils;
import com.mertapp.todolist.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (!checkUserExist(signupRequest)) {
            createUser(signupRequest);
            return ResponseEntity.ok(new MessageResponse("User registered!"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("User Exist!"));
    }

    @PostMapping("login")
    public ResponseEntity<JwtResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail()));
    }
    private void createUser(SignupRequest signupRequest) {
        User user = new User();
        user.setUsername(signupRequest.getUserName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        userRepository.save(user);
    }

    private boolean checkUserExist(SignupRequest signupRequest) {
        return checkUserNameExist(signupRequest.getUserName()) || checkEmailExist(signupRequest.getEmail());
    }

    private boolean checkEmailExist(String email) {
        return userRepository.existsByEmail(email);
    }

    private boolean checkUserNameExist(String userName) {
        return userRepository.existsByUsername(userName);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable(value = "id") long userId) throws Exception {
        return this.userRepository.findById(userId).orElseThrow(Exception::new);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return this.userRepository.save(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable("id") long userId) throws Exception {
        User existingUser = this.userRepository.findById(userId).orElseThrow(Exception::new);
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        return this.userRepository.save(existingUser);
    }

    @GetMapping("/{email}")
    public User getUserByEmail(@PathVariable("email") String email) {
        return this.userRepository.findUserByEmail(email);
    }
}
