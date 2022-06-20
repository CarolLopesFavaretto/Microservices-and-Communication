package br.com.order.order;

import br.com.order.order.adapter.request.OrderRequest;
import br.com.order.order.adapter.response.OrderResponse;
import br.com.order.order.adapter.response.UserResponse;
import br.com.order.order.config.PostgreSQLContainerTest;
import br.com.order.order.core.entity.Order;
import br.com.order.order.core.repository.OrderRepository;
import br.com.order.order.restClient.UserRestClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class OrderApplicationTests extends PostgreSQLContainerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private OrderRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRestClient userRestClient;


    @Test
    public void orderFindAllEmptyTest() throws Exception {
        repository.deleteAll();

        mvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    String contentAsString = mvcResult.getResponse().getContentAsString();
                    assertThat(contentAsString).isEqualTo("[]");
                });
    }

    @Test
    public void orderFindByIdTest() throws Exception {
        Order order = Order.builder()
                .userId(5L)
                .itemDescription("pizza")
                .itemQuantity(1)
                .itemPrice(50D)
                .totalValue(55D)
                .build();
        Order save = repository.save(order);

        mvc.perform(get("/orders/{id}", save.getId()))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    String contentAsString = mvcResult.getResponse().getContentAsString();
                    OrderResponse orderResponse = objectMapper.readValue(contentAsString, OrderResponse.class);
                    assertThat(orderResponse.getUserId()).isEqualTo(order.getUserId());
                    assertThat(orderResponse.getItemDescription()).isEqualTo(order.getItemDescription());
                    assertThat(orderResponse.getItemQuantity()).isEqualTo(order.getItemQuantity());
                    assertThat(orderResponse.getItemPrice()).isEqualTo(order.getItemPrice());
                    assertThat(orderResponse.getTotalValue()).isEqualTo(order.getTotalValue());
                });
    }

    @Test
    public void registrationOrderTest() throws Exception {
        OrderRequest order = OrderRequest.builder()
                .userId(5L)
                .itemDescription("pizza")
                .itemQuantity(1)
                .itemPrice(50D)
                .totalValue(55D)
                .build();
        UserResponse userResponse = UserResponse.builder()
                .cpf("40023598728")
                .name("Joao Pedro")
                .build();

        Mockito.doReturn(userResponse).when(userRestClient).findUserById(any());
        mvc.perform(post("/orders")
                        .content(objectMapper.writeValueAsString(order))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(mvcResult -> {
                    String contentAsString = mvcResult.getResponse().getContentAsString();
                    OrderResponse orderResponse = objectMapper.readValue(contentAsString, OrderResponse.class);
                    assertThat(orderResponse.getUserId()).isEqualTo(order.getUserId());
                    assertThat(orderResponse.getItemDescription()).isEqualTo(order.getItemDescription());
                    assertThat(orderResponse.getItemQuantity()).isEqualTo(order.getItemQuantity());
                    assertThat(orderResponse.getItemPrice()).isEqualTo(order.getItemPrice());
                    assertThat(orderResponse.getTotalValue()).isEqualTo(order.getTotalValue());
                });
    }

    @Test
    public void updateOrderTest() throws Exception {
        Order order = Order.builder()
                .userId(5L)
                .itemDescription("pizza")
                .itemQuantity(1)
                .itemPrice(50D)
                .totalValue(55D)
                .build();
        Order save = repository.save(order);

        OrderRequest orderRequest = OrderRequest.builder()
                .userId(5L)
                .itemDescription("pizza mussarela")
                .itemQuantity(2)
                .itemPrice(50D)
                .totalValue(55D)
                .build();

        mvc.perform(put("/orders/{id}", save.getId())
                        .content(objectMapper.writeValueAsString(orderRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    String contentAsString = mvcResult.getResponse().getContentAsString();
                    OrderResponse orderResponse = objectMapper.readValue(contentAsString, OrderResponse.class);
                    assertThat(orderResponse.getUserId()).isEqualTo(orderRequest.getUserId());
                    assertThat(orderResponse.getItemDescription()).isEqualTo(orderRequest.getItemDescription());
                    assertThat(orderResponse.getItemQuantity()).isEqualTo(orderRequest.getItemQuantity());
                    assertThat(orderResponse.getItemPrice()).isEqualTo(orderRequest.getItemPrice());
                    assertThat(orderResponse.getTotalValue()).isEqualTo(orderRequest.getTotalValue());
                });
    }

    @Test
    public void deleteOrderTest() throws Exception {
        Order order = Order.builder()
                .userId(5L)
                .itemDescription("pizza")
                .itemQuantity(1)
                .itemPrice(50D)
                .totalValue(55D)
                .build();
        Order save = repository.save(order);

        mvc.perform(delete("/orders/{id}", save.getId()))
                .andExpect(status().isOk());

        mvc.perform(get("/orders/{id}", save.getId()))
                .andExpect(status().isNotFound());
    }
}
