package br.com.jfb.webfluxcourse.service;

import br.com.jfb.webfluxcourse.entity.User;
import br.com.jfb.webfluxcourse.mapper.UserMapper;
import br.com.jfb.webfluxcourse.model.request.UserRequest;
import br.com.jfb.webfluxcourse.repository.UserRepository;
import br.com.jfb.webfluxcourse.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static java.lang.String.format;

@Service
public class UserService {

  @Autowired
  private UserRepository repository;
  @Autowired
  private UserMapper mapper;


  public Mono<User> save(final UserRequest request) {
    return repository.save(mapper.toEntity(request));
  }

  public Mono<User> findById(final String id) {
    return repository.findById(id).switchIfEmpty(Mono.error(
        new ObjectNotFoundException(format("Object not found, ID: %s, Type: %s", id, User.class.getSimpleName()))));
  }
}
