package com.rayllanderson.gerenciadordecompras.integration;

import com.rayllanderson.gerenciadordecompras.domain.dtos.user.UserPostRequestBody;
import com.rayllanderson.gerenciadordecompras.domain.dtos.user.UserPutPasswordRequestBody;
import com.rayllanderson.gerenciadordecompras.domain.dtos.user.UserPutRequestBody;
import com.rayllanderson.gerenciadordecompras.domain.dtos.user.UserResponseBody;
import com.rayllanderson.gerenciadordecompras.utils.user.UserCreator;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

@Log4j2
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIT extends BaseApiTest{

    @Test
    void register_RegisterAnUser_WhenSuccessful() {
        UserPostRequestBody userToBeRegistered = UserCreator.createUserToBeRegistered();

        ResponseEntity<UserResponseBody> userResponseEntity = rest.postForEntity("/api/v1/users",
                userToBeRegistered, UserResponseBody.class);

        Assertions.assertThat(userResponseEntity).isNotNull();
        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(userResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(userResponseEntity.getBody().getId()).isNotNull();
    }

    @Test
    void register_RegisterUsersWithoutEmail_WhenSuccessful() {
        UserPostRequestBody userToBeRegistered = UserCreator.createUserToBeRegistered();

        //setando email para "", dessa forma, garanto que pode registrar mais de 1 usuário com email "" (vazio)
        userToBeRegistered.setEmail("");

        UserPostRequestBody anotherUserToBeRegistered = UserCreator.createAnotherUserPostRequestBody();

        //precisa setar outro username por conta do @BeforeAll que já cadastra um usuário com username do objeto acima
        anotherUserToBeRegistered.setUsername("anyUsername");

        //setando vazio aqui também, ou seja, "" é igual a "" e a intenção é registrar com sucesso,
        // mesmo já existindo um email "" na base de dados.
        anotherUserToBeRegistered.setEmail("");


        //cadastrando primeiro usuário
        ResponseEntity<UserResponseBody> firstResponseEntity = rest.postForEntity("/api/v1/users",
                userToBeRegistered, UserResponseBody.class);

        //cadastrando segundo usuário
        ResponseEntity<UserResponseBody> secondResponseEntity = rest.postForEntity("/api/v1/users",
                anotherUserToBeRegistered, UserResponseBody.class);

        Assertions.assertThat(firstResponseEntity).isNotNull();
        Assertions.assertThat(firstResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(firstResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(firstResponseEntity.getBody().getId()).isNotNull();
        Assertions.assertThat(firstResponseEntity.getBody().getEmail()).isEmpty();

        Assertions.assertThat(secondResponseEntity).isNotNull();
        Assertions.assertThat(secondResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(secondResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(secondResponseEntity.getBody().getId()).isNotNull();
        Assertions.assertThat(secondResponseEntity.getBody().getEmail()).isEmpty();
    }

    @Test
    void register_NonRegisterAnUser_WhenUsernameIsInvalid() {
        UserPostRequestBody userToBeRegistered = UserCreator.createUserToBeRegistered();
        userToBeRegistered.setUsername("");

        ResponseEntity<UserResponseBody> userResponseEntity = rest.postForEntity("/api/v1/users",
                userToBeRegistered, UserResponseBody.class);

        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void register_NonRegisterAnUser_WhenEmailIsInvalid() {
        UserPostRequestBody userToBeRegistered = UserCreator.createUserToBeRegistered();
        userToBeRegistered.setEmail("any_non_formatted_email");

        ResponseEntity<UserResponseBody> userResponseEntity = rest.postForEntity("/api/v1/users",
                userToBeRegistered, UserResponseBody.class);

        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void updatePassword_UpdateUserPassword_WhenSuccessful() {
        UserPutPasswordRequestBody user = UserCreator.createUserPutPasswordRequestBody();

        ResponseEntity<Void> responseEntity = put("/api/v1/users/update/password", user, Void.class);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void updatePassword_NonUpdateUserPassword_WhenPasswordIsLessThan3Characters() {
        UserPutPasswordRequestBody user = UserCreator.createUserPutPasswordRequestBody();

        user.setPassword("3");

        ResponseEntity<Void> responseEntity = put("/api/v1/users/update/password", user, Void.class);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void updateUsernameEmailOrName_UpdateUser_WhenSuccessful() {
        UserPutRequestBody user = UserCreator.createUserPutRequestBody();
        user.setUsername("kaguyaSama");
        user.setEmail("kaguyaShinomiya@sama.com");
        user.setName("Kaguya Shinomiya");

        ResponseEntity<Void> responseEntity = put("/api/v1/users/update", user, Void.class);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void updateUsernameEmailOrName_NonUpdateUser_WhenUsernameIsAlreadyInUse() {
        UserPutRequestBody user = UserCreator.createUserPutRequestBody();
        user.setUsername("kaguya"); //username already exists in database

        ResponseEntity<Void> responseEntity = put("/api/v1/users/update", user, Void.class);

        log.info(responseEntity.getStatusCode());
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void updateUsernameEmailOrName_NonUpdateUser_WhenEmailIsAlreadyInUse() {
        UserPutRequestBody user = UserCreator.createUserPutRequestBody();
        user.setEmail("kaguya@sama.com"); //email already exists in database

        ResponseEntity<Void> responseEntity = put("/api/v1/users/update", user, Void.class);

        log.info(responseEntity.getStatusCode());
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}

