package com.mertapp.todolist.controller;

import com.mertapp.todolist.model.User;
import com.mertapp.todolist.payload.request.LoginRequest;
import com.mertapp.todolist.payload.request.SignupRequest;
import com.mertapp.todolist.payload.response.JwtResponse;
import com.mertapp.todolist.payload.response.MessageResponse;
import com.mertapp.todolist.repository.UserRepository;
import com.mertapp.todolist.security.jwt.JwtUtils;
import com.mertapp.todolist.security.services.UserDetailsImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "User Api documentation")
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
    @ApiOperation(value = "register operation user")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (!checkUserExist(signupRequest)) {
            createUser(signupRequest);
            return ResponseEntity.ok(new MessageResponse("User registered!"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("User Exist!"));
    }

    @PostMapping("login")
    @ApiOperation(value = "login operation user")
    public ResponseEntity<JwtResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail()));
    }
    private void createUser(SignupRequest signupRequest) {
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        userRepository.save(user);
    }

    private boolean checkUserExist(SignupRequest signupRequest) {
        return checkUserNameExist(signupRequest.getUsername()) || checkEmailExist(signupRequest.getEmail());
    }

    private boolean checkEmailExist(String email) {
        return userRepository.existsByEmail(email);
    }

    private boolean checkUserNameExist(String userName) {
        return userRepository.existsByUsername(userName);
    }
}
