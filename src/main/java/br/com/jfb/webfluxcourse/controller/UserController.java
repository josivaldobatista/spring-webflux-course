package br.com.jfb.webfluxcourse.controller;

import br.com.jfb.webfluxcourse.model.request.UserRequest;
import br.com.jfb.webfluxcourse.model.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping(value = "/users")
public interface UserController {

  @PostMapping
  ResponseEntity<Mono<Void>> save(@RequestBody UserRequest request);
  @GetMapping(value = "/{id}")
  ResponseEntity<Mono<UserResponse>> findById(@PathVariable("id") String id);
  @GetMapping
  ResponseEntity<Flux<UserResponse>> findAll();
  @PatchMapping(value = "/{id}")
  ResponseEntity<Mono<UserResponse>> update(@PathVariable("id") String id, @RequestBody UserRequest request);
  @DeleteMapping("/{id}")
  ResponseEntity<Mono<Void>> delete(@PathVariable("id") String id);


}
