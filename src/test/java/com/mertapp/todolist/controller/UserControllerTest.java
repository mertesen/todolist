package com.mertapp.todolist.controller;

import com.mertapp.todolist.payload.request.LoginRequest;
import com.mertapp.todolist.payload.request.SignupRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    public static final String API_ROOT_URL = "https://spring-react-mysql-todo-app.herokuapp.com/";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testRegisterUser() throws JSONException {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("");
        signupRequest.setUsername("test_user");
        signupRequest.setPassword("PASSW0RD");
        ResponseEntity<?> responseEntity = testRestTemplate.postForEntity(API_ROOT_URL + "api/user/register", signupRequest, JSONArray.class);
        assertNotNull(responseEntity);
    }

    @Test
    public void testLoginUser() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("mertesen");
        loginRequest.setPassword("123456");
        ResponseEntity<?> responseEntity = testRestTemplate.postForEntity(API_ROOT_URL + "api/user/login", loginRequest, LoginRequest.class);
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getHeaders().get("Authorization"));
    }
}
