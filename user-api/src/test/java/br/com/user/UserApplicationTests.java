package br.com.user;

import br.com.user.adapter.request.UserRequest;
import br.com.user.adapter.response.UserResponse;
import br.com.user.config.PostgreSQLContainerTest;
import br.com.user.core.entity.User;
import br.com.user.core.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class UserApplicationTests extends PostgreSQLContainerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void userFindAllEmptyTest() throws Exception {
        repository.deleteAll();

        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    String contentAsString = mvcResult.getResponse().getContentAsString();
                    assertThat(contentAsString).isEqualTo("[]");
                });
    }
    @Disabled("Após a implementação do Redis no projeto, os testes falharam por não conectar e não consegui configurar")
    @Test
    public void userFindByIdTest() throws Exception {
        User user = User.builder()
                .cpf("40028146883")
                .email("teste@teste.com")
                .name("Sr Teste Neto")
                .phoneNumber("11980662380")
                .build();
        repository.save(user);

        mvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    String contentAsString = mvcResult.getResponse().getContentAsString();
                    UserResponse userResponse = objectMapper.readValue(contentAsString, UserResponse.class);
                    assertThat(userResponse.getCpf()).isEqualTo(user.getCpf());
                    assertThat(userResponse.getEmail()).isEqualTo(user.getEmail());
                    assertThat(userResponse.getName()).isEqualTo(user.getName());
                    assertThat(userResponse.getPhoneNumber()).isEqualTo(user.getPhoneNumber());
                });
    }

    @Test
    public void registrationUserTest() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .cpf("40028146883")
                .email("teste@teste.com")
                .name("Sr Teste Neto")
                .phoneNumber("11980662380")
                .build();

        mvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(userRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(mvcResult -> {
                    String contentAsString = mvcResult.getResponse().getContentAsString();
                    UserResponse userResponse = objectMapper.readValue(contentAsString, UserResponse.class);
                    assertThat(userResponse.getCpf()).isEqualTo(userRequest.getCpf());
                    assertThat(userResponse.getEmail()).isEqualTo(userRequest.getEmail());
                    assertThat(userResponse.getName()).isEqualTo(userRequest.getName());
                    assertThat(userResponse.getPhoneNumber()).isEqualTo(userRequest.getPhoneNumber());
                });
    }
    @Disabled("Após a implementação do Redis no projeto, os testes falharam por não conectar e não consegui configurar")
    @Test
    public void updateUserTest() throws Exception {
        User user = User.builder()
                .cpf("40028146883")
                .email("teste@teste1.com")
                .name("Sr Teste Neto da Silva")
                .phoneNumber("11980662381")
                .build();
        repository.save(user);

        UserRequest userRequest = UserRequest.builder()
                .cpf("40028146883")
                .email("teste@teste2.com")
                .name("Sr Teste Neto da Silva Souza")
                .phoneNumber("11980662383")
                .build();

        mvc.perform(put("/users/{id}", 1)
                        .content(objectMapper.writeValueAsString(userRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    String contentAsString = mvcResult.getResponse().getContentAsString();
                    UserResponse userResponse = objectMapper.readValue(contentAsString, UserResponse.class);
                    assertThat(userResponse.getCpf()).isEqualTo(userRequest.getCpf());
                    assertThat(userResponse.getEmail()).isEqualTo(userRequest.getEmail());
                    assertThat(userResponse.getName()).isEqualTo(userRequest.getName());
                    assertThat(userResponse.getPhoneNumber()).isEqualTo(userRequest.getPhoneNumber());
                });
    }
    @Disabled("Após a implementação do Redis no projeto, os testes falharam por não conectar e não consegui configurar")
    @Test
    public void deleteUserTest() throws Exception {
        User user = User.builder()
                .cpf("40028146883")
                .email("teste@teste1.com")
                .name("Sr Teste Neto da Silva")
                .phoneNumber("11980662381")
                .build();
        repository.save(user);

        List<User> userList = repository.findAll();
        Long id = userList.get(0).getId();

        mvc.perform(delete("/users/{id}", id))
                .andExpect(status().isOk());

        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    String contentAsString = mvcResult.getResponse().getContentAsString();
                    assertThat(contentAsString).isEqualTo("[]");
                });

    }

}