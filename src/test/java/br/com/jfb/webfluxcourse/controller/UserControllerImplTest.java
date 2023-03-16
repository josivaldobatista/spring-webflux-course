package br.com.jfb.webfluxcourse.controller;

import br.com.jfb.webfluxcourse.entity.User;
import br.com.jfb.webfluxcourse.model.request.UserRequest;
import br.com.jfb.webfluxcourse.service.UserService;
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
import static org.springframework.http.HttpStatus.BAD_REQUEST;
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

  @Test
  @DisplayName("Test endpoint save with success")
  void should_test_endpoint_save_with_success() {
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
  @DisplayName("Test endpoint save with bad request")
  void should_test_endpoint_save_with_bad_request() {
    final var request = new UserRequest(" Maria Brown", "maria@email.com", "123");

    when(service.save(any(UserRequest.class))).thenReturn(Mono.just(User.builder().build()));

    webTestClient.post().uri("/users")
        .contentType(APPLICATION_JSON)
        .body(fromValue(request))
        .exchange()
        .expectStatus().isBadRequest()
        .expectBody()
        .jsonPath("$.path").isEqualTo("/users")
        .jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
        .jsonPath("$.error").isEqualTo("Validation Error")
        .jsonPath("$.message").isEqualTo("Error on validation attributes")
        .jsonPath("$.errors[0].fieldName").isEqualTo("name")
        .jsonPath("$.errors[0].message").isEqualTo("field cannot have blank spaces at the beginning or at end");
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