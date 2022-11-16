package users.create;

import dto.UserDTO;
import dto.UserOut;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import users.UserBaseTest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class CreateUserTests extends UserBaseTest {

    @Test
    public void createUserPositiveTest() {
        long id = 300L;

        UserDTO user = UserDTO.builder()
                .id(id)
                .userStatus(100L)
                .firstName("firstName")
                .lastName("lastName")
                .phone("+7-991-991-91-91")
                .email("email@mail.ru")
                .build();

        ValidatableResponse response = userApi.createUser(user)
                .time(lessThan(5000L))
                .body("message", equalTo(Long.toString(id)))
                .body("code", equalTo(200))
                .body("type", equalTo("unknown"))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/CreateUser.json"));

        UserOut userCreationResponse = response.extract().body().as(UserOut.class);
        Assertions.assertEquals(200L, userCreationResponse.getCode(),
                "Incorrect code");
        Assertions.assertEquals("unknown", userCreationResponse.getType(),
                "Incorrect type");
        Assertions.assertEquals(Long.toString(id), userCreationResponse.getMessage(),
                "Incorrect message");
    }
}
