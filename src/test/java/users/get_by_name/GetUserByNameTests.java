package users.get_by_name;

import dto.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import users.UserBaseTest;

import static org.hamcrest.Matchers.equalTo;

public class GetUserByNameTests extends UserBaseTest {

    private final String userName = String.valueOf(System.currentTimeMillis());
    private final String firstName = "Alena";
    private final String lastName = "Ivanova";
    private final String phone = "+7-991-991-91-91";
    private final String email = "email@mail.ru";
    private final long userStatus = 999L;
    private final long id = 681404L;

    @BeforeEach
    public void createUserForTest() {
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

        UserDTO userCreationResponse = userApi.getUserByName(userName)
                .then()
                .statusCode(200)
                .log().all()
                .extract().body().as(UserDTO.class);

        Assertions.assertEquals(id, userCreationResponse.getId(),
                "Incorrect id field value");
        Assertions.assertEquals(userStatus, userCreationResponse.getUserStatus(),
                "Incorrect userStatus field content");
        Assertions.assertEquals(userName, userCreationResponse.getUsername(),
                "Incorrect username field content");
        Assertions.assertEquals(firstName, userCreationResponse.getFirstName(),
                "Incorrect firstName field content");
        Assertions.assertEquals(lastName, userCreationResponse.getLastName(),
                "Incorrect lastName field content");
        Assertions.assertEquals(phone, userCreationResponse.getPhone(),
                "Incorrect phone field content");
        Assertions.assertEquals(email, userCreationResponse.getEmail(),
                "Incorrect email field content");
    }

}
