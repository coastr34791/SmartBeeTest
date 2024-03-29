package com.example.test.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.model.Client;
import com.example.test.model.Company;
import com.example.test.model.User;
import com.example.test.model.request.CompanyRequest;
import com.example.test.repository.CompanyRepository;
import com.example.test.service.MainService;
import com.example.test.utils.DateUtils;
import com.example.test.utils.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/company")
public class CompanyController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  PasswordEncoder encoder;
  
  @Autowired
  CompanyRepository companyRepository;
  
  @Autowired
  MainService mainService;

  @Autowired
  JwtUtils jwtUtils;
  
  @Autowired
  DateUtils dateUtils;
  
  @PostMapping("/create")
  @PreAuthorize("hasAuthority('CREATE')")
  public ResponseEntity<?> create(HttpServletRequest request, 
      @Valid @RequestBody CompanyRequest companyRequest) {
    
    if (companyRepository.existsByName(companyRequest.getName())) {
      return ResponseEntity.badRequest().body("Error: Name is already taken!");
    }
    
    User builder = mainService.getUserFromToken(request);
    companyRepository.save(new Company(companyRequest.getName(), companyRequest.getAddress(), 
        builder.getUsername(), dateUtils.getNow(), builder.getUsername(), dateUtils.getNow()));
    
    return ResponseEntity.ok("Company created successfully!");
  }

  @PostMapping("/delete")
  @PreAuthorize("hasAuthority('DELETE')")
  public ResponseEntity<?> delete(HttpServletRequest request, @Valid @RequestBody CompanyRequest companyRequest) {
    Company company = new Company(companyRequest.getId());
    if(!companyRepository.exists(Example.of(company))) {
      return ResponseEntity.badRequest().body("Company is not exists");
    }
    companyRepository.delete(company);
    return ResponseEntity.ok("Ssuccessfully deleted!");
  }

  @PostMapping("/update")
  @PreAuthorize("hasAuthority('EDIT')")
  public ResponseEntity<?> update(HttpServletRequest request, @Valid @RequestBody CompanyRequest companyRequest) {
    
    if(!companyRepository.exists(Example.of(new Company(companyRequest.getId())))) {
      return ResponseEntity.badRequest().body("Company is not exists");
    }
    User builder = mainService.getUserFromToken(request);
    companyRepository.updateById(companyRequest.getName(), companyRequest.getAddress(), builder.getUsername(), dateUtils.getNow(), companyRequest.getId());
    return ResponseEntity.ok("Edit successfully!");
  }
  
  @PostMapping("/query")
  @PreAuthorize("hasAuthority('QUERY')")
  public ResponseEntity<?> query(HttpServletRequest request, @Valid @RequestBody CompanyRequest companyRequest) {
    Company company = new Company();

    ExampleMatcher matcher = ExampleMatcher.matching();
    if (companyRequest.getId() != null && companyRequest.getId() != 0L) {
      company.setId(companyRequest.getId());
    }
    if (StringUtils.hasText(companyRequest.getName())) {
      company.setName(companyRequest.getName());
      matcher = matcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
    }
    if (StringUtils.hasText(companyRequest.getAddress())) {
      company.setAddress(companyRequest.getAddress());
      matcher = matcher.withMatcher("address", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    Example<Company> example = Example.of(company, matcher);
    List<Company> list = companyRepository.findAll(example);

    return ResponseEntity.ok().body(list);
  }
}
