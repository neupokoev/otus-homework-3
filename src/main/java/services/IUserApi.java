package services;

import dto.UserDTO;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public interface IUserApi {
  ValidatableResponse createUser(UserDTO user);

  ValidatableResponse createUserWithError(String jsonString);

  Response getUserByName(String userName);

  void authorizeUser(String userName, String password);
}
