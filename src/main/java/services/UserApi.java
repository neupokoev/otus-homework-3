package services;

import com.google.inject.Inject;
import dto.UserDTO;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class UserApi implements IUserApi {
  private final String user;
  private final String baseUrl;
  private final RequestSpecification requestSpecification;
  private final ResponseSpecification responseSpecification;

  @Inject
  public UserApi() {
    baseUrl = System.getProperty("base.url", "https://petstore.swagger.io/v");
    this.user = "/user";
    this.requestSpecification = RestAssured.given()
        .baseUri(baseUrl)
        .contentType(ContentType.JSON)
        .log().all();
    this.responseSpecification = new ResponseSpecBuilder()
        .expectStatusCode(200)
        .log(LogDetail.ALL)
        .build();
  }

  public ValidatableResponse createUser(UserDTO user) {
    return RestAssured.given(requestSpecification)
        .body(user)
        .when()
        .post(this.user)
        .then()
        .spec(responseSpecification);
  }

  public ValidatableResponse createUserWithError(String jsonString) {
    return RestAssured.given(requestSpecification)
        .body(jsonString)
        .when()
        .post(user)
        .then()
        .log().all();
  }

  public Response getUserByName(String userName) {
    return RestAssured.given(requestSpecification)
        .when()
        .get(user + "/" + userName);
  }

  public void authorizeUser(String userName, String password) {
    RestAssured.given(requestSpecification)
        .when()
        .get(user + "/" + "login?username=" + userName + "&password=" + password)
        .then()
        .log().all();
  }

}
