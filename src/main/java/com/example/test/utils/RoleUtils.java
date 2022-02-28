package com.example.test.utils;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class RoleUtils {

  static List<GrantedAuthority> superUser = Arrays.asList(new SimpleGrantedAuthority[] { new SimpleGrantedAuthority("CREATE"), new SimpleGrantedAuthority("DELETE"), new SimpleGrantedAuthority("EDIT"), new SimpleGrantedAuthority("QUERY") });;
  static List<GrantedAuthority> manager = Arrays.asList(new SimpleGrantedAuthority[] { new SimpleGrantedAuthority("DELETE"), new SimpleGrantedAuthority("EDIT"), new SimpleGrantedAuthority("QUERY") });;
  static List<GrantedAuthority> operator = Arrays.asList(new SimpleGrantedAuthority[] { new SimpleGrantedAuthority("CREATE"), new SimpleGrantedAuthority("QUERY") });;

  public static List<GrantedAuthority> getRoles(String role) {
    switch (role) {
      case "superUser": return superUser;
      case "manager": return manager;
      case "operator": return operator;
      default: return operator;
    }
  }
}
