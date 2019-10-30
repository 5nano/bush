package com.nano.Bush.utils;

import com.nano.Bush.model.User;
import com.nano.Bush.services.UsersService;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;

import static com.nano.Bush.utils.EncryptUtils.decode;
@Service
public class RequestHomeMadeInterceptor {
  private static final Logger logger = LoggerFactory.getLogger(RequestHomeMadeInterceptor.class);
  @Autowired
  UsersService usersService;

  public Integer extractIdUserCreator(String user) {
    logger.info("Incoming user {}", user);
    return usersService.getUserByUserName(user)
            .map(u ->u.getUserId().get())
            .getOrElseThrow(() -> new RuntimeException("User not found"));
  }

  public Integer extractIdCompany(String user) {
    logger.info("Incoming user {}", user);
    return usersService.getUserByUserName(user)
            .map(u ->u.getCompanyId())
            .getOrElseThrow(() -> new RuntimeException("Company not found"));
  }

  public Tuple2<Integer, Integer> extractUserCompany(String user) {
    logger.info("Incoming user {}", user);
    return usersService.getUserByUserName(user)
            .map(u ->Tuple.of(u.getCompanyId(),u.getUserId().get()))
            .getOrElseThrow(() -> new RuntimeException("User not found"));
  }

}
