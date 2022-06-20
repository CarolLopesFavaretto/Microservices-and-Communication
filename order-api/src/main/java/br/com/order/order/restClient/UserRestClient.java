package br.com.order.order.restClient;

import br.com.order.order.adapter.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://user-api:8081/users", name = "user")
public interface UserRestClient {

    @GetMapping("{id}")
    UserResponse findUserById(@PathVariable("id") Long userId);
}
