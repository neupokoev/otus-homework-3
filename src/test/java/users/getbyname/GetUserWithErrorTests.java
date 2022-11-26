package users.getbyname;

import com.google.inject.Guice;
import com.google.inject.Inject;
import dto.UserOut;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.UserApi;
import support.UserModule;

public class GetUserWithErrorTests {

  @Inject
  private UserApi userApi;

  @BeforeEach
  public void setup() {
    Guice.createInjector(new UserModule()).injectMembers(this);
  }

  @Test
  public void getUserByNameWith404ErrorTest() {
    final String notExistedUserName = "not_existed_user_" + System.currentTimeMillis();

    UserOut userCreationResponse = userApi.getUserByName(notExistedUserName)
        .then()
        .statusCode(404)
        .log().all()
        .extract().body().as(UserOut.class);

    Assertions.assertEquals(1L, userCreationResponse.getCode(),
        "Incorrect code");
    Assertions.assertEquals("error", userCreationResponse.getType(),
        "Incorrect type");
    Assertions.assertEquals("User not found", userCreationResponse.getMessage(),
        "Incorrect message");
  }

}
