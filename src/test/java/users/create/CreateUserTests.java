package users.create;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

import com.google.inject.Guice;
import com.google.inject.Inject;
import dto.UserDTO;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.UserApi;
import support.UserModule;

public class CreateUserTests {

  private final String userName = String.valueOf(System.currentTimeMillis());
  private final String firstName = "Alena";
  private final String lastName = "Ivanova";
  private final String phone = "+7-991-991-91-91";
  private final String email = "email@mail.ru";
  private final long userStatus = 999L;
  private final long id = 681404L;

  @Inject
  public UserApi userApi;

  @BeforeEach
  public void setup() {
    Guice.createInjector(new UserModule()).injectMembers(this);
  }

  @Test
  public void createUserPositiveTest() {
    //подготовить данные для создания пользователя
    UserDTO user = UserDTO.builder()
        .id(id)
        .username(userName)
        .userStatus(userStatus)
        .firstName(firstName)
        .lastName(lastName)
        .phone(phone)
        .email(email)
        .build();

    //поискать пользователя по имени - такого нет в системе - ответ 404
    userApi.getUserByName(userName)
        .then()
        .statusCode(404);

    //создать нового пользователя в системе
    ValidatableResponse response = userApi.createUser(user)
        .time(lessThan(5000L))
        .body("message", equalTo(Long.toString(id)))
        .body("code", equalTo(200))
        .body("type", equalTo("unknown"))
        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/CreateUser.json"));

    //получить пользователя по имени - теперь он есть (должен быть)
    UserDTO userGettingResponse = userApi.getUserByName(userName)
        .then()
        .statusCode(200)
        .log().all()
        .extract().body().as(UserDTO.class);

    //проверить, что все характеристики нового пользователя совпадают с ожидаемыми
    Assertions.assertEquals(id, userGettingResponse.getId(),
        "Incorrect id field value");
    Assertions.assertEquals(userStatus, userGettingResponse.getUserStatus(),
        "Incorrect userStatus field content");
    Assertions.assertEquals(userName, userGettingResponse.getUsername(),
        "Incorrect username field content");
    Assertions.assertEquals(firstName, userGettingResponse.getFirstName(),
        "Incorrect firstName field content");
    Assertions.assertEquals(lastName, userGettingResponse.getLastName(),
        "Incorrect lastName field content");
    Assertions.assertEquals(phone, userGettingResponse.getPhone(),
        "Incorrect phone field content");
    Assertions.assertEquals(email, userGettingResponse.getEmail(),
        "Incorrect email field content");
  }

}
