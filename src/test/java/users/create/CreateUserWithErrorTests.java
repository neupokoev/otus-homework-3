package users.create;

import dto.UserOut;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import users.UserBaseTest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class CreateUserWithErrorTests extends UserBaseTest {

    @Test
    public void createUserWithInvalidParametersTest() {
        UserOut userCreationResponse = userApi
                .createUserWithError("{ \"id\": \"string is instead of integer value\"}")
                .statusCode(500)
                .time(lessThan(5000L))
                .body("message", equalTo("something bad happened"))
                .body("code", equalTo(500))
                .body("type", equalTo("unknown"))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/CreateUser.json"))
                .extract().body().as(UserOut.class);

        Assertions.assertEquals(500L, userCreationResponse.getCode(),
                "Incorrect code");
        Assertions.assertEquals("unknown", userCreationResponse.getType(),
                "Incorrect type");
        Assertions.assertEquals("something bad happened", userCreationResponse.getMessage(),
                "Incorrect message");
    }
}
