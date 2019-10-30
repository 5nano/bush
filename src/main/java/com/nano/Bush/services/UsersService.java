package com.nano.Bush.services;

import com.nano.Bush.datasources.UsersDao;
import com.nano.Bush.model.User;
import com.nano.Bush.model.UserCredentials;
import io.vavr.control.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class UsersService {

    private static final Logger logger = LoggerFactory.getLogger(UsersService.class);
    @Autowired
    UsersDao usersDao;

    private static boolean equalPasswords(String p1, String p2) {
        return !isNull(p1) && p1.equals(p2);
    }

    public List<User> getUsers() throws SQLException {
        return usersDao.getUsers();
    }

    public Option<User> getUserByUserName(String username){
        return usersDao.getUserByUsername(username);
    }

    public void updateUser(User user) throws SQLException {
        user.setPassword(generateMD5HashPass(user.getPassword()));
        // 1 es el id de nanotica
        Integer cId = Option.of(user.getCompanyId()).getOrElse(1);
        user.setCompanyId(cId);
        usersDao.modify(user);
    }

    public void insertUser(User user) throws SQLException {
        user.setPassword(generateMD5HashPass(user.getPassword()));
        // 1 es el id de nanotica
        Integer cId = Option.of(user.getCompanyId()).getOrElse(1);
        user.setCompanyId(cId);
        usersDao.insert(user);
    }

    public boolean isValidUser(UserCredentials userCredentials) {
        return usersDao.getUserByUsername(userCredentials.username)
                .map(user -> equalPasswords(user.getPassword(), generateMD5HashPass(userCredentials.password)))
                .getOrElse(false);
    }

    private String generateMD5HashPass(String password) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error al hashear la pass del User ");
        }
        return generatedPassword;
    }
}
