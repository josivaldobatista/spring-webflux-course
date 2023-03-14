package br.com.jfb.webfluxcourse.controller.impl;

import br.com.jfb.webfluxcourse.controller.UserController;
import br.com.jfb.webfluxcourse.mapper.UserMapper;
import br.com.jfb.webfluxcourse.model.request.UserRequest;
import br.com.jfb.webfluxcourse.model.response.UserResponse;
import br.com.jfb.webfluxcourse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class UserControllerImpl implements UserController {

  @Autowired
  private UserService service;
  @Autowired
  private UserMapper mapper;

  @Override
  public ResponseEntity<Mono<Void>> save(final UserRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(service.save(request).then());
  }

  @Override
  public ResponseEntity<Mono<UserResponse>> findById(String id) {
    return ResponseEntity.ok().body(service.findById(id).map(mapper::toResponse)); // <- Method reference
  }

  @Override
  public ResponseEntity<Flux<UserResponse>> findAll() {
    return null;
  }

  @Override
  public ResponseEntity<Mono<UserResponse>> update(String id, UserRequest request) {
    return null;
  }

  @Override
  public ResponseEntity<Mono<Void>> delete(String id) {
    return null;
  }
}
