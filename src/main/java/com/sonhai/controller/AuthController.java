package com.sonhai.controller;

import com.sonhai.config.JwtProvider;
import com.sonhai.exception.UserException;
import com.sonhai.models.Cart;
import com.sonhai.models.User;
import com.sonhai.repository.UserRepository;
import com.sonhai.request.LoginRequest;
import com.sonhai.response.AuthResponse;
import com.sonhai.services.CartService;
import com.sonhai.services.CustomUserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Check the authentication from user's input then send back the token for the user when it's valid.
 * */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserRepository userRepository;
    private JwtProvider jwtProvider;

    private PasswordEncoder passwordEncoder;

    private CustomUserServiceImpl customUserService;

    private CartService cartService;

    public AuthController(UserRepository userRepository, CustomUserServiceImpl customUserService,
                          PasswordEncoder passwordEncoder, CartService cartService) {
        this.userRepository = userRepository;
        this.customUserService = customUserService;
        this.passwordEncoder = passwordEncoder;
        this.cartService = cartService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {
        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();

        /* Check whether the email has been used */
        User isEmailExist = userRepository.findByEmail(email);

        if (isEmailExist != null) {
            throw new UserException("The email has already used by another account.");
        }

        /* Create new User */
        User createdUser = new User();
        createdUser.setEmail(email);
        /* Encode the password before saving to the database */
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFirstName(firstName);
        createdUser.setLastName(lastName);

        /* Save the user to database */
        User savedUser = userRepository.save(createdUser);
        Cart cart = cartService.createCart(savedUser);

        /**
         * Decode and validate the Bearer Token.
         * */
        /* Setting authentication through user email and password */
        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        /* Generate the token */
        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse(token, "Signup Successfully!");

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest){
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse(token, "Signin Successfully!");

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
    }

    /* Check the user whether it exist */
    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserService.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Username is incorrect. Try again!");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("The password is incorrect. Try again!");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
