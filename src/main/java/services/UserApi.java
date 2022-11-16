package services;

import dto.UserDTO;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;

public class UserApi {
    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final String USER = "/user";
    private final RequestSpecification requestSpecification;
    private final ResponseSpecification responseSpecification;

    public UserApi() {
        requestSpecification = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .log().all();

        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .log(LogDetail.ALL)
                .build();
    }

    public ValidatableResponse createUser(UserDTO user) {
        return given(requestSpecification)
                // @formatter:off
                    .body(user)
                .when()
                    .post(USER)
                .then()
                    .spec(responseSpecification);
                // @formatter:on
    }

    public ValidatableResponse createUserWithError(String jsonString) {
        return given(requestSpecification)
                // @formatter:off
                    .body(jsonString)
                .when()
                    .post(USER)
                .then()
                    .log().all();
                // @formatter:on
    }

    public Response getUserByName(String userName) {
        return given(requestSpecification)
                // @formatter:off
                .when()
                    .get(USER + "/" + userName);
                // @formatter:on
    }

    public void authorizeUser(String userName) {
        given(requestSpecification)
                // @formatter:off
                .when()
                    .get(USER + "/" + "login?username=" + userName + "&password=password")
                .then()
                    .log().all();
                // @formatter:on
    }
}
