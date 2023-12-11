package com.sonhai.services;

import com.sonhai.exception.UserException;
import com.sonhai.models.User;

public interface UserService {
    public User findUserById(Long userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;
}
