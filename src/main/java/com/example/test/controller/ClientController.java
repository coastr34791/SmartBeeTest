package com.example.test.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.model.Client;
import com.example.test.model.Company;
import com.example.test.model.User;
import com.example.test.model.request.ClientRequest;
import com.example.test.repository.ClientRepository;
import com.example.test.repository.CompanyRepository;
import com.example.test.service.MainService;
import com.example.test.utils.DateUtils;
import com.example.test.utils.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/client")
public class ClientController {

  @Autowired
  ClientRepository clientRepository;

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
  public ResponseEntity<?> create(HttpServletRequest request, @Valid @RequestBody ClientRequest clientRequest) {

    User builder = mainService.getUserFromToken(request);

    if (!companyRepository.exists(Example.of(new Company(clientRequest.getCompanyId())))) {
      return ResponseEntity.badRequest().body("Company is not exists");
    }

    clientRepository.save(new Client(clientRequest.getCompanyId(), clientRequest.getName(), clientRequest.getEmail(),
        clientRequest.getPhone(), builder.getUsername(), dateUtils.getNow(), builder.getUsername(),
        dateUtils.getNow()));

    return ResponseEntity.ok("Client created successfully!");
  }

  @PostMapping("/createAll")
  @PreAuthorize("hasAuthority('CREATE')")
  public ResponseEntity<?> createAll(HttpServletRequest request,
      @Valid @RequestBody List<ClientRequest> clientRequestList) {

    User builder = mainService.getUserFromToken(request);
    List<Client> clientList = new ArrayList<Client>();
    int failCnt = 0;
    for (ClientRequest clientRequest : clientRequestList) {

      if (!companyRepository.exists(Example.of(new Company(clientRequest.getCompanyId())))) {
        failCnt++;
      }

      clientList.add(new Client(clientRequest.getCompanyId(), clientRequest.getName(), clientRequest.getEmail(),
          clientRequest.getPhone(), builder.getUsername(), dateUtils.getNow(), builder.getUsername(),
          dateUtils.getNow()));
    }
    int successCnt = clientRepository.saveAll(clientList).size();
    return ResponseEntity.ok("Client created " + successCnt + " successfully! " + failCnt + " fail.");
  }

  @PostMapping("/delete")
  @PreAuthorize("hasAuthority('DELETE')")
  public ResponseEntity<?> delete(HttpServletRequest request, @Valid @RequestBody ClientRequest clientRequest) {
    Client client = new Client(clientRequest.getId());
    if (!clientRepository.exists(Example.of(client))) {
      return ResponseEntity.badRequest().body("Client is not exists");
    }
    clientRepository.delete(client);
    return ResponseEntity.ok("Ssuccessfully deleted!");
  }

  @PostMapping("/update")
  @PreAuthorize("hasAuthority('EDIT')")
  public ResponseEntity<?> update(HttpServletRequest request, @Valid @RequestBody ClientRequest clientRequest) {

    User builder = mainService.getUserFromToken(request);

    if (!clientRepository.exists(Example.of(new Client(clientRequest.getId())))) {
      return ResponseEntity.badRequest().body("Client is not exists");
    }
    if (!companyRepository.exists(Example.of(new Company(clientRequest.getCompanyId())))) {
      return ResponseEntity.badRequest().body("Company is not exists");
    }

    clientRepository.updateById(clientRequest.getCompanyId(), clientRequest.getName(), clientRequest.getEmail(),
        clientRequest.getPhone(), builder.getUsername(), dateUtils.getNow(), clientRequest.getId());

    return ResponseEntity.ok("Edit successfully!");
  }

  @PostMapping("/query")
  @PreAuthorize("hasAuthority('QUERY')")
  public ResponseEntity<?> query(HttpServletRequest request, @Valid @RequestBody ClientRequest clientRequest) {
    Client client = new Client();

    ExampleMatcher matcher = ExampleMatcher.matching();
    if (clientRequest.getId() != null && clientRequest.getId() != 0L) {
      client.setId(clientRequest.getId());
    }
    if (clientRequest.getCompanyId() != null && clientRequest.getCompanyId() != 0L) {
      client.setCompanyId(clientRequest.getCompanyId());
    }
    if (StringUtils.hasText(clientRequest.getName())) {
      client.setName(clientRequest.getName());
      matcher = matcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
    }
    if (StringUtils.hasText(clientRequest.getEmail())) {
      client.setEmail(clientRequest.getEmail());
      matcher = matcher.withMatcher("email", ExampleMatcher.GenericPropertyMatchers.contains());
    }
    if (StringUtils.hasText(clientRequest.getPhone())) {
      client.setPhone(clientRequest.getPhone());
      matcher = matcher.withMatcher("phone", ExampleMatcher.GenericPropertyMatchers.contains());
    }

    Example<Client> example = Example.of(client, matcher);
    List<Client> list = clientRepository.findAll(example);

    return ResponseEntity.ok().body(list);
  }

}
