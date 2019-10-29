package com.nano.Bush.utils;

import com.nano.Bush.model.User;
import com.nano.Bush.services.UsersService;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;

import static com.nano.Bush.utils.EncryptUtils.decode;
@Service
public class RequestHomeMadeInterceptor {
  @Autowired
  UsersService usersService;

  public Integer extractIdUserCreator(String user) {
    /*String user = decode(userEncoded);*/
    return usersService.getUserByUserName(user)
            .map(u ->u.getUserId().get())
            .getOrElseThrow(() -> new RuntimeException("User not found"));
  }

  public Integer extractIdCompany(String user) {
    /*String user = decode(userEncoded);*/
    return usersService.getUserByUserName(user)
            .map(u ->u.getCompanyId())
            .getOrElseThrow(() -> new RuntimeException("Company not found"));
  }

  public Tuple2<Integer, Integer> extractUserCompany(String user) {
    /*String user = decode(userEncoded);*/
    return usersService.getUserByUserName(user)
            .map(u ->Tuple.of(u.getCompanyId(),u.getUserId().get()))
            .getOrElseThrow(() -> new RuntimeException("User not found"));
  }

}
