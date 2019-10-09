package com.microservices.rest.webservices.restfulwebservices.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserResource {

  @Autowired
  UserDaoService userDaoService;

  @GetMapping("/users")
  public List<User> retreiveAllUsers() {
    return userDaoService.findAll();
  }

  @DeleteMapping("/users/{id}")
  public void deleteUser(@PathVariable int id) {
    User deletedUser = userDaoService.deleteById(id);
    if(deletedUser == null)
      throw new UserNotFoundException("id -"+id);
  }

  @GetMapping("/users/{id}")
  public User retreiveUser(@PathVariable int id) {
    User user = userDaoService.findOne(id);
    if(user == null)
      throw new UserNotFoundException("id-"+id);

    return user;
  }

  @PostMapping("/users")
  public ResponseEntity createUser(@Valid @RequestBody User user) {
    User savedUser = userDaoService.save(user);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(savedUser.getId()).toUri();
    return ResponseEntity.created(location).build();
  }

}
