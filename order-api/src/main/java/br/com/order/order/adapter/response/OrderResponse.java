package br.com.order.order.adapter.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;
    private Long userId;
    private String itemDescription;
    private Integer itemQuantity;
    private Double itemPrice;
    private Double totalValue;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
