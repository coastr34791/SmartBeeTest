package com.example.test.utils;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

@Component
public class DateUtils {

  public Timestamp getNow() {
    Long datetime = System.currentTimeMillis();
    Timestamp timestamp = new Timestamp(datetime);
    return timestamp;
  }
}
