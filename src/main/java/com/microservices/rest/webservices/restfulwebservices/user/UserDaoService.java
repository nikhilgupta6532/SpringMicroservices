package com.microservices.rest.webservices.restfulwebservices.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class UserDaoService {
  private static List<User> users = new ArrayList<>();

  private static int usersCount = 3;

  static {
    users.add(new User(1, "Nikhil", new Date()));
    users.add(new User(2, "Shubham", new Date()));
    users.add(new User(3, "Ankit", new Date()));
  }

  public List<User> findAll() {
    return users;
  }

  public User save(User user) {
    return Optional.ofNullable(user.getId()).map(userId -> {
      users.add(user);
      return user;
    }).orElseGet(() -> {
      user.setId(++usersCount);
      users.add(user);
      return user;
    });

    // if (user.getId() == null) {
    // user.setId(++usersCount);
    // }
    // users.add(user);
    // return user;
  }

  public User findOne(int id) {
    return users.stream().filter(user -> user.getId() == id).findAny().orElse(null);
  }

  public User deleteById(int id) {
    User deleteUser = findOne(id);
    if (!ObjectUtils.isEmpty(deleteUser)) {
      users.remove(deleteUser);
    }
    return deleteUser;
  }
}
