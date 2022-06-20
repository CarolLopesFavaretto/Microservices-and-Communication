package br.com.order.order.controller;

import br.com.order.order.adapter.request.OrderRequest;
import br.com.order.order.adapter.response.OrderResponse;
import br.com.order.order.core.service.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrderController {

    @Autowired
    private OrderServices service;

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findAll() {
        return service.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> findByIdUser(@PathVariable Long userId) {
        return service.findByUserId(userId);
    }

    @PostMapping
    public ResponseEntity<OrderResponse> registrationOrder(@RequestBody @Valid OrderRequest orderRequest) {
        return service.registrationOrder(orderRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrder(@RequestBody @Valid OrderRequest orderRequest, @PathVariable Long id) {
        return service.updatedOrder(orderRequest, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        return service.deleteById(id);
    }
}