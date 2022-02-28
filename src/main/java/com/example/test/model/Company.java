package com.example.test.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity(name = "COMPANY")
@Table(name = "COMPANY")
public class Company {
  @Id
  @GeneratedValue
  private Long id;
  @Column
  private String name;
  @Column
  private String address;
  @Column
  private String createBy;
  @Column
  private Timestamp createAt;
  @Column
  private String updateBy;
  @Column
  private Timestamp updateAt;

  public Company() {}
  public Company(Long id) {
    this.id = id;
  }
  public Company(String name, String address, String createBy, 
      Timestamp createAt, String updateBy, Timestamp updateAt) {
    this.name = name;
    this.address = address;
    this.createBy= createBy;
    this.createAt = createAt;
    this.updateBy = updateBy;
    this.updateAt = updateAt;
  }
  
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCreateBy() {
    return createBy;
  }

  public void setCreateBy(String createBy) {
    this.createBy = createBy;
  }

  public Timestamp getCreateAt() {
    return createAt;
  }

  public void setCreateAt(Timestamp createAt) {
    this.createAt = createAt;
  }

  public String getUpdateBy() {
    return updateBy;
  }

  public void setUpdateBy(String updateBy) {
    this.updateBy = updateBy;
  }

  public Timestamp getUpdateAt() {
    return updateAt;
  }

  public void setUpdateAt(Timestamp updateAt) {
    this.updateAt = updateAt;
  }
}