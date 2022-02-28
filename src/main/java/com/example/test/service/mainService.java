package com.example.test.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.test.model.User;
import com.example.test.repository.UserRepository;
import com.example.test.utils.JwtUtils;

@Service
public class mainService {
  @Autowired
  JwtUtils jwtUtils;
  @Autowired
  UserRepository userRepository;
  
  public User getUserFromToken(HttpServletRequest request) {
    String jwt = jwtUtils.getJwtFromCookies(request);
    String name = jwtUtils.getUserNameFromJwtToken(jwt);
    System.out.println("jwt :: "+jwt);
    return userRepository.findByUsername(name).get();
  }
  
}
