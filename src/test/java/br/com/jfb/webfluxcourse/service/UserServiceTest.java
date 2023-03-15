package br.com.jfb.webfluxcourse.service;

import br.com.jfb.webfluxcourse.entity.User;
import br.com.jfb.webfluxcourse.mapper.UserMapper;
import br.com.jfb.webfluxcourse.model.request.UserRequest;
import br.com.jfb.webfluxcourse.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository repository;
  @Mock
  private UserMapper mapper;
  @InjectMocks
  private UserService service;

  @Test
  void should_save_a_user() {
    UserRequest request = new UserRequest(
        "Maria Brown", "maria@email.com", "123");
    User entity = User.builder().build();

    when(mapper.toEntity(any(UserRequest.class))).thenReturn(entity);
    when(repository.save(any(User.class))).thenReturn(Mono.just(User.builder().build()));

    Mono<User> result = service.save(request);

    StepVerifier.create(result)
        .expectNextMatches(user -> user instanceof User)// Poderia usar com methods reference aqui
        .expectComplete()
        .verify();

    verify(repository, times(1)).save(any(User.class)); //<- só para teste essa linha
  }
}