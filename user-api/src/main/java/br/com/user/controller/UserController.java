package br.com.user.controller;

import br.com.user.adapter.request.UserRequest;
import br.com.user.adapter.response.UserResponse;
import br.com.user.core.entity.User;
import br.com.user.core.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserServices service;


    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return service.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        Optional<UserResponse> byId = service.findById(id);
        if (byId.isPresent()) {
            return ResponseEntity.ok().body(byId.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest) {
        return service.registrationUser(userRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@RequestBody @Valid UserRequest userRequest, @PathVariable Long
            id) {
        Optional<UserResponse> byId = service.updatedUser(userRequest, id);
        if (byId.isPresent()) {
            return ResponseEntity.ok().body(byId.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        return service.deleteById(id);
    }
}

