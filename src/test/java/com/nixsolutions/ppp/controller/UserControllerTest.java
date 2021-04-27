package com.nixsolutions.ppp.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nixsolutions.ppp.config.TestConfig;
import com.nixsolutions.ppp.config.WebMvcConfig;
import com.nixsolutions.ppp.model.Gender;
import com.nixsolutions.ppp.model.Role;
import com.nixsolutions.ppp.model.User;
import com.nixsolutions.ppp.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, WebMvcConfig.class})
@WebAppConfiguration
public class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    WebApplicationContext webApplicationContext;

    private List<User> users;

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    private <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        User firstUser = User.builder()
                .id(1L)
                .username("first")
                .email("first@mail")
                .password("pass1")
                .birthday(new Date(1000))
                .gender(Gender.builder().id(1L).name("male").build())
                .roles(Collections.singleton(Role.builder().id(2L).name("user").build())).build();
        User secondUser = User.builder()
                .id(2L)
                .username("second")
                .email("second@mail")
                .password("pass2")
                .birthday(new Date(2000))
                .gender(Gender.builder().id(2L).name("female").build())
                .roles(Collections.singleton(Role.builder().id(2L).name("user").build())).build();
        users = Arrays.asList(firstUser, secondUser);
    }


    @Test
    public void findAllUsersShouldReturnFoundUserEntries() throws Exception {
        when(userService.findAll()).thenReturn(users);

        MvcResult mvcResult = mockMvc.perform(get("/users")).andReturn();
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());

        User[] result = mapFromJson(mvcResult.getResponse().getContentAsString(), User[].class);
        assertTrue(result.length > 1);
        assertEquals(users, Arrays.asList(result));
        verify(userService, times(2)).findAll();
    }

    @Test
    public void findAllUsersShouldThrowUserNotFound() throws Exception {
        when(userService.findAll()).thenReturn(Collections.emptyList());

        MvcResult mvcResult = mockMvc.perform(get("/users")).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());

        verify(userService, times(1)).findAll();
    }

    @Test
    public void addUserShouldReturnValidationError() throws Exception {
        User invalidUser = User.builder()
                .id(1L)
                .username("first")
                .email("first")
                .password("pass1")
                .birthday(new Date(1000))
                .gender(Gender.builder().id(1L).name("male").build())
                .build();
        MvcResult mvcResult = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(invalidUser))).andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void addUserShouldAddUserAndReturnId() throws Exception {
        User user = User.builder()
                .id(1L)
                .username("first")
                .email("first@gmail.com")
                .password("pass1")
                .birthday(new Date(1000))
                .gender(Gender.builder().id(1L).build())
                .build();

        when(userService.save(any(User.class))).thenReturn(1L);

        MvcResult mvcResult = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(user))).andReturn();

        assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
        assertEquals("1", mvcResult.getResponse().getContentAsString());

        verify(userService, times(1)).save(any(User.class));
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void updateUserTest() throws Exception {
        User user = User.builder()
                .username("new name")
                .email("first@gmail.com")
                .password("pass1")
                .birthday(new Date(1000))
                .gender(Gender.builder().id(1L).build())
                .build();
        MvcResult mvcResult = mockMvc.perform(put("/users/2")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(user))).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());

        verify(userService, times(1)).update(any(User.class));
    }

    @Test
    public void deleteUserTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/users/2")).andReturn();
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        String content = mvcResult.getResponse().getContentAsString();
        verify(userService, times(1)).deleteById(2L);
    }
}
