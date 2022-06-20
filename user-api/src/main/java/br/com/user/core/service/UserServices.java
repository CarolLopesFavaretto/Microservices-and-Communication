package br.com.user.core.service;

import br.com.user.adapter.mapper.UserMapper;
import br.com.user.adapter.request.UserRequest;
import br.com.user.adapter.response.UserResponse;
import br.com.user.core.entity.User;
import br.com.user.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper mapper;

    @Cacheable(cacheNames = "User", key = "#id")
    public Optional<UserResponse> findById(Long id) {
        return repository.findById(id).map(user -> (mapper.toModel(user)));
    }

    public ResponseEntity<List<User>> listAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    public ResponseEntity<UserResponse> registrationUser(UserRequest userRequest) {
        User user = mapper.toModel(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toModel(repository.save(user)));
    }

    @CachePut(cacheNames = "User", key = "#id")
    public Optional<UserResponse> updatedUser(UserRequest userRequest, Long id) {
        return repository.findById(id).map(user -> {
            user.setCpf(userRequest.getCpf());
            user.setEmail(userRequest.getEmail());
            user.setName(userRequest.getName());
            user.setPhoneNumber(userRequest.getPhoneNumber());
            User userUpdated = repository.save(user);
            return Optional.of(mapper.toModel(userUpdated));
        }).orElse(Optional.empty());
    }

    @CacheEvict(cacheNames = "User", key = "#id")
    public ResponseEntity<Void> deleteById(Long id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }
}
