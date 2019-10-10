package com.microservices.rest.webservices.restfulwebservices.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
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
import java.util.Optional;

@RestController
public class UserJPAResource {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PostRepository postRepository;

  @GetMapping("/jpa/users")
  public List<User> retreiveAllUsers() {
    return userRepository.findAll();
  }

  @DeleteMapping("/jpa/users/{id}")
  public void deleteUser(@PathVariable int id) {
    userRepository.deleteById(id);
  }

  @GetMapping("/jpa/users/{id}")
  public Resource<User> retreiveUser(@PathVariable int id) {
    Optional<User> user = userRepository.findById(id);
    if (!user.isPresent())
      throw new UserNotFoundException("id-" + id);

    Resource<User> resource = new Resource<>(user.get());
    ControllerLinkBuilder linkTo = ControllerLinkBuilder
        .linkTo(ControllerLinkBuilder.methodOn(this.getClass()).retreiveAllUsers());
    resource.add(linkTo.withRel("all-users"));

    return resource;
  }

  @PostMapping("/jpa/users")
  public ResponseEntity createUser(@Valid @RequestBody User user) {
    User savedUser = userRepository.save(user);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(savedUser.getId()).toUri();
    return ResponseEntity.created(location).build();
  }

  @GetMapping("/jpa/users/{id}/posts")
  public List<Post> retreiveAllPosts(@PathVariable int id){
    Optional<User> userOptional = userRepository.findById(id);
    if(!userOptional.isPresent()) {
      throw new UserNotFoundException("id-"+id);
    }
    return userOptional.get().getPosts();
  }

  @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<Object> createUser(@PathVariable int id,@Valid @RequestBody Post post) {
    Optional<User> userOptional = userRepository.findById(id);
    if(!userOptional.isPresent()) {
        throw new UserNotFoundException("id-"+id);
    }
    User user = userOptional.get();
    post.setUser(user);
    postRepository.save(post);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(post.getId()).toUri();

    return ResponseEntity.created(location).build();
  }

}
