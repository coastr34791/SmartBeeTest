package com.example.test.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
  @GetMapping("/all")
  public String allAccess() {
    return "Public Content.";
  }

  @GetMapping("/roleAll")
  @PreAuthorize("hasAuthority('CREATE') or hasAuthority('DELETE') or hasAuthority('EDIT') or hasAuthority('QUERY')")
  public String userAccess() {
    return "User Content.";
  }

  @GetMapping("/roleCREATE")
  @PreAuthorize("hasAuthority('CREATE')")
  public String roleCREATE() {
    return "CREATE.";
  }
  
  @GetMapping("/roleDELETE")
  @PreAuthorize("hasAuthority('DELETE')")
  public String roleDELETE() {
    return "DELETE.";
  }
  
  @GetMapping("/roleEDIT")
  @PreAuthorize("hasAuthority('EDIT')")
  public String roleEDIT() {
    return "EDIT.";
  }
  
  @GetMapping("/roleQUERY")
  @PreAuthorize("hasAuthority('QUERY')")
  public String roleQUERY() {
    return "QUERY.";
  }
}
