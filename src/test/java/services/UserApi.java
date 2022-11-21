package services;

import static io.restassured.RestAssured.given;

import dto.UserDTO;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class UserApi {
  private static final String USER = "/user";
  private static String BASE_URL = "";
  private final RequestSpecification requestSpecification;
  private final ResponseSpecification responseSpecification;

  public UserApi() {
    BASE_URL = System.getProperty("base.url", "https://petstore.swagger.io/v");

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
        .body(user)
        .when()
        .post(USER)
        .then()
        .spec(responseSpecification);
  }

  public ValidatableResponse createUserWithError(String jsonString) {
    return given(requestSpecification)
        .body(jsonString)
        .when()
        .post(USER)
        .then()
        .log().all();
  }

  public Response getUserByName(String userName) {
    return given(requestSpecification)
        .when()
        .get(USER + "/" + userName);
  }

  public void authorizeUser(String userName, String password) {
    given(requestSpecification)
        .when()
        .get(USER + "/" + "login?username=" + userName + "&password=" + password)
        .then()
        .log().all();
  }

}
