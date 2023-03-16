package br.com.jfb.webfluxcourse.controller;

import br.com.jfb.webfluxcourse.entity.User;
import br.com.jfb.webfluxcourse.mapper.UserMapper;
import br.com.jfb.webfluxcourse.model.request.UserRequest;
import br.com.jfb.webfluxcourse.model.response.UserResponse;
import br.com.jfb.webfluxcourse.service.UserService;
import br.com.jfb.webfluxcourse.service.exception.ObjectNotFoundException;
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
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@SpringBootTest
@AutoConfigureWebTestClient
@ExtendWith(SpringExtension.class)
class UserControllerImplTest {

  public static final String ID = "123456";
  public static final String NAME = "Maria Brown";
  public static final String EMAIL = "maria@email.com";
  public static final String PASSWORD = "123";

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
  void should_test_endpoint_save_with_success() {
    final var request = new UserRequest(NAME, EMAIL, PASSWORD);

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
    final var request = new UserRequest(NAME.concat(" "), EMAIL, PASSWORD);

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
  @DisplayName("Test find by id endpoint with success")
  void should_test_find_by_id_endpoint_with_success() {
    final var userResponse = new UserResponse(ID, NAME, EMAIL, PASSWORD);

    when(service.findById(anyString())).thenReturn(Mono.just(User.builder().build()));
    when(mapper.toResponse(any(User.class))).thenReturn(userResponse);

    webTestClient.get().uri("/users/" + 123456)
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$.id").isEqualTo(123456)
        .jsonPath("$.name").isEqualTo("Maria Brown")
        .jsonPath("$.email").isEqualTo("maria@email.com")
        .jsonPath("$.password").isEqualTo(123);
  }

  @Test
  @DisplayName("Test find by id endpoint with not_found")
  void should_test_find_by_id_endpoint_with_not_found() {
    when(service.findById(anyString())).thenReturn(Mono.error(new ObjectNotFoundException("Object not found, ID: 640e8f7dc774e57dfa64cf, Type: User")));

    webTestClient.get().uri("/users/" + "640e8f7dc774e57dfa64cf")
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus().isNotFound()
        .expectBody()
        .jsonPath("$.path").isEqualTo("/users/640e8f7dc774e57dfa64cf")
        .jsonPath("$.status").isEqualTo(NOT_FOUND.value())
        .jsonPath("$.error").isEqualTo("Not Found")
        .jsonPath("$.message").isEqualTo("Object not found, ID: 640e8f7dc774e57dfa64cf, Type: User");
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