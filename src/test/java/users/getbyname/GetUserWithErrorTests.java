package users.getbyname;

import dto.UserOut;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import users.UserBaseTest;

public class GetUserWithErrorTests extends UserBaseTest {

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
