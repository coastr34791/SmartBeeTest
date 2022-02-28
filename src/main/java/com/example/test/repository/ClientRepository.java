package com.example.test.repository;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.test.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
  Optional<Client> findByName(String name);

  Boolean existsByName(String name);

  Boolean existsByEmail(String email);
  
//  @Query("SELECT NAME FROM CLIENT WHERE COMPANY_ID = :companyId AND NAME = :name ")
//  public List<Client> getUserListByCompanyIdAndName(@Param("companyId") long companyId, @Param("name") String name);
  @Transactional
  @Modifying
  @Query("UPDATE CLIENT SET COMPANY_ID = :companyId, NAME = :name, EMAIL = :email, PHONE = :phone, UPDATE_BY = :updateBy, UPDATE_AT = :updateAt WHERE id = :id ")
  public int updateById(
      @Param("companyId") long companyId, @Param("name") String name, @Param("email") String email, 
      @Param("phone") String phone, @Param("updateBy") String updateBy, @Param("updateAt") Timestamp updateAt, @Param("id") long id);


}