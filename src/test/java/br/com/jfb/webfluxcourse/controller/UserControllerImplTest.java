package br.com.jfb.webfluxcourse.controller;

import br.com.jfb.webfluxcourse.entity.User;
import br.com.jfb.webfluxcourse.mapper.UserMapper;
import br.com.jfb.webfluxcourse.model.request.UserRequest;
import br.com.jfb.webfluxcourse.service.UserService;
import com.mongodb.reactivestreams.client.MongoClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@SpringBootTest
@AutoConfigureWebTestClient
@ExtendWith(SpringExtension.class)
class UserControllerImplTest {

  @Autowired
  private WebTestClient webTestClient;

  @MockBean
  private UserService service;
  @MockBean
  private UserMapper mapper;
  @MockBean
  private MongoClient mongoClient;

  @Test
  @DisplayName("Test endpoint save with success")
  void should_save_user_success() {
    final var request = new UserRequest("Maria Brown", "maria@email.com", "123");

    when(service.save(any(UserRequest.class))).thenReturn(Mono.just(User.builder().build()));

    webTestClient.post().uri("/users")
        .contentType(APPLICATION_JSON)
        .body(fromValue(request))
        .exchange()
        .expectStatus().isCreated();

    verify(service, times(1)).save(any(UserRequest.class));
  }

  @Test
  void findById() {
  }

  @Test
  void findAll() {
  }

  @Test
  void update() {
  }

  @Test
  void delete() {
  }
}