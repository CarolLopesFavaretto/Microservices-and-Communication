package br.com.order.order.core.service;

import br.com.order.order.adapter.mapper.OrderMapper;
import br.com.order.order.adapter.request.OrderRequest;
import br.com.order.order.adapter.response.OrderResponse;
import br.com.order.order.adapter.response.UserResponse;
import br.com.order.order.core.entity.Order;
import br.com.order.order.core.repository.OrderRepository;
import br.com.order.order.restClient.UserRestClient;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServices {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private OrderMapper mapper;

    @Autowired
    private UserRestClient userRestClient;

    public ResponseEntity<List<OrderResponse>> listAll() {
        List<OrderResponse> response = repository.findAll().stream().map(i -> mapper.toModel(i)).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<OrderResponse> findById(Long id) {
        return repository.findById(id).map(order -> ResponseEntity.ok(mapper.toModel(order)))
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<List<OrderResponse>> findByUserId(Long userId) {
        List<OrderResponse> response = repository.findByUserId(userId).stream().map(i -> mapper.toModel(i)).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<OrderResponse> registrationOrder(OrderRequest orderRequest) {
        try {
            UserResponse userById = userRestClient.findUserById(orderRequest.getUserId());
        } catch (FeignException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Order order = mapper.toModel(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toModel(repository.save(order)));
    }

    public ResponseEntity<OrderResponse> updatedOrder(OrderRequest orderRequest, Long id) {
        return repository.findById(id).map(order -> {
            order.setUserId(orderRequest.getUserId());
            order.setItemDescription(orderRequest.getItemDescription());
            order.setItemPrice(orderRequest.getItemPrice());
            order.setItemQuantity(orderRequest.getItemQuantity());
            order.setTotalValue(orderRequest.getTotalValue());
            Order orderUpdated = repository.save(order);
            return ResponseEntity.status(HttpStatus.OK).body(mapper.toModel(orderUpdated));
        }).orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Void> deleteById(Long id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }
}


