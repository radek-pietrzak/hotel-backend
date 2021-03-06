package pl.sdacademy.backend.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.sdacademy.backend.AuthorizedMockMvcRequestBuilder.authorizeGet;
import static pl.sdacademy.backend.AuthorizedMockMvcRequestBuilder.authorizePost;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
//@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureMockMvc
class UserControllerTestWithAuth {


    @Autowired
    MockMvc mock;

    @MockBean
    UserRepository userRepository;

    @Test
    void shouldReturn200Status() throws Exception {
        MvcResult mvcResult = mock
                .perform(authorizeGet("/user"))
                .andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void shouldReturnUserList() throws Exception {
        when(userRepository.findAll())
                .thenReturn(Arrays.asList(
                        new User("test",
                                "Test2021!",
                                "test@test.com",
                                "USER",
                                "test",
                                "test",
                                null),
                        new User("test",
                                "Test2021!",
                                "test@test.com",
                                "USER",
                                "test",
                                "test",
                                null))
                );
        mock.perform(authorizeGet("/user"))
                .andExpect(jsonPath("$", hasSize(2)));

    }

    @Test
    void shouldReturnFullUser() throws Exception {
        when(userRepository.save(any()))
                .thenReturn(new User("test",
                        "Test2021!",
                        "test@test.com",
                        "USER",
                        "test",
                        "test",
                        null));
        //language=JSON
        String userJson = "{\n" +
                "  \"username\": \"test\",\n" +
                "  \"password\": \"Test2021!\",\n" +
                "  \"email\": \"test\",\n" +
                "  \"role\": \"USER\",\n" +
                "  \"firstName\": \"test\",\n" +
                "  \"lastName\": \"test\",\n" +
                "  \"creditCard\": \"null\"\n" +
                "}";
        MockHttpServletRequestBuilder request = authorizePost("/user/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson);
        mock.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)));
    }
}

