package com.example.test.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.test.model.User;
import com.example.test.repository.UserRepository;
import com.example.test.utils.JwtUtils;

@Service
public class MainService {
    @Autowired
    UserRepository userRepository;

    public User getUserFromToken(HttpServletRequest request) {
        String jwt = JwtUtils.getJwtFromCookies(request);
        String name = JwtUtils.getUserNameFromJwtToken(jwt);
        return userRepository.findByUsername(name).get();
    }

}
