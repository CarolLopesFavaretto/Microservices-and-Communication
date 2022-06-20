package br.com.user.adapter.mapper;

import br.com.user.adapter.request.UserRequest;
import br.com.user.adapter.response.UserResponse;
import br.com.user.core.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponse toModel(User user);

    User toModel(UserRequest userRequest);
}
