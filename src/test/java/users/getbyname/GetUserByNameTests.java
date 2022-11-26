package users.getbyname;

import static org.hamcrest.Matchers.equalTo;

import com.google.inject.Guice;
import com.google.inject.Inject;
import dto.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.UserApi;
import support.UserModule;

public class GetUserByNameTests {

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
  public void createUserForTest() {
    Guice.createInjector(new UserModule()).injectMembers(this);
    UserDTO user = UserDTO.builder()
        .id(id)
        .username(userName)
        .userStatus(userStatus)
        .firstName(firstName)
        .lastName(lastName)
        .phone(phone)
        .email(email)
        .build();
    userApi.createUser(user).body("code", equalTo(200));
    //userApi.authorizeUser(userName);
  }

  @Test
  public void getExistedUserByNameUserTest() {

    UserDTO userGettingResponse = userApi.getUserByName(userName)
        .then()
        .statusCode(200)
        .log().all()
        .extract().body().as(UserDTO.class);

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
