package com.example.test.repository;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.test.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
  Optional<Company> findByName(String name);

  Boolean existsByName(String name);
  
  Boolean existsByid(Long id);
  
  @Transactional
  @Modifying
  @Query("UPDATE COMPANY SET NAME = :name, ADDRESS = :address, UPDATE_BY = :updateBy, UPDATE_AT = :updateAt WHERE id = :id ")
  public int updateById(@Param("name") String name, @Param("address") String address, @Param("updateBy") String updateBy, @Param("updateAt") Timestamp updateAt, @Param("id") long id);

}