package com.nano.Bush.services;

import com.nano.Bush.datasources.UsersDao;
import com.nano.Bush.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

@Service
public class UsersService {

    private static final Logger logger = LoggerFactory.getLogger(UsersService.class);

    public List<User> getUsers() throws SQLException {

        UsersDao usersDao = new UsersDao();

        return usersDao.getUsers();
    }

    public void insertUser(User user) throws SQLException {

        UsersDao usersDao = new UsersDao();

        user.setPassword(generateMD5HashPass(user));

        usersDao.insert(user);

    }

    private String generateMD5HashPass(User user) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(user.getPassword().getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error al hashear la pass del User " + user.getUsername());
        }
        return generatedPassword;
    }
}
