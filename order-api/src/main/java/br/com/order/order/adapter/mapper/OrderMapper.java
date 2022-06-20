package br.com.order.order.adapter.mapper;

import br.com.order.order.adapter.request.OrderRequest;
import br.com.order.order.adapter.response.OrderResponse;
import br.com.order.order.core.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderResponse toModel(Order order);

    Order toModel(OrderRequest userRequest);
}
