package com.sonhai.services;

import com.sonhai.config.JwtProvider;
import com.sonhai.exception.UserException;
import com.sonhai.models.User;
import com.sonhai.repository.UserRepository;

import java.util.Optional;
/**
 * Using Optional<T> Class - to deal with NullPointerException.
 *
 * Some methods have been used in the file
 * .get(): If the value is present, returns the value, otherwise throws NoSuchElementException.
 * .isPresent(): Returns true if a value present, otherwise false.
 *
 * */

public class UserServiceImpl implements UserService{
    UserRepository userRepository;
    JwtProvider jwtProvider;

    public UserServiceImpl(UserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public User findUserById(Long userId) throws UserException {
        Optional<User> user = userRepository.findById(userId);

        /* Check if there is a value in the user */
        if (!user.isPresent()) {
            return user.get();
        }
        throw new  UserException("Cannot fine the User with the id " + userId);
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        /* Find the email through jwt  */
        String email = jwtProvider.getEmailFromToken(jwt);

        /* Get the user through email */
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserException("Cannot find the user with email " + email);
        }
        return user;
    }
}
