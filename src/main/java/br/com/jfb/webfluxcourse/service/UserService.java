package br.com.jfb.webfluxcourse.service;

import br.com.jfb.webfluxcourse.entity.User;
import br.com.jfb.webfluxcourse.mapper.UserMapper;
import br.com.jfb.webfluxcourse.model.request.UserRequest;
import br.com.jfb.webfluxcourse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
    return repository.findById(id);
  }
}
